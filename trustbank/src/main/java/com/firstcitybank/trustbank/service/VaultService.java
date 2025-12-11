package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.model.Vault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaultService {

    private final VaultDao vaultDao;

    public VaultService(VaultDao vaultDao) {
        this.vaultDao = vaultDao;
    }

    public List<Vault> getVaults() {
        return vaultDao.selectVaults();
    }

    public void addNewVault(Vault vault) {
        // todo: vault insertion
    }

    public void deleteVault(String vaultCode) {
        // todo: check if vault still has some amount
    }

}
