package com.example.kursach.Repositories;

import com.example.kursach.Models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepositorie extends CrudRepository<User,Long> {
    User findBysurname(String surname);
    @Transactional
    @Query("SELECT u from User u WHERE u.surname =:su AND u.name=:na AND u.middleName=:mn")
    public User findByFIO(@Param("su") String surname,@Param("na") String name,@Param("mn") String midlename);

//    @Transactional
//    @Query("SELECT count(*) FROM history WHERE issuebook_id =:title")
//    public int count(@Param("title") long id);
}
