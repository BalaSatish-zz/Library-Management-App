package com.bala.satish.LibraryManagementSystem;

public class ApprovedBookDetails {
    String approvalDate;
    String bookName;
    String daysLeft;

    public ApprovedBookDetails() {
    }

    public ApprovedBookDetails(String approvalDate, String bookName, String daysLeft) {
        this.approvalDate = approvalDate;
        this.bookName = bookName;
        this.daysLeft = daysLeft;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public String getBookName() {
        return bookName;
    }

    public String getDaysLeft() {
        return daysLeft;
    }
}
