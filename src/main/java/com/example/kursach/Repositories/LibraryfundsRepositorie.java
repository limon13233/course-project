package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.LibraryFund;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LibraryfundsRepositorie extends CrudRepository<LibraryFund,Long> {
    LibraryFund findBybooks1_id(long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM LibraryFund WHERE books1_id =:title")
    public void deleteCBin(@Param("title") long id);
}
