package com.ivan.readingjournal.model.db;

public class Book {
    public long id;
    public String title;
    public String author;
    public int pagesTotal;
    public int pagesRead;
    public int rating;
    public String notes;
    public Book() {}
    public Book(long id, String title, String author, int pagesTotal, int pagesRead, int rating, String notes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pagesTotal = pagesTotal;
        this.pagesRead = pagesRead;
        this.rating = rating;
        this.notes = notes;
    }
}