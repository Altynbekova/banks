package com.altynbekova.banks.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.altynbekova.banks.db.model.Bank;

import java.util.List;

@Dao
public interface BankDao {
    @Query("select * from banks")
    List<Bank> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Bank bank);
}
