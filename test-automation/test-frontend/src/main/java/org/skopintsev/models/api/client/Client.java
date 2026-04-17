package org.skopintsev.models.api.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {
    String clientCode;
    String nameOrTitle;
    String clientTypeCode;
    String socialRankCode;
    String districtCode;
    Boolean isBlocked;
    String subSectorCode;
}
