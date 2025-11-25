package com.firstcitybank.trustbank.currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyDao {
    List<Currency> selectCurrencies();
    void insertCurrency(Currency currency);
    boolean existsByName(String currencyName);
    boolean existsByCode(String currencyCode);
    int deleteCurrency(String currencyCode);
    Optional<Currency> selectCurrencyByCode(String currencyCode);
}
