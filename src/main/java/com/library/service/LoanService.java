package com.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.library.db.ItemDao;
import com.library.db.LoanDao;
import com.library.db.UserDao;
import com.library.model.User;
import com.library.model.administration.Loan;
import com.library.model.administration.LoanSummary;
import com.library.model.administration.OverdueLoanSummary;
import com.library.model.administration.Receipt;
import com.library.model.items.Copy;
import com.library.model.items.Item;

public class LoanService {

	private final LoanDao loanDao;
	private final ItemDao itemDao;
	private final UserDao userDao;

	private static final Map<String, Integer> MAX_LOANS = Map.of(
			"Student", 5,
			"Researcher", 15,
			"Other University Employee", 10,
			"Public", 3);

	private static final Map<String, Integer> LOAN_DAYS = Map.of(
			"CourseLiterature", 14,
			"Book", 30,
			"DVD", 7);

	private static final Set<String> NON_LOANABLE_TYPES = Set.of("Periodical", "Magazine");

	public LoanService(LoanDao loanDao, ItemDao itemDao, UserDao userDao) {
		this.loanDao = loanDao;
		this.itemDao = itemDao;
		this.userDao = userDao;
	}

	public Loan addLoan(String barcode, String userId) {

		Copy copy = itemDao.findCopyByBarcode(barcode);
		if (copy == null) {
			throw new IllegalArgumentException("No copy found with barcode: " + barcode);
		}

		if (!"Available".equals(copy.getStatus())) {
			throw new IllegalArgumentException("This copy is not available for loan.");
		}

		if (copy.isReferenceCopy()) {
			throw new IllegalArgumentException("Reference copies cannot be loaned out.");
		}

		Item item = itemDao.findById(copy.getItemId());
		if (NON_LOANABLE_TYPES.contains(item.getItemType())) {
			throw new IllegalArgumentException("Periodicals and magazines cannot be loaned out.");
		}

		User user = userDao.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
		List<Loan> currentLoans = loanDao.findByUserId(userId);
		int maxLoans = MAX_LOANS.getOrDefault(user.getCategoryId(), 3);
		if (currentLoans.size() >= maxLoans) {
			throw new IllegalArgumentException(
					"Loan limit reached. Your category allows a maximum of " + maxLoans + " simultaneous loans.");
		}

		boolean hasOverdue = currentLoans.stream()
				.anyMatch(l -> l.getDueDate() != null
						&& l.getDueDate().isBefore(LocalDate.now())
						&& l.getReturnDate() == null);
		if (hasOverdue) {
			throw new IllegalArgumentException(
					"You have overdue loans. Please return them before borrowing again.");
		}

		int days = LOAN_DAYS.getOrDefault(item.getItemType(), 30);
		LocalDate dueDate = LocalDate.now().plusDays(days);

		Loan loan = new Loan(
				UUID.randomUUID().toString(),
				barcode,
				userId,
				LocalDate.now(),
				dueDate,
				null);

		return loanDao.createLoan(loan);
	}

	public Optional<Receipt> receipt(String loanId) {
		return loanDao.receipt(loanId);
	}

	public List<LoanSummary> getLoanSummariesForUser(String userId) {
		return loanDao.findSummariesByUserId(userId);
	}

	public List<OverdueLoanSummary> getOverdueLoanSummaries() {
		return loanDao.getOverdueLoanSummaries();
	}
}