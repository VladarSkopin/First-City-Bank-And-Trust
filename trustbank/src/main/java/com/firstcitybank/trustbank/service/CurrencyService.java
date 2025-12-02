package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.CurrencyDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyDao currencyDao;

    public CurrencyService(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public List<Currency> getCurrencies() {
        return currencyDao.selectCurrencies();
    }

    public void addNewCurrency(Currency currency) {
        // 1. Validate input
        if (currency == null) {
            throw new IllegalArgumentException("Currency data cannot be null");
        }

        if (currency.currencyCode() == null || currency.currencyCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code is required");
        }

        if (currency.currencyName() == null || currency.currencyName().trim().isEmpty()) {
            throw new IllegalArgumentException("Currency name is required");
        }

        // 2. Check if currency exists
        boolean currencyExists = currencyDao.existsByName(currency.currencyName());
        if (currencyExists) {
            throw new IllegalStateException("Currency with name '" + currency.currencyName() + "' already exists");
        }

        // 3. Insert new currency
        Integer rowsAffected = currencyDao.insertCurrency(currency);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Currency");
        }
    }

    public void deleteCurrency(String currencyCode) {
        Optional<Currency> currencies = currencyDao.selectCurrencyByCode(currencyCode);
        currencies.ifPresentOrElse(currency -> {
            int result = currencyDao.deleteCurrency(currencyCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Currency");
            }
        }, () -> {
            throw new NotFoundException(String.format("Currency with code %s not found", currencyCode));
        });
    }

}
