package org.skopintsev.model.vaults.vaultstats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaultStatsResponse {
    int totalCount;
    List<VaultSummary> vaults;
    LocalDateTime dateReceived;
    String systemName;
}
