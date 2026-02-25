package search.search_clients;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.skopintsev.assertions.api.clients.SearchClientsApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.model.Client;
import org.skopintsev.transport.GetApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchClientsByFieldTest extends BaseSearchClientsTest {

    List<Client> clientsExpected;

    @BeforeEach
    public void beforeEach() {
        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        int rowsInserted = ClientDbHelper.insertClient(clientDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        clientsExpected = List.of(
                Client.builder()
                        .clientCode(clientDb.getClientCode())
                        .nameOrTitle(clientDb.getNameOrTitle())
                        .clientTypeCode(BASE_CLIENT_TYPE_CODE)
                        .socialRankCode(BASE_SOCIAL_RANK_CODE)
                        .districtCode(BASE_DISTRICT_CODE)
                        .isBlocked(false)
                        .subSectorCode(BASE_SUB_SECTOR_CODE)
                        .build()
        );
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by social rank.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsBySocialRankTest() {
        List<Client> clientsFound = GetApiReqHelper.searchClientsByRankAndValidate(BASE_SOCIAL_RANK_CODE, SC_OK);
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by client type.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsByClientTypeTest() {
        List<Client> clientsFound = GetApiReqHelper.searchClientsByClientTypeAndValidate(BASE_CLIENT_TYPE_CODE, SC_OK);
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by sub-sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsBySubSectorTest() {
        List<Client> clientsFound = GetApiReqHelper.searchClientsBySubSectorAndValidate(BASE_SUB_SECTOR_CODE, SC_OK);
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by sector.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsBySectorTest() {
        List<Client> clientsFound = GetApiReqHelper.searchClientsBySectorAndValidate(BASE_SECTOR_CODE, SC_OK);
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by district.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsByDistrictTest() {
        List<Client> clientsFound = GetApiReqHelper.searchClientsByDistrictAndValidate(BASE_DISTRICT_CODE, SC_OK);
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for clients by multiple parameters.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsByMultipleQueryParamsTest() {
        // todo: generate more different clients


    }
}
