package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.Sector;
import com.firstcitybank.trustbank.service.SectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.SECTORS_PATH;

@RestController
@RequestMapping(path = SECTORS_PATH)
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping
    public List<Sector> getAllSectors() {
        return sectorService.getSectors();
    }

    @PostMapping
    public void addSector(@RequestBody Sector sector) {
        sectorService.addNewSector(sector);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteSector(@PathVariable("code") String code) {
        sectorService.deleteSector(code);
    }
}
