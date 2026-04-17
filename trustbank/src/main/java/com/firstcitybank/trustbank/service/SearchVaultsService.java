package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.helper.ClientValidator;
import com.firstcitybank.trustbank.helper.VaultValidator;
import com.firstcitybank.trustbank.model.vault.Vault;
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

    public List<Vault> getVaultsByClientCode(String clientCode) {
        ClientValidator.validateClientCode(clientCode);
        return vaultDao.selectVaultsByClientCode(clientCode);
    }

    public List<Vault> getVaultsByClientRank(String rankCode) {
        ClientValidator.validateSocialRankCode(rankCode);
        return vaultDao.selectVaultsByClientRank(rankCode);
    }

    public List<Vault> getVaultsByClientName(String clientName) {
        return vaultDao.selectVaultsByClientName(clientName);
    }

    public List<Vault> getVaultsByClientTypeCode(String clientTypeCode) {
        ClientValidator.validateClientTypeCode(clientTypeCode);
        return vaultDao.selectVaultsByClientType(clientTypeCode);
    }

    public List<Vault> getVaultsByClientSector(String sectorCode) {
        ClientValidator.validateSectorCode(sectorCode);
        return vaultDao.selectVaultsByClientSector(sectorCode);
    }


}
