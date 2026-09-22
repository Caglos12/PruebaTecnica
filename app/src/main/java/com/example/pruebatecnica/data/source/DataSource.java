package com.example.pruebatecnica.data.source;

import com.example.pruebatecnica.data.model.Contact;

import java.util.List;

public interface DataSource {
    List<Contact> getContacts();
}
