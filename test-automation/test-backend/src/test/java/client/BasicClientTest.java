package client;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.clients.ClientApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.clients.ClientDbAssertions;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.model.Client;
import org.skopintsev.model.factory.ClientApiFactory;
import org.skopintsev.transport.api.ClientsApiClient;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicClientTest extends BaseClientTest {

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Client object into the Database and checks API for the new added client.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientDbTest() {
        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        int rowsInserted = ClientDbHelper.insertClient(clientDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<Client> clients = ClientsApiClient.getClientsAndValidate(SC_OK);
        ClientApiAssertions.checkNotNullClients(clients);

        Client client = clients
                .stream()
                .filter(c -> c.getClientCode().equals(clientDb.getClientCode()))
                .findFirst()
                .orElse(null);
        ClientDbAssertions.checkClientField("nameOrTitle", client.getNameOrTitle(), clientDb.getNameOrTitle());
        ClientDbAssertions.checkClientField("clientTypeCode", client.getClientTypeCode(), BASE_CLIENT_TYPE_CODE);
        ClientDbAssertions.checkClientField("socialRankCode", client.getSocialRankCode(), BASE_SOCIAL_RANK_CODE);
        ClientDbAssertions.checkClientField("districtCode", client.getDistrictCode(), BASE_DISTRICT_CODE);
        ClientDbAssertions.checkClientField("isBlocked", client.getIsBlocked(), clientDb.getIsBlocked());
        ClientDbAssertions.checkClientField("subSectorCode", client.getSubSectorCode(), BASE_SUB_SECTOR_CODE);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Client object and checks Database for the new added client.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientApiTest() {
        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientsApiClient.saveClientAndValidate(client, SC_OK);

        List<Client> clients = ClientsApiClient.getClientsAndValidate(SC_OK);
        ClientApiAssertions.checkNotNullClients(clients);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);
        ClientDbAssertions.checkClientField("nameOrTitle", clientDb.getNameOrTitle(), client.getNameOrTitle());
        ClientDbAssertions.checkClientField("clientTypeCode", clientDb.getClientTypeCode(), BASE_CLIENT_TYPE_CODE);
        ClientDbAssertions.checkClientField("socialRankCode", clientDb.getSocialRankCode(), BASE_SOCIAL_RANK_CODE);
        ClientDbAssertions.checkClientField("districtCode", clientDb.getDistrictCode(), BASE_DISTRICT_CODE);
        ClientDbAssertions.checkClientField("isBlocked", clientDb.getIsBlocked(), client.getIsBlocked());
        ClientDbAssertions.checkClientField("subSectorCode", clientDb.getSubSectorCode(), BASE_SUB_SECTOR_CODE);
    }
}
