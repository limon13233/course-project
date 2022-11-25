package com.example.kursach.Models;

import javax.persistence.*;

@Entity
@Table(name = "booksorder")
public class BooksOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Books books;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private order order;

    public BooksOrder() {
    }

    public BooksOrder(int amount, Books books, com.example.kursach.Models.order order) {
        this.amount = amount;
        this.books = books;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public com.example.kursach.Models.order getOrder() {
        return order;
    }

    public void setOrder(com.example.kursach.Models.order order) {
        this.order = order;
    }
}
