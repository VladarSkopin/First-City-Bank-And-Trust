package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.PrivilegeLevel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRank {
    String rankCode;
    String rankName;
    @Builder.Default String description = "";
    @Builder.Default String privilegeLevel = PrivilegeLevel.STANDARD.getText();
    @Builder.Default String regulations = "";
}
