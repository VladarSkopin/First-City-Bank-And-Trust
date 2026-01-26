package org.skopintsev.database.districts;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistrictDbHelper {

    private static DistrictDb mapRow(ResultSet rs) {
        try {
            return DistrictDb.builder()
                    .districtCode(rs.getString("district_code"))
                    .districtName(rs.getString("district_name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping DistrictDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select district by code: {districtCode}")
    public static DistrictDb selectDistrictByCode(String districtCode) {
        String query = "SELECT district_code, district_name FROM districts WHERE district_code = ?";
        return DatabaseHelper.queryForObject(query, DistrictDbHelper::mapRow, districtCode);
    }

    @Step("Insert new district: {districtDb}")
    public static int insertDistrict(DistrictDb districtDb) {
        String query = """
                INSERT INTO districts (district_code, district_name)
                VALUES (?, ?)
                """;
        return DatabaseHelper.executeUpdate(
                query,
                districtDb.getDistrictCode(),
                districtDb.getDistrictName()
        );
    }

    @Step("Delete all test districts.")
    public static void deleteAllTestDistricts() {
        String query = "DELETE FROM districts WHERE district_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get districts count.")
    public static int getDistrictsCount() {
        String query = "SELECT COUNT(*) FROM districts";
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
