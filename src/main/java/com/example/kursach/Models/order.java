package com.example.kursach.Models;

import com.mysql.cj.conf.PropertyDefinitions;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "[order]")
public class order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateregistration;
    private boolean status;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Provider provider;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<BooksOrder> booksorders;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public Date getdateregistration(){return dateregistration;};
    public void setdateregistration(Date date){this.dateregistration=date;}

    public Provider getProvider() { return provider; }
    public void setProvider(Provider provider) { this.provider = provider; }


    public order() { }

    public order(Date dateregistration,boolean status) {
        this.dateregistration = dateregistration;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Collection<BooksOrder> getBooksorders() {
        return booksorders;
    }

    public void setBooksorders(Collection<BooksOrder> booksorders) {
        this.booksorders = booksorders;
    }
}
