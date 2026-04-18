package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.VaultPoolsDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.vault.vault_pools.CreateVaultPoolRequest;
import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPool;
import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPoolResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaultPoolsService {

    private final VaultPoolsDao vaultPoolsDao;

    public VaultPoolsService(VaultPoolsDao vaultPoolsDao) {
        this.vaultPoolsDao = vaultPoolsDao;
    }

    @Transactional
    public VaultPool createVaultPool(CreateVaultPoolRequest request) {
        // 1. Validate nulls
        if (request.vaultPoolName() == null || request.vaultPoolName().trim().isEmpty()) {
            throw new IllegalArgumentException("Vault pool name is required");
        }
        if (request.currencyCode() == null || request.currencyCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code is required");
        }
        if (request.sectorCode() == null || request.sectorCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code is required");
        }
        if (request.amountFrom() == null || request.amountFrom() < 0) {
            throw new IllegalArgumentException("'Amount from' must be >= 0");
        }
        if (request.amountTo() != null && request.amountTo() < 0) {
            throw new IllegalArgumentException("'Amount to' must be >= 0");
        }
        if (request.amountTo() != null && request.amountTo() < request.amountFrom()) {
            throw new IllegalArgumentException("'Amount to' cannot be less than 'amount from'");
        }
        if (request.createdFrom() == null) {
            throw new IllegalArgumentException("'Created from' timestamp is required");
        }

        // 2. Check uniqueness of vault pool name
        boolean exists = vaultPoolsDao.existsByName(request.vaultPoolName().trim());
        if (exists) {
            throw new IllegalStateException("Vault pool with name '" + request.vaultPoolName() + "' already exists");
        }

        boolean isArchived = request.isArchived() != null ? request.isArchived() : false;

        // 3. Build VaultPool object
        VaultPool vaultPool = new VaultPool(
                null, // id will be generated
                request.vaultPoolName().trim(),
                isArchived,
                request.currencyCode().trim().toUpperCase(),
                request.sectorCode().trim().toUpperCase(),
                request.amountFrom(),
                request.amountTo(),
                request.createdFrom(),
                request.createdTo() != null ? request.createdTo() : LocalDateTime.now() // fallback to now if not provided
        );

        // 4. Insert and get generated ID
        int rows = vaultPoolsDao.insertVaultPool(vaultPool);
        if (rows != 1) {
            throw new IllegalStateException("Failed to insert vault pool");
        }

        // 5. Retrieve the created pool by its unique name
        return vaultPoolsDao.selectVaultPoolByName(vaultPool.vaultPoolName())
                .orElseThrow(() -> new IllegalStateException("Created vault pool not found"));
    }

    @Transactional
    public void deleteVaultPool(String vaultPoolName) {
        if (vaultPoolName == null || vaultPoolName.trim().isEmpty()) {
            throw new IllegalArgumentException("Vault pool name cannot be null or empty");
        }

        String normalizedName = vaultPoolName.trim();

        // Check if exists
        boolean exists = vaultPoolsDao.existsByName(normalizedName);
        if (!exists) {
            throw new NotFoundException(
                    String.format("Vault pool with name '%s' not found", vaultPoolName)
            );
        }

        int rowsDeleted = vaultPoolsDao.deleteVaultPoolByName(normalizedName);
        if (rowsDeleted != 1) {
            throw new IllegalStateException(
                    String.format("Failed to delete vault pool '%s'. Expected 1 row, got %d",
                            vaultPoolName, rowsDeleted)
            );
        }
    }

    public List<VaultPoolResponse> getAllVaultPools() {
        List<VaultPool> vaultPools = vaultPoolsDao.selectAllVaultPools();
        return vaultPools.stream()
                .map(VaultPoolResponse::from)
                .collect(Collectors.toList());
    }
}
