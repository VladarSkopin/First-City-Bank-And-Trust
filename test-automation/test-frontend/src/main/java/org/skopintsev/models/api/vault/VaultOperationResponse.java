package org.skopintsev.models.api.vault;

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
public class VaultOperationResponse {
    String vaultCode;
    String clientCode;
    Long amount;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    String currencyCode;
    Boolean isArchived;
}
