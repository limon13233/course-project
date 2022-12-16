package com.example.kursach.Repositories;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProviderRepositorie extends CrudRepository<Provider,Long> {
    Provider findBynameprovider(String name);
    @Query(value = "CALL Provider_Delete(:id);", nativeQuery = true)
    String deleteById(@Param("id") long id);
}
