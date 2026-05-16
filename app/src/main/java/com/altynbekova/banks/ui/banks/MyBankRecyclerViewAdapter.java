package com.altynbekova.banks.ui.banks;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altynbekova.banks.model.Bank;
import com.altynbekova.banks.placeholder.PlaceholderContent.PlaceholderItem;
import com.altynbekova.banks.databinding.FragmentBankBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBankRecyclerViewAdapter extends RecyclerView.Adapter<MyBankRecyclerViewAdapter.ViewHolder> {

    private final List<Bank> banks;

    public MyBankRecyclerViewAdapter(List<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentBankBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = banks.get(position);
        holder.name.setText(banks.get(position).getName());
        holder.address.setText(banks.get(position).getAddress());
        holder.status.setText(banks.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView address;
        public final TextView status;
        public Bank mItem;

        public ViewHolder(FragmentBankBinding binding) {
            super(binding.getRoot());
            name = binding.name;
            address = binding.address;
            status = binding.status;
        }
    }

    public void updateItems(List<Bank> newBanks){
        banks.clear();
        banks.addAll(newBanks);
        notifyDataSetChanged();
    }
}