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
    private CopyBook copybook;

    public LibraryFund() {
    }

    public LibraryFund(int amount, CopyBook copybook) {
        this.amount = amount;
        this.copybook = copybook;
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

    public CopyBook getCopybook() {
        return copybook;
    }

    public void setCopybook(CopyBook copybook) {
        this.copybook = copybook;
    }
}
