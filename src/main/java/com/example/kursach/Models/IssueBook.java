package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "issuebook")
public class IssueBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    private CopyBook copybook;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "history",
            joinColumns = @JoinColumn(name ="issuebook_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public IssueBook(Date data, CopyBook copybook) {
        this.data = data;
        this.copybook = copybook;
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

    public CopyBook getCopybook() {
        return copybook;
    }

    public void setCopybook(CopyBook copybook) {
        this.copybook = copybook;
    }
}
