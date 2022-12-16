package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BooksRepositorie extends CrudRepository<Books,Long> {
    Books findBynamebooks(String nm);
    @Transactional
    @Query(value = "CALL Insert_Book(:name,:genre,:yr,:author);", nativeQuery = true)
    void insertbook(@Param("name") String name,@Param("genre") String genre,@Param("yr") String yr,@Param("author") String author);
}
