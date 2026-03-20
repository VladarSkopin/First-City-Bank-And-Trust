package org.skopintsev.models.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vault {
    String vaultCode;
    String clientCode;
    BigInteger amount;
    String currencyCode;
    Boolean isArchived;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
}
