package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.helper.VaultValidator;
import com.firstcitybank.trustbank.model.vault.Vault;
import com.firstcitybank.trustbank.model.vault.VaultOperationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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

        VaultValidator.validateVaultForDeletion(vault);

        int rowsDeleted = vaultDao.deleteVault(normalizedCode);

        if (rowsDeleted != 1) {
            throw new IllegalStateException(
                    String.format("Failed to delete vault '%s'. Expected 1 row affected, got %d",
                            vaultCode, rowsDeleted)
            );
        }
    }

    @Transactional
    public Vault executeVaultOperation(VaultOperationRequest operationRequest) {
        // 1. Validate input
        VaultValidator.validateOperationRequest(operationRequest);

        String normalizedVaultCode = operationRequest.vaultCode().trim().toUpperCase();

        // 2. Fetch and validate vault
        Vault vault = vaultDao.selectVaultByCode(normalizedVaultCode)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Vault with code '%s' not found", operationRequest.vaultCode())
                ));

        // 3. Validate vault for operation
        VaultValidator.validateVaultForOperation(vault, operationRequest);

        // 4. Execute the operation
        Vault updatedVault = switch (operationRequest.operationName().trim().toUpperCase()) {
            case "INSERT" -> executeInsertOperation(vault, operationRequest.amount());
            case "WITHDRAW" -> executeWithdrawOperation(vault, operationRequest.amount());
            default -> throw new IllegalArgumentException(
                    String.format("Invalid operation: '%s'. Must be 'INSERT' or 'WITHDRAW'",
                            operationRequest.operationName())
            );
        };

        return updatedVault;
    }

    private Vault executeInsertOperation(Vault vault, BigInteger amountToInsert) {
        // Calculate new amount
        BigInteger newAmount = vault.amount().add(amountToInsert);

        // Update vault in database
        int rowsUpdated = vaultDao.insertAmount(vault.vaultCode().trim().toUpperCase(), amountToInsert);

        if (rowsUpdated != 1) {
            throw new IllegalStateException(
                    String.format("Failed to insert amount into vault '%s'", vault.vaultCode())
            );
        }

        // Return updated vault
        return new Vault(
                vault.vaultCode(),
                vault.clientCode(),
                newAmount,
                vault.createdAt(),
                vault.modifiedAt(), // This will be updated by the database trigger
                vault.currencyCode(),
                vault.isArchived()
        );
    }

    private Vault executeWithdrawOperation(Vault vault, BigInteger amountToWithdraw) {
        // Calculate new amount
        BigInteger newAmount = vault.amount().subtract(amountToWithdraw);

        // Update vault in database
        int rowsUpdated = vaultDao.withdrawAmount(vault.vaultCode().trim().toUpperCase(), amountToWithdraw);

        if (rowsUpdated != 1) {
            throw new IllegalStateException(
                    String.format("Failed to withdraw amount from vault '%s'", vault.vaultCode())
            );
        }

        // Return updated vault
        return new Vault(
                vault.vaultCode(),
                vault.clientCode(),
                newAmount,
                vault.createdAt(),
                vault.modifiedAt(), // This will be updated by the database trigger
                vault.currencyCode(),
                vault.isArchived()
        );
    }

}
