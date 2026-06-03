package com.altynbekova.banks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.altynbekova.banks.db.AppRepository;
import com.altynbekova.banks.db.model.Bank;

import java.util.List;

public class BankViewModel extends AndroidViewModel {
    private final AppRepository appRepository;
    private final LiveData<List<Bank>> banks;

    public BankViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
//        banks = appRepository.getBanks();
    }

    public LiveData<List<Bank>> getBanks() {
        return appRepository.getBanks();
    }

    public void insert(Bank bank) {
        appRepository.insert(bank);
    }

    public void insertAll(List<Bank> banks) {
        appRepository.insertAll(banks);
    }
}
