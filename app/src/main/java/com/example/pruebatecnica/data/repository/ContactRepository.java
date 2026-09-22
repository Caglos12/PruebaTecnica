package com.example.pruebatecnica.data.repository;

import com.example.pruebatecnica.data.model.Contact;
import com.example.pruebatecnica.data.source.DataSource;

import java.util.List;

public class ContactRepository {
    private final DataSource dataSource;

    public ContactRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Contact> getAllContacts() {
        return dataSource.getContacts();
    }
}
