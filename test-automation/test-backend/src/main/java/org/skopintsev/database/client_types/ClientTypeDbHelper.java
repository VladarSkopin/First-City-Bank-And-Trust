package org.skopintsev.database.client_types;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.CommonDatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientTypeDbHelper {

    private static ClientTypeDb mapRow(ResultSet rs) {
        try {
            return ClientTypeDb.builder()
                    .clientTypeCode(rs.getString("client_type_code"))
                    .clientTypeName(rs.getString("client_type_name"))
                    .description(rs.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ClientType from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select client type by code: {clientTypeCode}")
    public static ClientTypeDb selectClientTypeByCode(String clientTypeCode) {
        String query = "SELECT client_type_code, client_type_name, description FROM client_types WHERE client_type_code = ?";
        return CommonDatabaseHelper.queryForObject(query, ClientTypeDbHelper::mapRow, clientTypeCode);
    }

    @Step("Insert new client type: {clientTypeDb}")
    public static int insertClientType(ClientTypeDb clientTypeDb) {
        String query = """
            INSERT INTO client_types (client_type_code, client_type_name, description)
            VALUES (?, ?, ?)
            """;
        return CommonDatabaseHelper.executeUpdate(
                query,
                clientTypeDb.getClientTypeCode(),
                clientTypeDb.getClientTypeName(),
                clientTypeDb.getDescription()
        );
    }

    @Step("Delete client type by code: {clientTypeCode}")
    public static void deleteClientType(String clientTypeCode) {
        String query = "DELETE FROM client_types WHERE client_type_code = ?";
        CommonDatabaseHelper.executeUpdate(query, clientTypeCode);
    }

    @Step("Delete all test client types.")
    public static void deleteAllTestClientTypes() {
        String query = "DELETE FROM client_types WHERE client_type_code LIKE 'TEST-%'";
        CommonDatabaseHelper.executeUpdate(query);
    }

    @Step("Get client types count.")
    public static int getClientTypesCount() {
        String query = "SELECT COUNT(*) FROM client_types";
        Integer count = CommonDatabaseHelper.queryForObject(query,
                rs -> {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error getting count", e);
                    }
                }
        );
        return count != null ? count : 0;
    }
}
