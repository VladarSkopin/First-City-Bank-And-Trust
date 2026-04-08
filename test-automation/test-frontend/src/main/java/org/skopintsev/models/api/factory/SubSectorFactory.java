package org.skopintsev.models.api.factory;

import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class SubSectorFactory {

    public static List<SubSector> generateSubSectorList(String sectorCode) {
        return List.of(
                SubSector.builder()
                        .subSectorCode(GeneratorBuilder.generateTestCode())
                        .subSectorName(GeneratorBuilder.generateString(15))
                        .description(GeneratorBuilder.generateString(50))
                        .sectorCode(sectorCode)
                        .build()
        );
    }
}
