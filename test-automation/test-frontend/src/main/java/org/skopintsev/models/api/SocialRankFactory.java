package org.skopintsev.models.api;

import org.skopintsev.enums.PrivilegeLevel;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class SocialRankFactory {

    public static List<SocialRank> generateSocialRanksList() {
        return List.of(
                SocialRank.builder()
                        .rankCode(GeneratorBuilder.generateTestCode())
                        .rankName(GeneratorBuilder.generateString(12))
                        .privilegeLevel(PrivilegeLevel.STANDARD.getText())
                        .description(GeneratorBuilder.generateString(20))
                        .regulations(GeneratorBuilder.generateString(50))
                        .build()
        );
    }

}
