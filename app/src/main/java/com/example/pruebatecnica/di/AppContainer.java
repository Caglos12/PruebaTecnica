package com.example.pruebatecnica.di;

import android.content.Context;

import com.example.pruebatecnica.data.repository.ContactRepository;
import com.example.pruebatecnica.data.source.DataSource;
import com.example.pruebatecnica.data.source.MockDataSource;

public class AppContainer {

    private final ContactRepository contactRepository;

    public AppContainer(Context context) {
        DataSource dataSource = new MockDataSource(context);
        contactRepository = new ContactRepository(dataSource);
    }

    public ContactRepository getContactRepository() {
        return contactRepository;
    }
}
