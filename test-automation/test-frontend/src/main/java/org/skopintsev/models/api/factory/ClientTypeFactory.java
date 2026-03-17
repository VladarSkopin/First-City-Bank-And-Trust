package org.skopintsev.models.api.factory;

import org.skopintsev.enums.ClientTypeName;
import org.skopintsev.models.api.ClientType;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class ClientTypeFactory {

    public static List<ClientType> generateClientTypesList() {
        return List.of(
                ClientType.builder()
                        .clientTypeCode(GeneratorBuilder.generateTestCode())
                        .clientTypeName(ClientTypeName.UNK.getText())
                        .description(GeneratorBuilder.generateString(30))
                        .build(),
                ClientType.builder()
                        .clientTypeCode(GeneratorBuilder.generateTestCode())
                        .clientTypeName(ClientTypeName.INDV.getText())
                        .description(GeneratorBuilder.generateString(30))
                        .build()
        );
    }
}
