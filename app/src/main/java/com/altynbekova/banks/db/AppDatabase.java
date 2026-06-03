package com.altynbekova.banks.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.altynbekova.banks.db.dao.BankDao;
import com.altynbekova.banks.db.model.Bank;
import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Bank.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BankDao bankDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final String DB_NAME = "bank_db";
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                BankDao bankDao = INSTANCE.bankDao();
                List<Bank> banks = new ArrayList<>(
                        List.of(
                                new Bank("bank1", "address1", "active",
                                        new Point(55.030100, 82.920580)),
                                new Bank("bank2", "address2", "active",
                                        new Point(55.030200, 82.920500))
                        ));

                bankDao.insertAll(banks);
            });
        }
    };

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DB_NAME)
                            .addCallback(dbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
