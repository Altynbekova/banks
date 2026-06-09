package com.altynbekova.banks.ui.banks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altynbekova.banks.R;
import com.altynbekova.banks.db.model.Bank;
import com.altynbekova.banks.util.ApiService;
import com.altynbekova.banks.util.Util;
import com.altynbekova.banks.viewmodel.BankViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 */
public class BankFragment extends Fragment {
    private static final String TAG = "Banks Map";
    private static final List<Bank> banks = new ArrayList<>(
            List.of(
                    new Bank("bank1", "address1", "active",
                            new Point(55.030100, 82.920580)),
                    new Bank("bank2", "address2", "active",
                            new Point(55.030200, 82.920500))
            ));

    private MyBankRecyclerViewAdapter adapter;
    private BankViewModel bankViewModel;

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
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyBankRecyclerViewAdapter(
                new MyBankRecyclerViewAdapter.OnClickListener() {
                    @Override
                    public void onDelete(int id) {
                        bankViewModel.delete(id);
                    }

                    @Override
                    public void update(Bank bank) {
                        showEditDialog(bank);
                    }
                }
        );
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.fab).setOnClickListener(v -> {showAddDialog()});

        return view;
    }

    private void showAddDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_bank, null);
        dialog.setContentView(dialogView);

        EditText bankName = dialogView.findViewById(R.id.bankName);
        Button saveBtn = dialogView.findViewById(R.id.save);

        saveBtn.setOnClickListener(v -> {
            Bank newBank = new Bank();
            newBank.setName(bankName.getText().toString().trim());

            bankViewModel.insert(newBank);
        });

        dialog.show();
    }

    private void showEditDialog(Bank bank) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_bank, null);
        dialog.setContentView(dialogView);

        EditText bankName = dialogView.findViewById(R.id.bankName);
        Button saveBtn = dialogView.findViewById(R.id.save);

        bankName.setText(bank.getName());

        saveBtn.setOnClickListener(v -> {
            String newName = bankName.getText().toString().trim();

            if (!newName.isEmpty() && !newName.equals(bank.getName())){
                bank.setName(newName);

                bankViewModel.update(bank);

                dialog.dismiss();
            } else {
                bankName.setError("Поле не должно быть пустым");
            }
        });

        dialog.show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fetchBanksData();

        bankViewModel = new ViewModelProvider(requireActivity()).get(BankViewModel.class);

        bankViewModel.getBanks().observe(requireActivity(),
                new Observer<List<Bank>>() {
                    @Override
                    public void onChanged(List<Bank> banks) {
                        adapter.updateItems(banks);
                    }
                });
        /*AsyncTask.execute(() ->
                bankViewModel.insertAll(banks)
        );*/
    }

    private void fetchBanksData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suggestions.dadata.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);


        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,
                "{ \"query\": \"банк\", \n" +
                        "\t\"locations\":[\n" +
                        "\t\t{\n" +
                        "\t\t\t\"kladr_id\":\"6100000100000\"\n" +
                        "\t\t}, \n" +
                        "\t\t{\n" +
                        "\t\t\t\"kladr_id\":\"5400000100000\"\n" +
                        "\t\t}] }");
        Call<ResponseBody> call = service.suggestions(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    List<Bank> banksResponse = Util.parseResponse(response.body());

                    getActivity().runOnUiThread(
                            () -> adapter.updateItems(banksResponse));
                    Log.d(TAG, "onResponse: api response " + banksResponse);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: failed request", t);
            }
        });
    }
}