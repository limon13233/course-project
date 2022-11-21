package com.example.kursach.Controllers;


import com.example.kursach.Models.User;
import com.example.kursach.Repositories.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
@Autowired
UserRepositorie userRepositorie;
    @GetMapping("/")
    public String index(Model model)
    {
        Iterable<User> users = userRepositorie.findAll();
        model.addAttribute("list_employee", users);
        return ("index");
    }
}
