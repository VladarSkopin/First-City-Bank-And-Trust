package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.Client;
import com.firstcitybank.trustbank.service.SearchClientsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.SEARCH_CLIENTS_PATH;

@RestController
@RequestMapping(path = SEARCH_CLIENTS_PATH)
public class SearchClientsController {

    private final SearchClientsService searchClientsService;

    public SearchClientsController(SearchClientsService searchClientsService) {
        this.searchClientsService = searchClientsService;
    }

    @GetMapping("/by-social-rank/{socialRankCode}")
    public List<Client> getClientsBySocialRank(@PathVariable String socialRankCode) {
        return searchClientsService.getClientsBySocialRank(socialRankCode);
    }

    @GetMapping("/by-client-type/{clientTypeCode}")
    public List<Client> getClientsByClientType(@PathVariable String clientTypeCode) {
        return searchClientsService.getClientsByClientType(clientTypeCode);
    }

    @GetMapping("/by-sub-sector/{subSectorCode}")
    public List<Client> getClientsBySubSector(@PathVariable String subSectorCode) {
        return searchClientsService.getClientsBySubSector(subSectorCode);
    }

    @GetMapping("/by-sector/{sectorCode}")
    public List<Client> getClientsBySector(@PathVariable String sectorCode) {
        return searchClientsService.getClientsBySector(sectorCode);
    }

    @GetMapping
    public List<Client> searchClients(
            @RequestParam(required = false) String socialRankCode,
            @RequestParam(required = false) String clientTypeCode,
            @RequestParam(required = false) String subSectorCode,
            @RequestParam(required = false) String sectorCode,
            @RequestParam(required = false) String districtCode,
            @RequestParam(required = false) Boolean isBlocked) {

        return searchClientsService.searchClients(
                socialRankCode, clientTypeCode, subSectorCode, sectorCode, districtCode, isBlocked
        );
    }

}
