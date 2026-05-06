package com.library.db;

import java.util.List;
import java.util.Optional;

import com.library.model.administration.Loan;

public interface LoanDao {

	Loan createLoan(Loan loan);

	Optional<Loan> findById(String id);

	List<Loan> findByUserId(String userId);
}