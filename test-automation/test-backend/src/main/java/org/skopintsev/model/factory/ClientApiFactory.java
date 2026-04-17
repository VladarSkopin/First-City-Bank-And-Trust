package org.skopintsev.model.factory;

import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.client.Client;


public class ClientApiFactory {

    public static Client defaultClientApiRequest(
            String clientTypeCode, String socialRankCode, String districtCode, String subSectorCode) {

        return Client.builder()
                .clientCode(GeneratorBuilder.generateTestCode())
                .nameOrTitle(GeneratorBuilder.generateString(12))
                .clientTypeCode(clientTypeCode)
                .socialRankCode(socialRankCode)
                .districtCode(districtCode)
                .isBlocked(false)
                .subSectorCode(subSectorCode)
                .build();
    }

    public static Client codeClientApiRequest(
            String clientCode, String clientTypeCode, String socialRankCode, String districtCode, String subSectorCode) {

        return Client.builder()
                .clientCode(clientCode)
                .nameOrTitle(GeneratorBuilder.generateString(12))
                .clientTypeCode(clientTypeCode)
                .socialRankCode(socialRankCode)
                .districtCode(districtCode)
                .isBlocked(false)
                .subSectorCode(subSectorCode)
                .build();
    }

    public static Client nameOrTitleClientApiRequest(
            String nameOrTitle, String clientTypeCode, String socialRankCode, String districtCode, String subSectorCode) {

        return Client.builder()
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
