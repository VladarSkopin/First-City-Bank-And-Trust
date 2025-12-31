package com.firstcitybank.trustbank.helper;

public class ClientValidator {

    public static void validateClientCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Client code cannot be null or empty");
        }
    }

    public static void validateSocialRankCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Social rank code cannot be null or empty");
        }
    }

    public static void validateClientTypeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Client type code cannot be null or empty");
        }
    }

    public static void validateSubSectorCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Sub-sector code cannot be null or empty");
        }
    }

    public static void validateSectorCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Sector code cannot be null or empty");
        }
    }

    public static void validateDistrictCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("District code cannot be null or empty");
        }
    }
}
