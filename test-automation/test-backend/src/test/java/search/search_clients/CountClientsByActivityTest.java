package search.search_clients;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.clients.SearchClientsApiAssertions;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.transport.GetApiReqHelper;

import static org.skopintsev.constants.Constants.SC_OK;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountClientsByActivityTest extends BaseSearchClientsTest {

    @BeforeEach
    public void beforeEach() {
        ClientDb clientDbBlockedFirst = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDb clientDbBlockedSecond = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        ClientDb clientDbBlockedThird = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        clientDbBlockedFirst.setIsBlocked(true);
        clientDbBlockedSecond.setIsBlocked(true);
        clientDbBlockedThird.setIsBlocked(true);
        ClientDbHelper.insertClient(clientDbBlockedFirst);
        ClientDbHelper.insertClient(clientDbBlockedSecond);
        ClientDbHelper.insertClient(clientDbBlockedThird);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to count all clients.")
    @Severity(SeverityLevel.CRITICAL)
    public void countAllClientsTest() {
        int clientsDbCount = ClientDbHelper.getClientsCount();

        int clientsCount = GetApiReqHelper.countAllClientsAndValidate(SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to count active clients (isBlocked = false).")
    @Severity(SeverityLevel.CRITICAL)
    public void countActiveClientsTest() {
        int clientsDbCount = ClientDbHelper.getClientsCountByIsBlockedField(false);

        int clientsCount = GetApiReqHelper.countActiveClientsAndValidate(SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to count blocked clients (isBlocked = true).")
    @Severity(SeverityLevel.CRITICAL)
    public void countBlockedClientsTest() {
        int clientsDbCount = ClientDbHelper.getClientsCountByIsBlockedField(true);

        int clientsCount = GetApiReqHelper.countBlockedClientsAndValidate(SC_OK);
        SearchClientsApiAssertions.checkClientsCount(clientsDbCount, clientsCount);
    }
}
