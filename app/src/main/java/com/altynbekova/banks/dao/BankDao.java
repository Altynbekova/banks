package com.altynbekova.banks.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.altynbekova.banks.model.Bank;

import java.util.List;

public interface BankDao {
    @Query("select * from banks")
    List<Bank> getAll();

    @Insert
    void insert(Bank bank);
}
