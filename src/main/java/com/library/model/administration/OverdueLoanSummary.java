package com.library.model.administration;

import java.time.LocalDate;

public class OverdueLoanSummary {
	private final String itemTitle;
	private final String memberName;
	private final String userId;
	private final LocalDate dueDate;
	private final long daysOverdue;

	public OverdueLoanSummary(String itemTitle, String memberName,
			String userId, LocalDate dueDate) {
		this.itemTitle = itemTitle;
		this.memberName = memberName;
		this.userId = userId;
		this.dueDate = dueDate;
		this.daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getUserId() {
		return userId;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public long getDaysOverdue() {
		return daysOverdue;
	}
}