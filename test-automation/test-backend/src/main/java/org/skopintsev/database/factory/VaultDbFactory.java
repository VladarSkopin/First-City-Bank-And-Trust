package org.skopintsev.database.factory;

import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.helper.GeneratorBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class VaultDbFactory {

    public static VaultDb defaultVaultDbRequest(
            String clientCode, String currencyCode) {

        return VaultDb.builder()
                .vaultCode(GeneratorBuilder.generateTestCode())
                .clientCode(clientCode)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .amount(BigInteger.valueOf(GeneratorBuilder.generateAmount()))
                .currencyCode(currencyCode)
                .isArchived(false)
                .build();
    }

    public static VaultDb amountVaultDbRequest(
            String clientCode, BigInteger amount, String currencyCode) {

        return VaultDb.builder()
                .vaultCode(GeneratorBuilder.generateTestCode())
                .clientCode(clientCode)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .amount(amount)
                .currencyCode(currencyCode)
                .isArchived(false)
                .build();
    }

}
