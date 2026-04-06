package com.firstcitybank.trustbank.model.vault;

public record VaultStatsRequest(
        int limit,
        SearchParams searchParams,
        String systemName
) {}
