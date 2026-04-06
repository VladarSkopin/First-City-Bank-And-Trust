package com.firstcitybank.trustbank.model.vault;

public record SearchParams(
        String searchBy, // "SECTOR" or "SUBSECTOR"
        String searchString
) {}
