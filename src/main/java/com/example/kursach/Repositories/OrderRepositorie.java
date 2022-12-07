package com.example.kursach.Repositories;


import com.example.kursach.Models.order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepositorie extends CrudRepository<order,Long> {
}
