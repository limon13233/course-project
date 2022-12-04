package com.example.kursach.Repositories;

import com.example.kursach.Models.BooksOrder;
import com.example.kursach.Models.IssueBook;
import org.springframework.data.repository.CrudRepository;

public interface IssuebookRepositorie extends CrudRepository<IssueBook,Long> {

}
