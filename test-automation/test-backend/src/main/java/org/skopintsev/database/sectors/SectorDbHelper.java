package org.skopintsev.database.sectors;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorDbHelper {

    private static SectorDb mapRow(ResultSet rs) {
        try {
            return SectorDb.builder()
                    .sectorCode(rs.getString("sector_code"))
                    .sectorName(rs.getString("sector_name"))
                    .description(rs.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping SectorDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select sector by code: {sectorCode}")
    public static SectorDb selectSectorByCode(String sectorCode) {
        String query = "SELECT sector_code, sector_name, description FROM sectors WHERE sector_code = ?";
        return DatabaseHelper.queryForObject(query, SectorDbHelper::mapRow, sectorCode);
    }

    @Step("Insert new sector: {sectorDb}")
    public static int insertSector(SectorDb sectorDb) {
        String query = """
                INSERT INTO sectors (sector_code, sector_name, description)
                VALUES (?, ?, ?)
                """;
        return DatabaseHelper.executeUpdate(
                query,
                sectorDb.getSectorCode(),
                sectorDb.getSectorName(),
                sectorDb.getDescription()
        );
    }

    @Step("Delete all test sectors.")
    public static void deleteAllTestSectors() {
        String query = "DELETE FROM sectors WHERE sector_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get sectors count.")
    public static int getSectorsCount() {
        String query = "SELECT COUNT(*) FROM sectors";
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
