package org.skopintsev.database.sectors.subsectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubSectorDb {
    String subSectorCode;
    String subSectorName;
    String description;
    String sectorCode;
}
