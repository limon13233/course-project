package com.example.kursach.Models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class HistoryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
