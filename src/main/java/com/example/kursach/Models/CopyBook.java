package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "copybook")
public class CopyBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Books book;
    @OneToMany(mappedBy = "copybook", fetch = FetchType.LAZY)
    private Collection<LibraryFund> libraryfunds;
    @OneToMany(mappedBy = "copybook", fetch = FetchType.LAZY)
    private Collection<IssueBook> issuebooks;


    public CopyBook() {
    }

    public CopyBook(Books books, Collection<LibraryFund> libraryfunds) {
        this.book = books;
        this.libraryfunds = libraryfunds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Books getBooks() {
        return book;
    }

    public void setBooks(Books books) {
        this.book = books;
    }

    public Collection<LibraryFund> getLibraryfunds() {
        return libraryfunds;
    }

    public void setLibraryfunds(Collection<LibraryFund> libraryfunds) {
        this.libraryfunds = libraryfunds;
    }
}
