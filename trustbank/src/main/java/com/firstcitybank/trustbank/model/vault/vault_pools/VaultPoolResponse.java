package com.firstcitybank.trustbank.model.vault.vault_pools;

import java.time.LocalDateTime;

public record VaultPoolResponse(
        String vaultPoolName,
        Boolean isArchived,
        String currencyCode,
        String sectorCode,
        Long amountFrom,
        Long amountTo,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
    public static VaultPoolResponse from(VaultPool vaultPool) {
        return new VaultPoolResponse(
                vaultPool.vaultPoolName(),
                vaultPool.isArchived(),
                vaultPool.currencyCode(),
                vaultPool.sectorCode(),
                vaultPool.amountFrom(),
                vaultPool.amountTo(),
                vaultPool.createdFrom(),
                vaultPool.createdTo()
        );
    }
}
