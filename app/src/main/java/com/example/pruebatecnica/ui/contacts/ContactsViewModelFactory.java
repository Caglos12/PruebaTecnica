package com.example.pruebatecnica.ui.contacts;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebatecnica.data.repository.ContactRepository;

public class ContactsViewModelFactory implements ViewModelProvider.Factory {

    private final ContactRepository repository;

    public ContactsViewModelFactory(ContactRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactsViewModel.class)) {
            return (T) new ContactsViewModel(repository);
        }
        throw new IllegalArgumentException(
                "ViewModel desconocido: " + modelClass.getName()
        );
    }
}
