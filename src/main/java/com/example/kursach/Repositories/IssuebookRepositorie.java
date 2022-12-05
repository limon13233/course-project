package com.example.kursach.Repositories;

import com.example.kursach.Models.BooksOrder;
import com.example.kursach.Models.CopyBook;
import com.example.kursach.Models.IssueBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IssuebookRepositorie extends CrudRepository<IssueBook,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM IssueBook WHERE copybook_id =:title")
    public void deleteCBin(@Param("title") long id);
}
