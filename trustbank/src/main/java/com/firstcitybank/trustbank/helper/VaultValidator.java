package com.firstcitybank.trustbank.helper;

import com.firstcitybank.trustbank.exception.BusinessRuleException;
import com.firstcitybank.trustbank.model.Vault;
import com.firstcitybank.trustbank.model.VaultOperationRequest;

import java.math.BigInteger;


public class VaultValidator {

    public static void validateVaultForDeletion(Vault vault) {
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

    public static void validateOperationRequest(VaultOperationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Operation request cannot be null");
        }

        if (request.vaultCode() == null || request.vaultCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Vault code is required");
        }

        if (request.amount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        if (request.amount().signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (request.operationName() == null || request.operationName().trim().isEmpty()) {
            throw new IllegalArgumentException("Operation name is required");
        }

        String operation = request.operationName().toUpperCase();
        if (!operation.equals("INSERT") && !operation.equals("WITHDRAW")) {
            throw new IllegalArgumentException("Operation must be 'INSERT' or 'WITHDRAW'");
        }
    }

    public static void validateVaultForOperation(Vault vault, VaultOperationRequest request) {
        // Check if vault is archived
        if (vault.isArchived()) {
            throw new BusinessRuleException(
                    String.format("Cannot perform operations on archived vault '%s'", vault.vaultCode())
            );
        }

        // Additional validation for withdrawal
        if (request.operationName().equalsIgnoreCase("WITHDRAW")) {
            if (vault.amount().compareTo(request.amount()) < 0) {
                throw new BusinessRuleException(
                        String.format("Insufficient funds in vault '%s'. " +
                                        "Available: %s, Requested: %s",
                                vault.vaultCode(), vault.amount(), request.amount())
                );
            }
        }

        // Check for very large amounts (optional)
        BigInteger maxSingleOperation = new BigInteger("1000000000"); // 1 billion
        if (request.amount().compareTo(maxSingleOperation) > 0) {
            throw new BusinessRuleException(
                    String.format("Amount %s exceeds maximum single operation limit of %s",
                            request.amount(), maxSingleOperation)
            );
        }
    }
}
