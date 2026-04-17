package org.skopintsev.vaults_page;

import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.*;
import org.skopintsev.models.api.client.Client;
import org.skopintsev.models.api.client.ClientType;
import org.skopintsev.models.api.factory.*;
import org.skopintsev.models.api.sector.Sector;
import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

public class BaseVaultTest extends BaseTest {

    List<Currency> BASE_CURRENCIES_LIST = CurrencyFactory.generateMultipleCurrenciesList();
    List<District> BASE_DISTRICTS_LIST = DistrictFactory.generateDistrictsList();
    List<SocialRank> BASE_SOCIAL_RANKS_LIST = SocialRankFactory.generateSocialRanksList();
    List<Sector> BASE_SECTORS_LIST = SectorFactory.generateSectorList();
    List<SubSector> BASE_SUB_SECTORS_LIST = SubSectorFactory.generateSubSectorList(
            BASE_SECTORS_LIST.get(0).getSectorCode());
    List<ClientType> BASE_CLIENT_TYPES_LIST = ClientTypeFactory.generateClientTypesList();
    List<Client> BASE_CLIENTS_LIST = List.of(
            ClientFactory.generateClient(
                    BASE_CLIENT_TYPES_LIST.get(0).getClientTypeCode(),
                    null,
                    null,
                    null,
                    true),
            ClientFactory.generateClient(
                    BASE_CLIENT_TYPES_LIST.get(1).getClientTypeCode(),
                    BASE_SOCIAL_RANKS_LIST.get(0).getRankCode(),
                    BASE_DISTRICTS_LIST.get(0).getDistrictCode(),
                    BASE_SUB_SECTORS_LIST.get(0).getSubSectorCode(),
                    false));
    List<Vault> BASE_VAULTS_LIST = List.of(
            VaultFactory.generateVault(
                    BASE_CLIENTS_LIST.get(0).getClientCode(),
                    BASE_CURRENCIES_LIST.get(0).getCurrencyCode(),
                    false),
            VaultFactory.generateVault(
                    BASE_CLIENTS_LIST.get(1).getClientCode(),
                    BASE_CURRENCIES_LIST.get(1).getCurrencyCode(),
                    false),
            VaultFactory.generateVault(
                    BASE_CLIENTS_LIST.get(1).getClientCode(),
                    BASE_CURRENCIES_LIST.get(2).getCurrencyCode(),
                    true));


    @BeforeEach
    public void openVaultsPage() {
        PostApiResponseHelper.stubGetVaults(BASE_VAULTS_LIST);
        PostApiResponseHelper.stubGetClients(BASE_CLIENTS_LIST);
        PostApiResponseHelper.stubGetClientTypes(BASE_CLIENT_TYPES_LIST);
        PostApiResponseHelper.stubGetSocialRanks(BASE_SOCIAL_RANKS_LIST);
        PostApiResponseHelper.stubGetDistricts(BASE_DISTRICTS_LIST);
        PostApiResponseHelper.stubGetCurrencies(BASE_CURRENCIES_LIST);
        PostApiResponseHelper.stubGetSubSectors(BASE_SUB_SECTORS_LIST);

        OpenUrl.openVaultsPage();
    }
}
