package com.firstcitybank.trustbank.model.vault;

import java.math.BigInteger;

public record VaultOperationRequest(
        String vaultCode,
        BigInteger amount,
        String operationName  // "INSERT" or "WITHDRAW"
) {}