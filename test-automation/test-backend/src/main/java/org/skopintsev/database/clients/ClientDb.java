package org.skopintsev.database.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDb {
    String clientCode;
    String nameOrTitle;
    String clientTypeCode;
    String socialRankCode;
    String districtCode;
    @Builder.Default Boolean isBlocked = false;
    String subSectorCode;
}
