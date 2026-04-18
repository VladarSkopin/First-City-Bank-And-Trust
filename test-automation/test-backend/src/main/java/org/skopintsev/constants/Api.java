package org.skopintsev.constants;

public interface Api {

    String CURRENCIES = "/api/v1/currencies";

    String DISTRICTS = "/api/v1/districts";

    String SOCIAL_RANKS = "/api/v1/socialranks";

    String CLIENT_TYPES = "/api/v1/clienttypes";

    String SECTORS = "/api/v1/sectors";

    String SUB_SECTORS = "/api/v1/subsectors";

    String CLIENTS = "/api/v1/clients";

    String VAULTS = "/api/v1/vaults";

    String VAULT_STATS = "/api/v1/vaultStats";

    String VAULT_OPERATIONS = VAULTS + "/operations";

    String SEARCH_CLIENTS = CLIENTS + "/search";

    String SEARCH_VAULTS = VAULTS + "/search";

    String SEARCH_CLIENTS_BY_SOCIAL_RANK = SEARCH_CLIENTS + "/by-social-rank";

    String SEARCH_CLIENTS_BY_CLIENT_TYPE = SEARCH_CLIENTS + "/by-client-type";

    String SEARCH_CLIENTS_BY_SUB_SECTOR = SEARCH_CLIENTS + "/by-sub-sector";

    String SEARCH_CLIENTS_BY_SECTOR = SEARCH_CLIENTS + "/by-sector";

    String SEARCH_CLIENTS_BY_DISTRICT = SEARCH_CLIENTS + "/by-district";

    String COUNT_CLIENTS_BY_RANK = SEARCH_CLIENTS + "/count-by-rank";

    String COUNT_CLIENTS_BY_TYPE = SEARCH_CLIENTS + "/count-by-type";

    String COUNT_CLIENTS_BY_SECTOR = SEARCH_CLIENTS + "/count-by-sector";

    String COUNT_CLIENTS_ALL = SEARCH_CLIENTS + "/count-all";

    String COUNT_CLIENTS_ACTIVE = SEARCH_CLIENTS + "/count-active";

    String COUNT_CLIENTS_BLOCKED = SEARCH_CLIENTS + "/count-blocked";

    String SEARCH_VAULTS_BY_CURRENCY = SEARCH_VAULTS + "/by-currency";

    String SEARCH_VAULTS_BY_CLIENT_CODE = SEARCH_VAULTS + "/by-client-code";

    String SEARCH_VAULTS_BY_CLIENT_NAME = SEARCH_VAULTS + "/by-client-name";

    String SEARCH_VAULTS_BY_CLIENT_RANK = SEARCH_VAULTS + "/by-client-rank";

    String SEARCH_VAULTS_BY_CLIENT_TYPE = SEARCH_VAULTS + "/by-client-type";

    String SEARCH_VAULTS_BY_CLIENT_SECTOR = SEARCH_VAULTS + "/by-client-sector";

    String VAULT_POOLS = "/api/v1/vault-pools";

}
