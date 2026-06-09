package com.altynbekova.banks.ui;

import com.altynbekova.banks.db.model.Bank;

public interface OnBankClickListener {
    void onBankClick(Bank bank, int position);
}
