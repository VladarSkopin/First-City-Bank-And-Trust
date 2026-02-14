package org.skopintsev.database.clients;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDbHelper {

    private static ClientDb mapRow(ResultSet rs) {
        try {
            return ClientDb.builder()
                    .clientCode(rs.getString("client_code"))
                    .nameOrTitle(rs.getString("name_or_title"))
                    .clientTypeCode(rs.getString("client_type_code"))
                    .socialRankCode(rs.getString("social_rank_code"))
                    .districtCode(rs.getString("district_code"))
                    .isBlocked(rs.getBoolean("is_blocked"))
                    .subSectorCode(rs.getString("sub_sector_code"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping Client from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select client by code: {clientCode}")
    public static ClientDb selectClientByCode(String clientCode) {
        String query = "SELECT client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code FROM clients WHERE client_code = ?";
        return DatabaseHelper.queryForObject(query, ClientDbHelper::mapRow, clientCode);
    }

    @Step("Insert new client: {clientDb}")
    public static int insertClient(ClientDb clientDb) {
        String query = """
            INSERT INTO clients (client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        return DatabaseHelper.executeUpdate(
                query,
                clientDb.getClientCode(),
                clientDb.getNameOrTitle(),
                clientDb.getClientTypeCode(),
                clientDb.getSocialRankCode(),
                clientDb.getDistrictCode(),
                clientDb.getIsBlocked(),
                clientDb.getSubSectorCode()
        );
    }

    @Step("Delete client by code: {clientCode}")
    public static void deleteClient(String clientCode) {
        String query = "DELETE FROM clients WHERE client_code = ?";
        DatabaseHelper.executeUpdate(query, clientCode);
    }

    @Step("Delete all test clients.")
    public static void deleteAllTestClients() {
        String query = "DELETE FROM clients WHERE client_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get client count.")
    public static int getClientsCount() {
        String query = "SELECT COUNT(*) FROM clients";
        Integer count = DatabaseHelper.queryForObject(query,
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
