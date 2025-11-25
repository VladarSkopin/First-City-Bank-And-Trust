package com.firstcitybank.trustbank.currency;

import com.firstcitybank.trustbank.exception.NotFoundException;
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

        if (currency.currencyName() == null || currency.currencyName().trim().isEmpty()) {
            throw new IllegalArgumentException("Currency name cannot be empty");
        }

        // 2. todo: Check if currency exists

        // 3. todo: Insert new currency

        // 4. todo: Check if insertion was successful
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
