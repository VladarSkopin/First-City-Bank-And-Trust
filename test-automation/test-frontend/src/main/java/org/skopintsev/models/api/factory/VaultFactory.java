package org.skopintsev.models.api.factory;

import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.util.GeneratorBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class VaultFactory {

    public static Vault generateVault(
            String clientCode,
            String currencyCode,
            boolean isArchived) {
        return Vault.builder()
                    .vaultCode(GeneratorBuilder.generateTestCode())
                    .clientCode(clientCode)
                    .amount(BigInteger.valueOf(GeneratorBuilder.generateAmount()))
                    .currencyCode(currencyCode)
                    .isArchived(isArchived)
                    .createdAt(LocalDateTime.now().minusMonths(1))
                    .modifiedAt(LocalDateTime.now().minusDays(1))
                    .build();
    }
}
