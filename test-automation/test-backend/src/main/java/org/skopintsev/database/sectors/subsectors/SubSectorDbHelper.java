package org.skopintsev.database.sectors.subsectors;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.CommonDatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubSectorDbHelper {

    private static SubSectorDb mapRow(ResultSet rs) {
        try {
            return SubSectorDb.builder()
                    .subSectorCode(rs.getString("sub_sector_code"))
                    .subSectorName(rs.getString("sub_sector_name"))
                    .description(rs.getString("description"))
                    .sectorCode(rs.getString("sector_code"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping SubSectorDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select sub-sector by code: {subSectorCode}")
    public static SubSectorDb selectSubSectorByCode(String subSectorCode) {
        String query = "SELECT sub_sector_code, sub_sector_name, description, sector_code FROM sub_sectors WHERE sub_sector_code = ?";
        return CommonDatabaseHelper.queryForObject(query, SubSectorDbHelper::mapRow, subSectorCode);
    }

    @Step("Insert new sub-sector: {subSectorDb}")
    public static int insertSubSector(SubSectorDb subSectorDb) {
        String query = """
                INSERT INTO sub_sectors (sub_sector_code, sub_sector_name, description, sector_code)
                VALUES (?, ?, ?, ?)
                """;
        return CommonDatabaseHelper.executeUpdate(
                query,
                subSectorDb.getSubSectorCode(),
                subSectorDb.getSubSectorName(),
                subSectorDb.getDescription(),
                subSectorDb.getSectorCode()
        );
    }

    @Step("Delete sub-sector by code: {subSectorCode}")
    public static void deleteSubSector(String subSectorCode) {
        String query = "DELETE FROM sub_sectors WHERE sub_sector_code = ?";
        CommonDatabaseHelper.executeUpdate(query, subSectorCode);
    }

    @Step("Delete all test sub-sectors.")
    public static void deleteAllTestSubSectors() {
        String query = "DELETE FROM sub_sectors WHERE sub_sector_code LIKE 'TEST-%'";
        CommonDatabaseHelper.executeUpdate(query);
    }

    @Step("Get sub-sectors count.")
    public static int getSubSectorsCount() {
        String query = "SELECT COUNT(*) FROM sub_sectors";
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
