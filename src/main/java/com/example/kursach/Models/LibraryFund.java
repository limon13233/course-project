package com.example.kursach.Models;

import javax.persistence.*;

@Entity
@Table(name = "libraryfunds")
public class LibraryFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    @ManyToOne(optional = true, cascade = CascadeType.REMOVE)
    private Books books1;

    public LibraryFund() {
    }

    public LibraryFund(int amount, Books copybook) {
        this.amount = amount;
        this.books1 = copybook;
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

    public Books getCopybook() {
        return books1;
    }

    public void setCopybook(Books copybook) {
        this.books1 = copybook;
    }
}
