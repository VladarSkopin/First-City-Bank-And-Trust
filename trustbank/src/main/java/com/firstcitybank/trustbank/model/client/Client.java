package com.firstcitybank.trustbank.model.client;

public record Client (
        String clientCode,
        String nameOrTitle,
        String clientTypeCode,
        String socialRankCode,
        String districtCode,
        Boolean isBlocked,
        String subSectorCode) {
}
