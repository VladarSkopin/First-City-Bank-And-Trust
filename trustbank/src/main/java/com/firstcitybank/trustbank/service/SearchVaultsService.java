package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.helper.VaultValidator;
import com.firstcitybank.trustbank.model.Vault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchVaultsService  {

    private final VaultDao vaultDao;

    public SearchVaultsService(VaultDao vaultDao) {
        this.vaultDao = vaultDao;
    }

    public List<Vault> getVaultsByCurrencyCode(String currencyCode) {
        VaultValidator.validateCurrencyCode(currencyCode);
        return vaultDao.selectVaultsByCurrencyCode(currencyCode);
    }


}
