package com.firstcitybank.trustbank.model;

public record Client (
        String clientCode,
        String nameOrTitle,
        String clientTypeCode,
        String socialRankCode,
        String districtCode,
        Boolean isBlocked) {
}
