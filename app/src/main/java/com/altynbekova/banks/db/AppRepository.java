package com.altynbekova.banks.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.altynbekova.banks.db.dao.BankDao;
import com.altynbekova.banks.db.model.Bank;
import com.altynbekova.banks.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private BankDao bankDao;

    private List<Bank> banks = new ArrayList<>();

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        bankDao = db.bankDao();
    }

    /*public List<Bank> getBanks() {
        Future<List<Bank>> future = AppDatabase.databaseWriteExecutor.submit(new Callable<List<Bank>>() {
            @Override
            public List<Bank> call() throws Exception {
                return bankDao.getAll();
            }
        });

        try {
            banks = future.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(Util.TAG, "getBanks: cannot select all", e);
        }

        return banks;
    }*/

    public LiveData<List<Bank>> getBanks() {
        return bankDao.getAll();
    }

    public void insert(Bank bank){
        AppDatabase.databaseWriteExecutor.execute(() ->
                bankDao.insert(bank));
    }

    public void insertAll(List<Bank> banks){
        AppDatabase.databaseWriteExecutor.execute(() ->
                bankDao.insertAll(banks));
    }
}
