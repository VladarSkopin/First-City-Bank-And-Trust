package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Vault;

import java.math.BigInteger;
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
    int insertAmount(String vaultCode, BigInteger amountToInsert);
    int withdrawAmount(String vaultCode, BigInteger amountToWithdraw);
    boolean existsByCode(String vaultCode);
    int deleteVault(String vaultCode);
    Optional<Vault> selectVaultByCode(String vaultCode);
}
