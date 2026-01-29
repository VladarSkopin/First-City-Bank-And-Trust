package org.skopintsev.database.sectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.GeneratorBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectorDb {
    String sectorCode;
    String sectorName;
    @Builder.Default String description = GeneratorBuilder.generateString(50);
}
