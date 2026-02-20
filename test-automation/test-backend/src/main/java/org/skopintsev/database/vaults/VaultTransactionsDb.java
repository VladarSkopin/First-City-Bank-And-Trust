package org.skopintsev.database.vaults;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultTransactionsDb {
    Integer transactionId;
    String vaultCode;
    String operationType;  // 'INSERT', 'WITHDRAW'
    BigInteger amount;
    BigInteger newBalance;
    LocalDateTime transactionTime;
}
