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
    public List<Vault> getVaultsByCurrencyCode(@PathVariable String currencyCode) {
        return searchVaultsService.getVaultsByCurrencyCode(currencyCode);
    }

    @GetMapping("/by-client-code/{clientCode}")
    public List<Vault> getVaultsByClientCode(@PathVariable String clientCode) {
        return searchVaultsService.getVaultsByClientCode(clientCode);
    }

    @GetMapping("/by-client-rank/{rankCode}")
    public List<Vault> getVaultsByClientRank(@PathVariable String rankCode) {
        return searchVaultsService.getVaultsByClientRank(rankCode);
    }

    @GetMapping("/by-client-name/{clientName}")
    public List<Vault> getVaultsByClientName(@PathVariable String clientName) {
        return searchVaultsService.getVaultsByClientName(clientName);
    }

    @GetMapping("/by-client-type/{clientTypeCode}")
    public List<Vault> getVaultsByClientType(@PathVariable String clientTypeCode) {
        return searchVaultsService.getVaultsByClientTypeCode(clientTypeCode);
    }

    @GetMapping("/by-client-sector/{sectorCode}")
    public List<Vault> getVaultsByClientSector(@PathVariable String sectorCode) {
        return searchVaultsService.getVaultsByClientSector(sectorCode);
    }

}
