package org.skopintsev.model;

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
    @Builder.Default Boolean isBlocked = false;
    String subSectorCode;
}
