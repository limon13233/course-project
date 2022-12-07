package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.LibraryFund;
import org.springframework.data.repository.CrudRepository;

public interface LibraryfundsRepositorie extends CrudRepository<LibraryFund,Long> {
    LibraryFund findBybooks1_id(long id);
}
