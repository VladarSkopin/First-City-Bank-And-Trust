package org.skopintsev.database.social_ranks;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.CommonDatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SocialRankDbHelper {

    private static SocialRankDb mapRow(ResultSet rs) {
        try {
            return SocialRankDb.builder()
                    .rankCode(rs.getString("rank_code"))
                    .rankName(rs.getString("rank_name"))
                    .description(rs.getString("description"))
                    .privilegeLevel(rs.getString("privilege_level"))
                    .regulations(rs.getString("regulations"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping SocialRankDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select social rank by code: {rankCode}")
    public static SocialRankDb selectSocialRankByCode(String rankCode) {
        String query = "SELECT rank_code, rank_name, description, privilege_level, regulations FROM social_ranks WHERE rank_code = ?";
        return CommonDatabaseHelper.queryForObject(query, SocialRankDbHelper::mapRow, rankCode);
    }

    @Step("Insert new social rank: {socialRankDb}")
    public static int insertSocialRank(SocialRankDb socialRankDb) {
        String query = """
            INSERT INTO social_ranks (rank_code, rank_name, description, privilege_level, regulations)
            VALUES (?, ?, ?, ?, ?)
            """;
        return CommonDatabaseHelper.executeUpdate(
                query,
                socialRankDb.getRankCode(),
                socialRankDb.getRankName(),
                socialRankDb.getDescription(),
                socialRankDb.getPrivilegeLevel(),
                socialRankDb.getRegulations()
        );
    }

    @Step("Delete social rank by code: {rankCode}")
    public static void deleteSocialRank(String rankCode) {
        String query = "DELETE FROM social_ranks WHERE rank_code = ?";
        CommonDatabaseHelper.executeUpdate(query, rankCode);
    }

    @Step("Delete all test social ranks.")
    public static void deleteAllTestSocialRanks() {
        String query = "DELETE FROM social_ranks WHERE rank_code LIKE 'TEST-%'";
        CommonDatabaseHelper.executeUpdate(query);
    }

    @Step("Get social ranks count.")
    public static int getSocialRanksCount() {
        String query = "SELECT COUNT(*) FROM social_ranks";
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
