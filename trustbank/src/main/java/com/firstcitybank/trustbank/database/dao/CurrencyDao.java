package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyDao {
    List<Currency> selectCurrencies();
    int insertCurrency(Currency currency);
    boolean existsByName(String currencyName);
    boolean existsByCode(String currencyCode);
    int deleteCurrency(String currencyCode);
    Optional<Currency> selectCurrencyByCode(String currencyCode);
}
