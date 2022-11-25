package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepositorie extends CrudRepository<Books,Long> {

}
