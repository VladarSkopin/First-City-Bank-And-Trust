package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.sector.SubSector;
import com.firstcitybank.trustbank.service.SubSectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.SUB_SECTORS_PATH;

@RestController
@RequestMapping(path = SUB_SECTORS_PATH)
public class SubSectorController {

    private final SubSectorService subSectorService;

    public SubSectorController(SubSectorService subSectorService) {
        this.subSectorService = subSectorService;
    }

    @GetMapping
    public List<SubSector> getAllSubSectors() {
        return subSectorService.getSubSectors();
    }

    @PostMapping
    public void addSubSector(@RequestBody SubSector subSector) {
        subSectorService.addNewSubSector(subSector);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteSubSector(@PathVariable("code") String code) {
        subSectorService.deleteSubSector(code);
    }

}
