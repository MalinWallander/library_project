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

		// 1. Fetch and validate the copy
		Copy copy = itemDao.findCopyByBarcode(barcode);
		if (copy == null) {
			throw new IllegalArgumentException("No copy found with barcode: " + barcode);
		}

		// 2. Check copy is available
		if (!"Available".equals(copy.getStatus())) {
			throw new IllegalArgumentException("This copy is not available for loan.");
		}

		// 3. Check not a reference copy
		if (copy.isReferenceCopy()) {
			throw new IllegalArgumentException("Reference copies cannot be loaned out.");
		}

		// 4. Check item type is loanable
		Item item = itemDao.findById(copy.getItemId());
		if (NON_LOANABLE_TYPES.contains(item.getItemType())) {
			throw new IllegalArgumentException("Periodicals and magazines cannot be loaned out.");
		}

		// 5. Fetch user and check loan limit
		User user = userDao.findById(UUID.fromString(userId))
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
		List<Loan> currentLoans = loanDao.findByUserId(userId);
		int maxLoans = MAX_LOANS.getOrDefault(user.getCategoryId(), 3);
		if (currentLoans.size() >= maxLoans) {
			throw new IllegalArgumentException(
					"Loan limit reached. Your category allows a maximum of " + maxLoans + " simultaneous loans.");
		}

		// 6. Check for overdue loans
		boolean hasOverdue = currentLoans.stream()
				.anyMatch(l -> l.getDueDate() != null
						&& l.getDueDate().isBefore(LocalDate.now())
						&& l.getReturnDate() == null);
		if (hasOverdue) {
			throw new IllegalArgumentException(
					"You have overdue loans. Please return them before borrowing again.");
		}

		// 7. Calculate due date based on item type
		int days = LOAN_DAYS.getOrDefault(item.getItemType(), 30);
		LocalDate dueDate = LocalDate.now().plusDays(days);

		Loan loan = new Loan(
				UUID.randomUUID(),
				barcode,
				userId,
				LocalDate.now(),
				dueDate,
				null);

		return loanDao.createLoan(loan);
	}

	public Optional<Loan> getLoanById(String loanId) {
		return loanDao.findById(loanId);
	}

	public Optional<Receipt> receipt(String loanId) {
		return loanDao.receipt(loanId);
	}

	public List<Loan> getLoansForUser(String userId) {
		return loanDao.findByUserId(userId);
	}

	public List<Loan> getOverdueLoans() {
		return loanDao.getOverdueLoans();
	}

	public List<LoanSummary> getLoanSummariesForUser(String userId) {
		return loanDao.findSummariesByUserId(userId);
	}

	public List<OverdueLoanSummary> getOverdueLoanSummaries() {
		return loanDao.getOverdueLoanSummaries();
	}
}