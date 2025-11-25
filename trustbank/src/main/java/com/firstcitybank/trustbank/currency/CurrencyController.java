package com.firstcitybank.trustbank.currency;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/currencies")
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

    @DeleteMapping
    public void deleteCurrency(@PathVariable("code") String code) {
        currencyService.deleteCurrency(code);
    }

}
