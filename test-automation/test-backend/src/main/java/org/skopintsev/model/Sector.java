package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.GeneratorBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sector {
    String sectorCode;
    String sectorName;
    @Builder.Default String description = GeneratorBuilder.generateString(50);
}
