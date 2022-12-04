package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.CopyBook;
import org.springframework.data.repository.CrudRepository;

public interface CopyBooksRepositorie extends CrudRepository<CopyBook,Long> {
    CopyBook findBybook_id(long id);
}
