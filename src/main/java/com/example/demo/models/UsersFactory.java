package com.example.demo.models;

public class UsersFactory {
    public static Users createUser(String name, String email, int age, String type) {
        // if (type.equals("admin")) {
        //     return new AdminUsers(name, email, age);
        // } else if (type.equals("guest")) {
        //     return new GuestUsers(name, email, age);
        // } else {
        //     return new Users(name, email, age);
        // }
        return new Users(name, email, age); 
    }
}
