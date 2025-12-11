package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Vault;

import java.util.List;
import java.util.Optional;

public interface VaultDao {
    List<Vault> selectVaults();
    List<Vault> selectVaultsByClientName(String clientName);
    List<Vault> selectVaultsByClientCode(String clientCode);
    List<Vault> selectNobilityVaults();
    List<Vault> selectForeignVaults();
    List<Vault> selectGoldenVaults();
    int insertVault(Vault vault);
    int insertAmount(Integer amountToInsert);
    int withdrawAmount(Integer amountToWithdraw);
    boolean existsByName(String clientTypeName);
    boolean existsByCode(String clientTypeCode);
    int deleteVault(String clientTypeCode);
    Optional<Vault> selectVaultByCode(String clientTypeCode);
}
