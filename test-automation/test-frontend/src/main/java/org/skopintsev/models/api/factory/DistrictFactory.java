package org.skopintsev.models.api.factory;

import org.skopintsev.models.api.District;
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
