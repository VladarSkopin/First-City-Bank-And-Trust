package org.skopintsev.models.api.factory;

import org.skopintsev.models.api.Client;
import org.skopintsev.util.GeneratorBuilder;


public class ClientFactory {

    public static Client generateClient(
            String clientTypeCode,
            String rankCode,
            String districtCode,
            String subSectorCode,
            boolean isBlocked) {
        return Client.builder()
                        .clientCode(GeneratorBuilder.generateTestCode())
                        .nameOrTitle(GeneratorBuilder.generateString(10))
                        .clientTypeCode(clientTypeCode)
                        .socialRankCode(rankCode)
                        .districtCode(districtCode)
                        .subSectorCode(subSectorCode)
                        .isBlocked(isBlocked)
                        .build();
    }
}
