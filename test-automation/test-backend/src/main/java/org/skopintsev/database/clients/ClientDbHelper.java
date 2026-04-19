package org.skopintsev.database.clients;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.CommonDatabaseHelper;

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
        return CommonDatabaseHelper.queryForObject(query, ClientDbHelper::mapRow, clientCode);
    }

    @Step("Insert new client: {clientDb}")
    public static int insertClient(ClientDb clientDb) {
        String query = """
            INSERT INTO clients (client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        return CommonDatabaseHelper.executeUpdate(
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
        CommonDatabaseHelper.executeUpdate(query, clientCode);
    }

    @Step("Delete all test clients.")
    public static void deleteAllTestClients() {
        String query = "DELETE FROM clients WHERE client_code LIKE 'TEST-%'";
        CommonDatabaseHelper.executeUpdate(query);
    }

    @Step("Get clients count.")
    public static int getClientsCount() {
        String query = "SELECT COUNT(*) FROM clients";
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

    @Step("Get clients count with field is_blocked = {0}.")
    public static int getClientsCountByIsBlockedField(boolean isBlocked) {
        String query = "SELECT COUNT(*) FROM clients WHERE is_blocked = " + isBlocked;
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

    @Step("Get clients count with field social_rank_code = {0}.")
    public static int getClientsCountByRank(String rankCode) {
        String query = "SELECT COUNT(*) FROM clients WHERE social_rank_code = '" + rankCode + "'";
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

    @Step("Get clients count with field client_type_code = {0}.")
    public static int getClientsCountByClientType(String clientTypeCode) {
        String query = "SELECT COUNT(*) FROM clients WHERE client_type_code = '" + clientTypeCode+ "'";
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

    @Step("Get clients count with sector code = {0}.")
    public static int getClientsCountBySector(String sectorCode) {
        String query = """
        SELECT COUNT(*)
        FROM clients c
        JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code
        WHERE ss.sector_code = ?
        """;

        Integer count = CommonDatabaseHelper.queryForObject(query,
                rs -> {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error getting count", e);
                    }
                },
                sectorCode  // Pass the parameter safely
        );
        return count != null ? count : 0;
    }

}
