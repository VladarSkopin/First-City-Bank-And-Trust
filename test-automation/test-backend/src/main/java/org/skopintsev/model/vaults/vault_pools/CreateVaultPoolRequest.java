package org.skopintsev.model.vaults.vault_pools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateVaultPoolRequest {
    String vaultPoolName;
    Boolean isArchived;
    String currencyCode;
    String sectorCode;
    Long amountFrom;
    Long amountTo;
    LocalDateTime createdFrom;
    LocalDateTime createdTo;
}
