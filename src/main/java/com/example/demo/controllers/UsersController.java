package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Users;
import com.example.demo.models.UsersFactory;
import com.example.demo.models.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    // private List<Users> usersList = new ArrayList<>();

    @Autowired
    private UsersRepository usersRepository;
    private List<Users> usersList = new ArrayList<>();

    @GetMapping("/users/view")
    public String getAll(Model model) {
        // Call DB to get all users
        usersList = usersRepository.findAll();
        model.addAttribute("users", usersList);
        return "users/view";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String name, @RequestParam String email, @RequestParam int age, @RequestParam String type) {
        // Call DB to add the user
        Users newUser = UsersFactory.createUser(name, email, age, type);
       
        // usersList.add(newUser);
        usersRepository.save(newUser);
        return "redirect:/users/view";  
    }

    @PostMapping("/users/update")
    public String putMethodName(@RequestParam String name, @RequestParam String email, @RequestParam int age) {
        // Call DB to update
        // todo: as an exercise, look for email and update the user, 
        //       then test it by adding a user, then updating it, 
        //       then viewing all users to see the change
        
        return "redirect:/users/view";
    }

   @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        Users user = (Users) session.getAttribute("session_user");
        if (user == null){
            return "users/login";
        }
        else {
            // Prevent caching
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            model.addAttribute("user",user);
            return "users/protected";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        // processing login
        String name = formData.get("name");
        String email = formData.get("email");
        List<Users> userlist = usersRepository.findByNameAndEmail(name, email);
        if (userlist.isEmpty()){
            return "users/login";
        }
        else {
            // success
            Users user = userlist.get(0);
            request.getSession().setAttribute("session_user", user);
            
            // Prevent caching of protected pages
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            model.addAttribute("user", user);
            return "users/protected";
        }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request, HttpServletResponse response){
        // Prevent caching of pages
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        // destroy the session
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/protected_page")
    public String getMethodName(HttpServletRequest request, HttpServletResponse response) {
        // check logged in status, if not logged in, redirect to login page
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login";
        }
        return "users/protected";
    }
    
    
}
