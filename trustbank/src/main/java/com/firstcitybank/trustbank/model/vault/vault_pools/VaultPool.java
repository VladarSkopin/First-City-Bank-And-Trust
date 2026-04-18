package com.firstcitybank.trustbank.model.vault.vault_pools;

import java.time.LocalDateTime;

public record VaultPool(
        Long id,
        String vaultPoolName,
        Boolean isArchived,
        String currencyCode,
        String sectorCode,
        Long amountFrom,
        Long amountTo,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {}
