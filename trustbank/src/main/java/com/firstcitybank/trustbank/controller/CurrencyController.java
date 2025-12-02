package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.service.CurrencyService;
import com.firstcitybank.trustbank.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.CURRENCIES_PATH;

@RestController
@RequestMapping(path = CURRENCIES_PATH)
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @PostMapping
    public void addCurrency(@RequestBody Currency currency) {
        currencyService.addNewCurrency(currency);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteCurrency(@PathVariable("code") String code) {
        currencyService.deleteCurrency(code);
    }

}
