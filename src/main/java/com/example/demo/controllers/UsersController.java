package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Users;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private List<Users> usersList = new ArrayList<>();

    @GetMapping("/users/view") 
    public String getAll(Model model) {
        // Call DB to get all users
        model.addAttribute("users", usersList);
        return "users/view";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String name, @RequestParam String email, @RequestParam int age) {
        // Call DB to add the user
        usersList.add(new Users(name, email, age));
        return "redirect:/users/view";
    }
    
    @PostMapping("/users/update")
    public String putMethodName(@RequestParam String name, @RequestParam String email, @RequestParam int age) {
        // Call DB to update
        // look for email and update the user
        for (Users user : usersList) {
            if (user.getEmail().equals(email)) {
                user.setName(name);
                user.setAge(age);
                break;
            }
        }
        return "redirect:/users/view";
    }
}
