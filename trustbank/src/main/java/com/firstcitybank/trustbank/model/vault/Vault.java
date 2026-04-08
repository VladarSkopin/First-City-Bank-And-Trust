package com.firstcitybank.trustbank.model.vault;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record Vault (
        String vaultCode,
        String clientCode,
        BigInteger amount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String currencyCode,
        Boolean isArchived
) { }
