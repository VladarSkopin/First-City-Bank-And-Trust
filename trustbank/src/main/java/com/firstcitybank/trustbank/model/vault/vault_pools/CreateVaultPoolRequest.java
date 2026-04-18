package com.firstcitybank.trustbank.model.vault.vault_pools;

import java.time.LocalDateTime;

public record CreateVaultPoolRequest(
        String vaultPoolName,

        Boolean isArchived,  // optional, defaults to false in service

        String currencyCode,

        String sectorCode,

        Long amountFrom,

        Long amountTo,  // optional, can be null

        LocalDateTime createdFrom,

        LocalDateTime createdTo  // optional, defaults to CURRENT_TIMESTAMP in DB
) {}
