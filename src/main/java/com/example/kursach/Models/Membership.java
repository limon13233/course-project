package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String period;
    private String price;
    private Date dateregistration;
    @OneToMany(mappedBy = "membership", fetch = FetchType.EAGER)
    private Collection<User> users;

    public Membership(String period, String price, Date dateregistration, Collection<User> users) {
        this.period = period;
        this.price = price;
        this.dateregistration = dateregistration;
        this.users = users;
    }

    public Membership() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDateregistration() {
        return dateregistration;
    }

    public void setDateregistration(Date dateregistration) {
        this.dateregistration = dateregistration;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
