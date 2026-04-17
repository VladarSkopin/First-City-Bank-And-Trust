package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.client.ClientType;
import com.firstcitybank.trustbank.service.ClientTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CLIENT_TYPES_PATH;
import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;

@RestController
@RequestMapping(path = CLIENT_TYPES_PATH)
public class ClientTypeController {

    private final ClientTypeService clientTypeService;

    public ClientTypeController(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    @GetMapping
    public List<ClientType> getClientTypes() {
        return clientTypeService.getClientTypes();
    }

    @PostMapping
    public void addClientType(@RequestBody ClientType clientType) {
        clientTypeService.addNewClientType(clientType);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteClientType(@PathVariable("code") String code) {
        clientTypeService.deleteClientType(code);
    }
}
