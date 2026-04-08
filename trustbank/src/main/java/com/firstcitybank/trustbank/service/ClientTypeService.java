package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.ClientTypeDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.client.ClientType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientTypeService {

    private final ClientTypeDao clientTypeDao;

    public ClientTypeService(ClientTypeDao clientTypeDao) {
        this.clientTypeDao = clientTypeDao;
    }

    public List<ClientType> getClientTypes() {
        return clientTypeDao.selectClientTypes();
    }

    public void addNewClientType(ClientType clientType) {
        // 1. Validate input
        if (clientType == null) {
            throw new IllegalArgumentException("Client Type data cannot be null");
        }

        if (clientType.clientTypeCode() == null || clientType.clientTypeCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Client Type code is required");
        }

        if (clientType.clientTypeName() == null || clientType.clientTypeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client Type name is required");
        }

        // 2. Check if client type exists
        boolean clientTypeExists = clientTypeDao.existsByName(clientType.clientTypeName());
        if (clientTypeExists) {
            throw new IllegalStateException("Client Type with name '" + clientType.clientTypeName() + "' already exists");
        }

        // 3. Insert new client type
        Integer rowsAffected = clientTypeDao.insertClientType(clientType);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Client Type");
        }
    }

    public void deleteClientType(String clientTypeCode) {
        if (clientTypeCode == null || clientTypeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Client Type code cannot be null or empty");
        }

        String normalizedCode = clientTypeCode.trim().toUpperCase();

        Optional<ClientType> clientTypes = clientTypeDao.selectClientTypeByCode(normalizedCode);
        clientTypes.ifPresentOrElse(clientType -> {
            int result = clientTypeDao.deleteClientType(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Client Type");
            }
        }, () -> {
            throw new NotFoundException(String.format("Client Type with code %s not found", normalizedCode));
        });
    }
}
