package org.skopintsev.models.api.factory;

import org.skopintsev.enums.ClientTypeNameEnum;
import org.skopintsev.models.api.ClientType;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class ClientTypeFactory {

    public static List<ClientType> generateClientTypesList() {
        return List.of(
                ClientType.builder()
                        .clientTypeCode(GeneratorBuilder.generateTestCode())
                        .clientTypeName(ClientTypeNameEnum.UNK.getText())
                        .description(GeneratorBuilder.generateString(30))
                        .build(),
                ClientType.builder()
                        .clientTypeCode(GeneratorBuilder.generateTestCode())
                        .clientTypeName(ClientTypeNameEnum.INDV.getText())
                        .description(GeneratorBuilder.generateString(30))
                        .build()
        );
    }
}
