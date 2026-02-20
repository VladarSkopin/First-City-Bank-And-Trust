package org.skopintsev.model.sectors;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.GeneratorBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubSector {
    String subSectorCode;
    String subSectorName;
    @Builder.Default String description = GeneratorBuilder.generateString(50);
    String sectorCode;
}
