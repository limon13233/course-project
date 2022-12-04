package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.Provider;
import org.springframework.data.repository.CrudRepository;

public interface ProviderRepositorie extends CrudRepository<Provider,Long> {
    Provider findBynameprovider(String name);
}
