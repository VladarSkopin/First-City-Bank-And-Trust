package org.skopintsev.database.social_ranks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.PrivilegeLevel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialRankDb {
    String rankCode;
    String rankName;
    @Builder.Default String description = "";
    @Builder.Default String privilegeLevel = PrivilegeLevel.STANDARD.getText();
    @Builder.Default String regulations = "";
}
