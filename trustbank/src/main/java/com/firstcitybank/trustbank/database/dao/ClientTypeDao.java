package com.firstcitybank.trustbank.database.dao;


import com.firstcitybank.trustbank.model.ClientType;

import java.util.List;
import java.util.Optional;

public interface ClientTypeDao {
    List<ClientType> selectClientTypes();
    int insertClientType(ClientType clientType);
    boolean existsByName(String clientTypeName);
    boolean existsByCode(String clientTypeCode);
    int deleteClientType(String clientTypeCode);
    Optional<ClientType> selectClientTypeByCode(String clientTypeCode);
}
