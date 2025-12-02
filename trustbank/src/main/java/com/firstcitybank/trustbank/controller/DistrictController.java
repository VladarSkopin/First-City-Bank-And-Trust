package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.service.DistrictService;
import com.firstcitybank.trustbank.model.District;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.DISTRICTS_PATH;

@RestController
@RequestMapping(path = DISTRICTS_PATH)
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

    @DeleteMapping(CODE_PATH)
    public void deleteDistrict(@PathVariable("code") String code) {
        districtService.deleteDistrict(code);
    }
}
