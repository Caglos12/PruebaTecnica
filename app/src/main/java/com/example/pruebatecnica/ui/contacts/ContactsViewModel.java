package com.example.pruebatecnica.ui.contacts;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pruebatecnica.data.model.Contact;
import com.example.pruebatecnica.data.repository.ContactRepository;
import com.example.pruebatecnica.ui.base.UiState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactsViewModel extends ViewModel {
    public enum Filter { ALL, VISITED, NOT_VISITED }
    public enum Sort { CODE, NAME }

    private final List<Contact> allContacts;
    private final MutableLiveData<UiState<List<Contact>>> contacts = new MutableLiveData<>();
    private Filter filter = Filter.ALL;
    private Sort sort = Sort.CODE;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ContactsViewModel(ContactRepository repository) {
        allContacts = repository.getAllContacts();
        apply();
    }

    public LiveData<UiState<List<Contact>>> getContacts() {
        return contacts;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
        apply();
    }

    public void setSort(Sort sort) {
        this.sort = sort;
        apply();
    }

    private void apply() {
        contacts.setValue(UiState.loading());

        handler.postDelayed(() -> {
            List<Contact> result = new ArrayList<>();
            for (Contact contact : allContacts) {
                switch (filter) {
                    case VISITED:
                        if (contact.isVisited()) result.add(contact);
                        break;
                    case NOT_VISITED:
                        if (!contact.isVisited()) result.add(contact);
                        break;
                    default:
                        result.add(contact);
                }
            }

            Comparator<Contact> comparator = (sort == Sort.NAME)
                    ? Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)
                    : Comparator.comparing(Contact::getCode);
            result.sort(comparator);

            contacts.setValue(UiState.success(result));
        }, 300);
    }
}
