package com.firstcitybank.trustbank.district;

import java.util.List;
import java.util.Optional;

public interface DistrictDao {
    List<District> selectDistricts();
    int insertDistrict(District district);
    boolean existsByName(String districtName);
    boolean existsByCode(String districtCode);
    int deleteDistrict(String districtCode);
    Optional<District> selectDistrictByCode(String districtCode);
}
