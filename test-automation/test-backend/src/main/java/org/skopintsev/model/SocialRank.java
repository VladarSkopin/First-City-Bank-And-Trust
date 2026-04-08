package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.PrivilegeLevelEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRank {
    String rankCode;
    String rankName;
    @Builder.Default String description = "";
    @Builder.Default String privilegeLevel = PrivilegeLevelEnum.STANDARD.getText();
    @Builder.Default String regulations = "";
}
