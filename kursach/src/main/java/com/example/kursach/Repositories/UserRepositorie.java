package com.example.kursach.Repositories;

import com.example.kursach.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositorie extends CrudRepository<User,Long> {

}
