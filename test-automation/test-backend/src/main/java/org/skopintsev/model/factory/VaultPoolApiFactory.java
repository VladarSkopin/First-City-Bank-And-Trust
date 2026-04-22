package org.skopintsev.model.factory;

import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.vaults.vault_pools.CreateVaultPoolRequest;

import java.time.LocalDateTime;

public class VaultPoolApiFactory {

    public static CreateVaultPoolRequest generateDefaultCreateVaultPoolRequest(
            String currencyCode, String sectorCode) {

        return CreateVaultPoolRequest.builder()
                .vaultPoolName(GeneratorBuilder.generateTestName())
                .isArchived(false)
                .currencyCode(currencyCode)
                .sectorCode(sectorCode)
                .amountFrom(0L)
                .amountTo(100L)
                .createdFrom(LocalDateTime.now().minusYears(1))
                .createdTo(LocalDateTime.now())
                .build();
    }
}
