package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.Vault;
import com.firstcitybank.trustbank.service.SearchVaultsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.SEARCH_VAULTS_PATH;


@RestController
@RequestMapping(path = SEARCH_VAULTS_PATH)
public class SearchVaultController {

    private final SearchVaultsService searchVaultsService;

    public SearchVaultController(SearchVaultsService searchVaultsService) {
        this.searchVaultsService = searchVaultsService;
    }

    @GetMapping("/by-currency/{currencyCode}")
    public List<Vault> getClientsBySector(@PathVariable String currencyCode) {
        return searchVaultsService.getVaultsByCurrencyCode(currencyCode);
    }

}
