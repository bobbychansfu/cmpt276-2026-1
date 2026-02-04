package com.example.demo.models;

public class AdminUsers extends Users {
    public AdminUsers(String name, String email, int age) {
        super(name + " (Admin)", email, age);
    }
    // Additional methods specific to AdminUsers can be added here
    
}
