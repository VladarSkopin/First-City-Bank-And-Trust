package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.vault.Vault;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface VaultDao {
    List<Vault> selectVaults();
    List<Vault> selectVaultsByClientName(String clientName);
    List<Vault> selectVaultsByCurrencyCode(String currencyCode);
    List<Vault> selectVaultsByClientCode(String clientCode);
    List<Vault> selectVaultsByClientRank(String rankCode);
    List<Vault> selectVaultsByClientType(String typeCode);
    List<Vault> selectVaultsByClientSector(String sectorCode);
    int insertVault(Vault vault);
    int insertAmount(String vaultCode, BigInteger amountToInsert);
    int withdrawAmount(String vaultCode, BigInteger amountToWithdraw);
    boolean existsByCode(String vaultCode);
    int deleteVault(String vaultCode);
    Optional<Vault> selectVaultByCode(String vaultCode);
    List<Vault> selectVaultsBySectorWithLimit(String sectorCode, int limit);
    List<Vault> selectVaultsBySubSectorWithLimit(String subSectorCode, int limit);
}
