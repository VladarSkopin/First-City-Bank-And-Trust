package com.firstcitybank.trustbank.district;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/districts")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping
    public List<District> getDistricts() {
        return districtService.getDistricts();
    }

    @PostMapping
    public void addDistrict(@RequestBody District district) {
        districtService.addNewDistrict(district);
    }

    @DeleteMapping("{code}")
    public void deleteDistrict(@PathVariable("code") String code) {
        districtService.deleteDistrict(code);
    }
}
