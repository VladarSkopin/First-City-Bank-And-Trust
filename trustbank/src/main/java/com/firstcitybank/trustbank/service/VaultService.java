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
        // 1. Validate input
        if (vault == null) {
            throw new IllegalArgumentException("Vault data cannot be null");
        }

        if (vault.vaultCode() == null || vault.vaultCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Vault code is required");
        }

        if (vault.clientCode() == null || vault.clientCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Vault client code is required");
        }

        if (vault.amount() == null) {
            throw new IllegalArgumentException("Amount field is required");
        }

        if (vault.amount().signum() < 0) {
            throw new IllegalArgumentException("Amount should be a positive number");
        }

        if (vault.currencyCode() == null || vault.currencyCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Vault currency code is required");
        }

        if (vault.isArchived() == null) {
            throw new IllegalArgumentException("Vault 'isArchived' field is required");
        }

        // 2. Check if vault exists
        boolean vaultExists = vaultDao.existsByCode(vault.vaultCode());
        if (vaultExists) {
            throw new IllegalStateException("Vault with code '" + vault.vaultCode() + "' already exists");
        }

        // 3. Insert new vault
        Integer rowsAffected = vaultDao.insertVault(vault);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Vault");
        }
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
