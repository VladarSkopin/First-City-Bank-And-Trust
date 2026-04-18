package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPool;

import java.util.List;
import java.util.Optional;

public interface VaultPoolsDao {
    int insertVaultPool(VaultPool vaultPool);
    Optional<VaultPool> selectVaultPoolById(Long id);
    Optional<VaultPool> selectVaultPoolByName(String vaultPoolName);
    boolean existsByName(String vaultPoolName);
    int deleteVaultPoolByName(String vaultPoolName);
    List<VaultPool> selectAllVaultPools();
}
