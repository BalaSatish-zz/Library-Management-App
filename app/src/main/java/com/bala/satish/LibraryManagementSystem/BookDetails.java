package com.bala.satish.LibraryManagementSystem;

public class BookDetails {

    private String book;
    private String author;
    private String quantity;

    public BookDetails() {
    }

    public BookDetails(String author, String book, String quantity) {
        this.book = book;
        this.author = author;
        this.quantity = quantity;
    }

    public String getBook() {
        return book;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuantity() {
        return quantity;
    }

}
