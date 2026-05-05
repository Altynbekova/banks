package com.altynbekova.banks;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altynbekova.banks.placeholder.PlaceholderContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BankFragment extends Fragment {
    private static List<Bank> banks = List.of(
            new Bank("bank1", "address1", "active"),
            new Bank("bank2", "address2", "active")
    );
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BankFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BankFragment newInstance(int columnCount) {
        BankFragment fragment = new BankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyBankRecyclerViewAdapter(banks));

        return view;
    }
}