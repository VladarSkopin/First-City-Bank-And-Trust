package org.skopintsev.models.api.factory;

import org.skopintsev.models.api.Sector;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class SectorFactory {

    public static List<Sector> generateSectorList() {
        return List.of(
                Sector.builder()
                        .sectorCode(GeneratorBuilder.generateTestCode())
                        .sectorName(GeneratorBuilder.generateString(15))
                        .description(GeneratorBuilder.generateString(50))
                        .build()
        );
    }
}
