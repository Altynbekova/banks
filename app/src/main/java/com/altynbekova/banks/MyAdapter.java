package com.altynbekova.banks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.altynbekova.banks.ui.banks.BankFragment;
import com.altynbekova.banks.ui.map.MapFragment;

public class MyAdapter extends FragmentStateAdapter {
    public MyAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = MapFragment.newInstance(position);
        } else fragment = new BankFragment();

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
