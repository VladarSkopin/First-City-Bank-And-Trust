package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.exception.BusinessRuleException;
import com.firstcitybank.trustbank.exception.NotFoundException;
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
        if (vaultCode == null || vaultCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Vault code cannot be null or empty");
        }

        String normalizedCode = vaultCode.trim().toUpperCase();

        // Check if vault exists
        Vault vault = vaultDao.selectVaultByCode(normalizedCode)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Vault with code '%s' not found", vaultCode)
                ));

        validateVaultForDeletion(vault);

        int rowsDeleted = vaultDao.deleteVault(normalizedCode);

        if (rowsDeleted != 1) {
            throw new IllegalStateException(
                    String.format("Failed to delete vault '%s'. Expected 1 row affected, got %d",
                            vaultCode, rowsDeleted)
            );
        }


    }

    private void validateVaultForDeletion(Vault vault) {
        // Check if vault has any amount
        if (vault.amount().signum() > 0) {
            throw new BusinessRuleException(
                    String.format("Cannot delete vault '%s' because it contains amount: %s",
                            vault.vaultCode(), vault.amount())
            );
        }

        // Check if vault is archived (optional business rule)
        if (!vault.isArchived()) {
            throw new BusinessRuleException(
                    String.format("Vault '%s' must be archived before deletion", vault.vaultCode())
            );
        }
    }

}
