package com.example.pruebatecnica.ui.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pruebatecnica.PruebaTecnicaApp;
import com.example.pruebatecnica.R;
import com.example.pruebatecnica.data.repository.ContactRepository;
import com.example.pruebatecnica.databinding.FragmentContactsBinding;
import com.example.pruebatecnica.ui.contacts.adapter.ContactsAdapter;

public class ContactsFragment extends Fragment {

    private FragmentContactsBinding binding;
    private ContactsViewModel viewModel;
    private ContactsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();
        setupRecyclerView();
        setupObservers();
        setupListeners();
    }

    private void setupViewModel() {
        ContactRepository repository = ((PruebaTecnicaApp) requireActivity().getApplication())
                .getAppContainer().getContactRepository();
        ContactsViewModelFactory factory = new ContactsViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(ContactsViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new ContactsAdapter(this::dial);
        binding.recyclerContacts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerContacts.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.getContacts().observe(getViewLifecycleOwner(), state -> {
            switch (state.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.recyclerContacts.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.recyclerContacts.setVisibility(View.VISIBLE);
                    if (state.data != null) {
                        adapter.submitList(state.data, ()
                                -> binding.recyclerContacts.scrollToPosition(0));
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.recyclerContacts.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),
                            "Error: " + state.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void setupListeners() {
        binding.filterGroup.setOnCheckedStateChangeListener((group,
                                                             checkedIds) -> {
            int id = checkedIds.isEmpty() ? View.NO_ID : checkedIds.get(0);
            if (id == R.id.chipVisited) {
                viewModel.setFilter(ContactsViewModel.Filter.VISITED);
            } else if (id == R.id.chipNotVisited) {
                viewModel.setFilter(ContactsViewModel.Filter.NOT_VISITED);
            } else {
                viewModel.setFilter(ContactsViewModel.Filter.ALL);
            }
        });

        binding.sortGroup.setOnCheckedStateChangeListener((group,
                                                           checkedIds) -> {
            int id = checkedIds.isEmpty() ? View.NO_ID : checkedIds.get(0);
            if (id == R.id.chipSortName) {
                viewModel.setSort(ContactsViewModel.Sort.NAME);
            } else {
                viewModel.setSort(ContactsViewModel.Sort.CODE);
            }
        });
    }

    private void dial(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                phone, null));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), R.string.no_dialer,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
