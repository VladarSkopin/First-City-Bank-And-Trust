package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Vault;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface VaultDao {
    List<Vault> selectVaults();
    List<Vault> selectVaultsByClientName(String clientName);
    List<Vault> selectVaultsByClientCode(String clientCode);
    List<Vault> selectVaultsByCurrencyCode(String currencyCode);
    List<Vault> selectVaultsByClientRank(String rankCode);
    List<Vault> selectVaultsByClientType(String typeCode);
    int insertVault(Vault vault);
    int insertAmount(String vaultCode, BigInteger amountToInsert);
    int withdrawAmount(String vaultCode, BigInteger amountToWithdraw);
    boolean existsByCode(String vaultCode);
    int deleteVault(String vaultCode);
    Optional<Vault> selectVaultByCode(String vaultCode);
}
