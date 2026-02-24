package search.search_clients;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;
import org.skopintsev.assertions.api.clients.SearchClientsApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Client;
import org.skopintsev.transport.GetApiReqHelper;

import java.util.List;
import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchClientsByFieldTest extends BaseSearchClientsTest {

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Client object into the Database and uses API to search for clients.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchClientsBySocialRankTest() {
        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        int rowsInserted = ClientDbHelper.insertClient(clientDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<Client> clientsFound = GetApiReqHelper.searchClientsByRankAndValidate(BASE_SOCIAL_RANK_CODE, SC_OK);
        List<Client> clientsExpected = List.of(
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
        SearchClientsApiAssertions.checkSearchClientsResponseMatchesExpected(clientsFound, clientsExpected);
    }

    public void searchClientsByClientTypeTest() {

    }

    public void searchClientsBySubSectorTest() {

    }

    public void searchClientsBySectorTest() {

    }

    public void searchClientsByDistrictTest() {

    }

    public void searchClientsByMultipleQueryParamsTest() {

    }


    // negative tests

    public void searchClientsByInvalidSocialRankTest() {

    }

    public void searchClientsByInvalidClientTypeTest() {

    }

    public void searchClientsByInvalidSubSectorTest() {

    }

    public void searchClientsByInvalidSectorTest() {

    }

    public void searchClientsByInvalidDistrictTest() {

    }

    public void searchClientsByInvalidMultipleQueryParamsTest() {

    }


    private static Stream<Arguments> invalidTestCodeProvider() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
