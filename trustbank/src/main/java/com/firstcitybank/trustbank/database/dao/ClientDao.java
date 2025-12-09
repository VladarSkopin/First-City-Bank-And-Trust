package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    List<Client> selectClients();
    int insertClient(Client client);
    boolean existsByName(String clientName);
    boolean existsByCode(String clientCode);
    int deleteClient(String clientCode);
    Optional<Client> selectClientByCode(String clientCode);
}
