package org.skopintsev.models.api;

import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class DistrictFactory {

    public static List<District> generateDistrictsList() {
        return List.of(
                District.builder()
                        .districtCode(GeneratorBuilder.generateTestCode())
                        .districtName(GeneratorBuilder.generateString(10))
                        .build()
        );
    }

}
