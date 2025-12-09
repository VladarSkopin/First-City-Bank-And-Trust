package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.Client;
import com.firstcitybank.trustbank.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CLIENTS_PATH;
import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;

@RestController
@RequestMapping(path = CLIENTS_PATH)
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getClients();
    }

    @PostMapping
    public void addClient(@RequestBody Client client) {
        clientService.addNewClient(client);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteClient(@PathVariable("code") String code) {
        clientService.deleteClient(code);
    }

}
