package org.skopintsev.model.vaults.vaultstats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchParams {
    String searchBy; // "SECTOR" or "SUBSECTOR"
    String searchString;
}
