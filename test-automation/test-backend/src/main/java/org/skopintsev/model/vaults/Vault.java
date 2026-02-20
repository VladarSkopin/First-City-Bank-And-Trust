package org.skopintsev.model.vaults;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vault {
    String vaultCode;
    String clientCode;
    BigInteger amount;
    String currencyCode;
    Boolean isArchived;
}
