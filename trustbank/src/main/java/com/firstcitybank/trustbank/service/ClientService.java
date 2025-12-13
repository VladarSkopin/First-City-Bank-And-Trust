package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.ClientDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getClients() {
        return clientDao.selectClients();
    }

    public void addNewClient(Client client) {
        // 1. Validate input
        if (client == null) {
            throw new IllegalArgumentException("Client data cannot be null");
        }

        if (client.clientCode() == null || client.clientCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Client code is required");
        }

        if (client.nameOrTitle() == null || client.nameOrTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name or title is required");
        }

        if (client.clientTypeCode() == null || client.clientTypeCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Client type code is required");
        }

        if (client.isBlocked() == null) {
            throw new IllegalArgumentException("Client isBlocked field is required");
        }

        // District code validation (can be null, but if provided must be valid)
        if (client.districtCode() != null && client.districtCode().trim().isEmpty()) {
            throw new IllegalArgumentException("District code cannot be empty if provided");
        }

        // 2. Check if client exists
        boolean clientExists = clientDao.existsByName(client.nameOrTitle());
        if (clientExists) {
            throw new IllegalStateException("Client with name or title '" + client.nameOrTitle() + "' already exists");
        }

        // 3. Insert new client
        Integer rowsAffected = clientDao.insertClient(client);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Client");
        }
    }

    public void deleteClient(String clientCode) {
        if (clientCode == null || clientCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Client code cannot be null or empty");
        }

        String normalizedCode = clientCode.trim().toUpperCase();

        Optional<Client> clients = clientDao.selectClientByCode(normalizedCode);
        clients.ifPresentOrElse(client -> {
            int result = clientDao.deleteClient(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Client");
            }
        }, () -> {
            throw new NotFoundException(String.format("Client with code %s not found", normalizedCode));
        });
    }

}
