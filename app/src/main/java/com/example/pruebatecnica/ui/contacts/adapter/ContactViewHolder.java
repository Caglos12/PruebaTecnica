package com.example.pruebatecnica.ui.contacts.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import com.example.pruebatecnica.R;
import com.example.pruebatecnica.databinding.ItemContactBinding;
import com.example.pruebatecnica.data.model.Contact;

import java.util.function.Consumer;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private final ItemContactBinding binding;
    private final Consumer<String> onDialClick;

    public ContactViewHolder(ItemContactBinding binding, Consumer<String> onDialClick) {
        super(binding.getRoot());
        this.binding = binding;
        this.onDialClick = onDialClick;
    }

    public void bind(Contact contact) {
        binding.textName.setText(contact.getName());
        binding.textCode.setText(itemView.getContext().getString(R.string.code_prefix, contact.getCode()));
        binding.textEmail.setText(contact.getEmail());
        binding.btnPhone.setText(contact.getPhone());

        if (contact.isVisited()) {
            binding.textVisited.setText(R.string.visited);
            binding.textVisited.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.visited));
        } else {
            binding.textVisited.setText(R.string.not_visited);
            binding.textVisited.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.not_visited));
        }

        binding.btnPhone.setOnClickListener(v -> {
            if (onDialClick != null) {
                onDialClick.accept(contact.getPhone());
            }
        });
    }
}
