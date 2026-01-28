package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.models.Users;

import org.springframework.ui.Model;

@Controller
public class UsersController {
    @GetMapping("/users/view")
    public String viewUsers(Model model) {
        // gets users from DB (not implemented)
        List<Users> usersList = new ArrayList<>();
        usersList.add(new Users("Alice", "alice@example.com", 30));
        usersList.add(new Users("Bob", "bob@example.com", 25));
        usersList.add(new Users("Charlie", "charlie@example.com", 35));
        usersList.add(new Users("Diana", "diana@example.com", 28));
        // end of DB fetch simulation
        model.addAttribute("users", usersList);
        return "users/view";
    }
}
