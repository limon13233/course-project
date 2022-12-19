package com.example.kursach.Controllers;

import com.example.kursach.Models.Books;
import com.example.kursach.Models.Provider;
import com.example.kursach.Models.User;
import com.example.kursach.Repositories.BooksRepositorie;
import com.example.kursach.Repositories.ProviderRepositorie;
import com.example.kursach.Repositories.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Api {
    @Autowired
    ProviderRepositorie providerRepositorie;
    @Autowired
    UserRepositorie userRepositorie;
    @PostMapping("/api/users")
    public ResponseEntity<User> CreateUserApi(@Valid @RequestBody User user)
    {
        return ResponseEntity.ok(userRepositorie.save(user));
    }
    @DeleteMapping("/api/users/del/{id}")
    public ResponseEntity DeleteUserApi(@PathVariable long id) throws Exception
    {

        User book = userRepositorie.findById(id)
                .orElseThrow(() -> new Exception());
        userRepositorie.delete(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/users/view")
    public ResponseEntity<List<String>> getAllNotes() {
        List<String> users = new ArrayList<>();
        Iterable<User> iu=userRepositorie.findAll();
        for (User us:iu
             ) {
            users.add(us.toString());
        }
        return ResponseEntity.ok(users);
    }
    @PostMapping("/api/provider")
    public ResponseEntity<Provider> CreateUserApi(@Valid @RequestBody Provider user)
    {
        return ResponseEntity.ok(providerRepositorie.save(user));
    }
}
