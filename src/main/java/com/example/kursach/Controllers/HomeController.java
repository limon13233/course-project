package com.example.kursach.Controllers;


import com.example.kursach.Models.Books;
import com.example.kursach.Models.Role;
import com.example.kursach.Models.User;
import com.example.kursach.Repositories.BooksRepositorie;
import com.example.kursach.Repositories.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    PasswordEncoder passwordEncoder;
@Autowired
UserRepositorie userRepositorie;
    @Autowired
    BooksRepositorie booksRepositorie;
    @GetMapping("/")
    public String index(Model model)
    {
        Iterable<User> users = userRepositorie.findAll();
        model.addAttribute("list_employee", users);
        model.addAttribute("listRoles", Role.values());
        return ("index");
    }
    @PostMapping("/")
    public String index(Model model,@RequestParam String[] roles,@RequestParam Long id)
    {
        User user = userRepositorie.findById(id).orElseThrow();
        user.getRoles().clear();

        for(String role: roles){
            user.getRoles().add(Role.valueOf(role));
        }

        userRepositorie.save(user);
        return("redirect:/");
    }
    @GetMapping("/Add")
    public String Add(User user,Books books)
    {
        return ("Add");
    }
    @PostMapping("/Add")
    public String Add(
            User user,
            RedirectAttributes red,
            @RequestParam String add)
    {
        red.addFlashAttribute("user",user);
        return ("redirect:Add/"+add);
    }
    @GetMapping("Add/{id}")
    public String EmployeeDelete(@PathVariable int id,  @Valid@ModelAttribute User user,
                                 @Valid Books books,BindingResult bindingResult) {
        switch (id) {
            case (1):
                if (bindingResult.hasErrors())
                    return ("Add");
                user.setActive(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(Collections.singleton(Role.EMPLOYEE));
                userRepositorie.save(user);
                return ("redirect:/Add");
            case (2):
                if (bindingResult.hasErrors())
                    return ("Add");
                user.setActive(true);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(Collections.singleton(Role.USER));
                userRepositorie.save(user);
                return ("redirect:/Add");
            case (3):
                if (bindingResult.hasErrors())
                    return ("Add");
                booksRepositorie.save(books);
                return ("redirect:/Add");
        };
        return("Add");
    }
}
