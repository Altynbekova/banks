package com.altynbekova.banks.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.altynbekova.banks.model.Bank;

@Database(entities = {Bank.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BankDao bankDao();
}
