package org.skopintsev.models.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubSector {
    String subSectorCode;
    String subSectorName;
    String description;
    String sectorCode;
}
