package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

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
}
