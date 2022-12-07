package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Collection;


public class ForStatistic {


    private long book;
    private String name;


    public ForStatistic() {
    }

    public ForStatistic(int book, String name) {
        this.book = book;
        this.name = name;
    }

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
