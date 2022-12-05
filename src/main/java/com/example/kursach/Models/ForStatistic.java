package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Collection;


public class ForStatistic {


    private int book;
    private String name;


    public ForStatistic() {
    }

    public ForStatistic(int book, String name) {
        this.book = book;
        this.name = name;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
