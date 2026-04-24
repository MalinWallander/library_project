package com.library.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.library.db.LoanDao;
import com.library.model.administration.Loan;

public class LoanService {

	private final LoanDao loanDao;

	public LoanService(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	public Loan addLoan(String copyId, String userId) {
		Loan loan = new Loan(
				UUID.randomUUID(),
				copyId,
				userId,
				LocalDate.now(),
				null);
		return loanDao.createLoan(loan);
	}

	public Optional<Loan> getLoanById(String loanId) {
		return loanDao.findById(loanId);
	}
}