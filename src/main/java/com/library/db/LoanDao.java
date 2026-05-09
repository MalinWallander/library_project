package com.library.db;

import java.util.List;
import java.util.Optional;

import com.library.model.administration.Loan;
import com.library.model.administration.LoanSummary;
import com.library.model.administration.Receipt;

public interface LoanDao {

	Loan createLoan(Loan loan);

	Optional<Loan> findById(String id);

	Optional<Receipt> receipt(String loanId);

	List<Loan> findByUserId(String userId);

	List<Loan> getOverdueLoans();

	List<LoanSummary> findSummariesByUserId(String userId);
}
