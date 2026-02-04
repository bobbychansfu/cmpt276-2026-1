package com.example.demo.models;

public class GuestUsers extends Users {
    public GuestUsers(String name, String email, int age) {
        super(name + " (Guest)", email, age);
    }
    // Additional methods specific to GuestUsers can be added here
}
