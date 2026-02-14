package org.skopintsev.database.factory;

import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.helper.GeneratorBuilder;

public class ClientDbFactory {

    public static ClientDb defaultClientDbRequest(
            String clientTypeCode, String socialRankCode, String districtCode, String subSectorCode) {

        return ClientDb.builder()
                .clientCode(GeneratorBuilder.generateTestCode())
                .nameOrTitle(GeneratorBuilder.generateString(12))
                .clientTypeCode(clientTypeCode)
                .socialRankCode(socialRankCode)
                .districtCode(districtCode)
                .isBlocked(false)
                .subSectorCode(subSectorCode)
                .build();
    }

    public static ClientDb nameOrTitleClientDbRequest(
            String nameOrTitle, String clientTypeCode, String socialRankCode, String districtCode, String subSectorCode) {

        return ClientDb.builder()
                .clientCode(GeneratorBuilder.generateTestCode())
                .nameOrTitle(nameOrTitle)
                .clientTypeCode(clientTypeCode)
                .socialRankCode(socialRankCode)
                .districtCode(districtCode)
                .isBlocked(false)
                .subSectorCode(subSectorCode)
                .build();
    }

}
