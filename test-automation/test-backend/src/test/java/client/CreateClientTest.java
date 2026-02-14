package client;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.clients.ClientDbAssertions;
import org.skopintsev.database.factory.ClientDbFactory;
import org.skopintsev.database.clients.ClientDb;
import org.skopintsev.database.clients.ClientDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Client;
import org.skopintsev.model.factory.ClientApiFactory;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientTest extends BaseClientTest {

    static final String CLIENT_CODE = GeneratorBuilder.generateTestCode();
    final String NAME_OR_TITLE = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a client that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientAlreadyExistsTest() {
        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);
        int clientsCountOld = ClientDbHelper.getClientsCount();

        PostApiReqHelper.saveClientAndValidate(client, SC_SERVER_ERROR);
        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] invalidClientCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a client:
        1) with client code = null,
        2) with client code = empty string,
        3) with client code = whitespace.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientWithInvalidCodeTest(String invalidClientCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.codeClientApiRequest(
                invalidClientCode,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_SERVER_ERROR);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientCode = {0}")
    @MethodSource("clientCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a client:
        1) with client code that needs to be trimmed,
        2) with client code that should be modified to upper case,
        3) with client code in mixed case.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientCodeTrimUppercaseTest(String clientCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();
        String clientCodeTrimmedUppercase = clientCode.trim().toUpperCase();

        Client client = ClientApiFactory.codeClientApiRequest(
                clientCode,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(clientCodeTrimmedUppercase);
        ClientDbAssertions.checkClientPresence(clientDb, true);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew - 1, clientsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a client with name or title already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientNameOrTitleAlreadyExistsTest() {
        ClientDb clientDb = ClientDbFactory.nameOrTitleClientDbRequest(
                NAME_OR_TITLE,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        int rowsInserted = ClientDbHelper.insertClient(clientDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.nameOrTitleClientApiRequest(
                NAME_OR_TITLE,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_SERVER_ERROR);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] nameOrTitle = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a client:
        1) with name or title = null,
        2) with name or title = empty string,
        3) with name or title = whitespace.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientWithInvalidNameTest(String invalidNameOrTitle) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.nameOrTitleClientApiRequest(
                invalidNameOrTitle,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_SERVER_ERROR);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a client with name or title that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientNameOrTitleTrimTest() {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        String clientNameOrTitleToTrim = " " + NAME_OR_TITLE + " ";
        String clientNameOrTitleTrimmed = clientNameOrTitleToTrim.trim();

        Client client = ClientApiFactory.nameOrTitleClientApiRequest(
                clientNameOrTitleToTrim,
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);
        ClientDbAssertions.checkClientField("nameOrTitle", clientDb.getNameOrTitle(), clientNameOrTitleTrimmed);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew - 1, clientsCountOld);
    }


    private static Stream<Arguments> clientCodeRequest() {
        return Stream.of(
                Arguments.of(" " + CLIENT_CODE + " "),
                Arguments.of(CLIENT_CODE.toLowerCase()),
                Arguments.of(CLIENT_CODE.charAt(0) + CLIENT_CODE.substring(1).toLowerCase())
        );
    }
}
