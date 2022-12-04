package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.Membership;
import org.springframework.data.repository.CrudRepository;

public interface MembershipRepositorie extends CrudRepository<Membership,Long> {
 Membership findByperiod(String period);
}
