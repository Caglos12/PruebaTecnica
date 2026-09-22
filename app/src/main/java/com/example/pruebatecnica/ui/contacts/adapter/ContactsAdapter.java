package com.example.pruebatecnica.ui.contacts.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.pruebatecnica.databinding.ItemContactBinding;
import com.example.pruebatecnica.data.model.Contact;

import java.util.function.Consumer;

public class ContactsAdapter extends ListAdapter<Contact, ContactViewHolder> {

    private final Consumer<String> onDialClick;

    public ContactsAdapter(Consumer<String> onDialClick) {
        super(new ContactDiffCallback());
        this.onDialClick = onDialClick;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ContactViewHolder(binding, onDialClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static class ContactDiffCallback extends DiffUtil.ItemCallback<Contact> {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getCode() == newItem.getCode();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.equals(newItem);
        }
    }
}
