package com.firstcitybank.trustbank.helper;

import com.firstcitybank.trustbank.helper.enums.ClientType;
import com.firstcitybank.trustbank.helper.enums.PrivilegeLevel;

public class Utils {

    public static String validateAndGetPrivilegeLevel(String privilegeLevel) {
        try {
            // Convert string to enum to validate it exists
            PrivilegeLevel level = PrivilegeLevel.valueOf(privilegeLevel.toUpperCase());
            return level.name(); // Return standardized enum name
        } catch (IllegalArgumentException e) {
            // Try matching by text
            for (PrivilegeLevel pl : PrivilegeLevel.values()) {
                if (pl.getText().equalsIgnoreCase(privilegeLevel)) {
                    return pl.name();
                }
            }
            throw new IllegalArgumentException("Invalid privilege level: " + privilegeLevel);
        }
    }

    public static String validateAndGetClientTypeName(String clientTypeName) {
        try {
            // Convert string to enum to validate it exists
            ClientType clientType = ClientType.valueOf(clientTypeName.toUpperCase());
            return clientType.getText();
        } catch (IllegalArgumentException e) {
            // Try matching by text
            for (ClientType ct : ClientType.values()) {
                if (ct.getText().equalsIgnoreCase(clientTypeName)) {
                    return ct.getText();
                }
            }
            throw new IllegalArgumentException("Invalid client type name: " + clientTypeName);
        }
    }

}
