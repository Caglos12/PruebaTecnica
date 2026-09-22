package com.example.pruebatecnica.data.model;

public class Contact {

    private final int code;
    private final String name;
    private final String phone;
    private final String email;
    private final boolean visited;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVisited() {
        return visited;
    }

    public Contact(int code, String name, String phone, String email, boolean visited) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.visited = visited;
    }
}
