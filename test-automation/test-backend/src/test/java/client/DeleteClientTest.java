package client;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.clients.ClientDbAssertions;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteClientTest extends BaseClientTest {

    @Test
    @Tag("regression")
    @Description("Test creates a new client in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteClientTest() {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        ClientDb clientDb = ClientDbFactory.defaultClientDbRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        int rowsInserted = ClientDbHelper.insertClient(clientDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        String extractedClientCode = clientDb.getClientCode();

        DeleteApiReqHelper.deleteClientAndValidate(extractedClientCode, SC_OK);

        clientDb = ClientDbHelper.selectClientByCode(extractedClientCode);
        ClientDbAssertions.checkClientPresence(clientDb, false);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientCode = {0}")
    @MethodSource("clientCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a client:
        1) with code = null,
        2) with code = empty string,
        3) with code = whitespace,
        4) a client that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteClientNegativeTest(String clientCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        DeleteApiReqHelper.deleteClientAndValidate(clientCode, SC_NOT_FOUND);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    private static Stream<Arguments> clientCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
