package com.firstcitybank.trustbank.model.vault;

import java.math.BigInteger;

public record VaultSummary(
        String vaultCode,
        String currency,   // from currencyCode
        BigInteger amount
) { }
