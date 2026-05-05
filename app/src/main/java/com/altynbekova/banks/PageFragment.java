package com.altynbekova.banks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.altynbekova.banks.databinding.FragmentPageBinding;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

public class PageFragment extends Fragment {
    private MapView mapView;
    private FragmentPageBinding binding;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View result=inflater.inflate(R.layout.fragment_page, container, false);
        binding = FragmentPageBinding.inflate(inflater, container, false);
        mapView = binding.mapview;
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}