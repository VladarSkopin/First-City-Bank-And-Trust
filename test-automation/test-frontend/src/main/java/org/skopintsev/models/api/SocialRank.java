package org.skopintsev.models.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRank {
    String rankCode;
    String rankName;
    String description;
    String privilegeLevel;
    String regulations;
}
