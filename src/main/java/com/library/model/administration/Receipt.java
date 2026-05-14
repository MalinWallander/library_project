package com.library.model.administration;

import java.time.LocalDate;

public class Receipt {
    private final String loanId;
    private final String copyId;
    private final String itemTitle;
    private final String itemType;
    private final String memberId;
    private final String memberName;
    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public Receipt(
            String loanId,
            String copyId,
            String itemTitle,
            String itemType,
            String memberId,
            String memberName,
            LocalDate loanDate,
            LocalDate dueDate) {
        this.loanId = loanId;
        this.copyId = copyId;
        this.itemTitle = itemTitle;
        this.itemType = itemType;
        this.memberId = memberId;
        this.memberName = memberName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    // TODO: Never used
    public String getCopyId() {
        return copyId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemType() {
        return itemType;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
