package com.library.model.administration;

import java.time.LocalDate;

public class LoanSummary {
	private final String itemTitle;
	private final LocalDate loanDate;
	private final LocalDate dueDate;

	public LoanSummary(String itemTitle, LocalDate loanDate, LocalDate dueDate) {
		this.itemTitle = itemTitle;
		this.loanDate = loanDate;
		this.dueDate = dueDate;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
}