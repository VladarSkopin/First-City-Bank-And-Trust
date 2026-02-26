package search.search_clients;

import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.clients.SearchClientsApiAssertions;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.transport.GetApiReqHelper;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CountClientsByFieldTest extends BaseSearchClientsTest {

    // todo: generate clients with different ranks, client types, sectors

    public void countClientsByRankTest() {
        int clientsDbCount = ClientDbHelper.getClientsCount();

        int clientsCount = GetApiReqHelper.countClientsByRankAndValidate("", SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }

    public void countClientsByClientTypeTest() {
        int clientsDbCount = ClientDbHelper.getClientsCount();

        int clientsCount = GetApiReqHelper.countClientsByClientTypeAndValidate("", SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }

    public void countClientsBySectorTest() {
        int clientsDbCount = ClientDbHelper.getClientsCount();

        int clientsCount = GetApiReqHelper.countClientsBySectorAndValidate("", SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }
}
