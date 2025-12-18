package com.firstcitybank.trustbank.model;

import java.math.BigInteger;

public record VaultOperationRequest(
        String vaultCode,
        BigInteger amount,
        String operationName  // "INSERT" or "WITHDRAW"
) {}