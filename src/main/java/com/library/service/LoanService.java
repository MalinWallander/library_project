package com.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.library.db.LoanDao;
import com.library.model.administration.Loan;
import com.library.model.administration.Receipt;

public class LoanService {

	private final LoanDao loanDao;

	public LoanService(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	public Loan addLoan(String copyId, String userId) {
		LocalDate borrowDate = LocalDate.now();
LocalDate dueDate = calculateDueDate(copyId);

Loan loan = new Loan(
    UUID.randomUUID(),
    copyId,
    userId,
    borrowDate,
    dueDate,
    null
);
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


	private LocalDate calculateDueDate(String copyId) {

    // Tillfällig enkel regel:
    return LocalDate.now().plusDays(30);

}

  public List<Loan> getOverdueLoans() {
        return loanDao.getOverdueLoans();
    }

}

