package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "issuebook")
public class IssueBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private boolean status;
    
    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    private Books books2;
    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    private User user;
//    @ManyToMany(cascade = CascadeType.DETACH)
//    @JoinTable(name = "history",
//            joinColumns = @JoinColumn(name ="issuebook_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> users;

    public IssueBook(Date data, Books books,User user,boolean status) {
        this.data = data;
        this.books2 = books;
        this.user = user;
        this.status = status;
    }

    public IssueBook() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Books getBooks2() {
        return books2;
    }

    public void setBooks2(Books books2) {
        this.books2 = books2;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
