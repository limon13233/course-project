package com.example.kursach.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1,max = 50,message = "Поле не должно быть пустым")
    private String period;
    @Size(min = 1,max = 100,message = "Поле не должно быть пустым")
    private String price;
    private Date dateregistration;
    @JsonIgnore
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
    @JsonIgnore
    public Collection<User> getUsers() {
        return users;
    }
    @JsonIgnore
    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id +
                ", \"period\":\"" + this.period + '\"' +
                ", \"price\":\"" + this.price + '\"' +
                ", \"dateregistration\":" + this.dateregistration +
                '}';
    }
}
