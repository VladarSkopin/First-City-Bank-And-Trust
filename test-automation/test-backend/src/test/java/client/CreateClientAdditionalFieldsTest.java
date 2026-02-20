package client;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.clients.ClientDbAssertions;
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
public class CreateClientAdditionalFieldsTest extends BaseClientTest {

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with district code = null,
            2) with district code = empty string,
            3) with district code = whitespace,
            4) with district code absent on the database.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientInvalidDistrictCodeTest(String districtCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                districtCode,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, districtCode == null ? SC_OK : SC_SERVER_ERROR);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, districtCode == null ? true : false);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(districtCode == null ? clientsCountNew - 1 : clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with client type code = null,
            2) with client type code = empty string,
            3) with client type code = whitespace,
            4) with client type code absent on the database.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientInvalidClientTypeCodeTest(String clientTypeCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                clientTypeCode,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_SERVER_ERROR);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, false);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] subSectorCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with sub-sector code = null,
            2) with sub-sector code = empty string,
            3) with sub-sector code = whitespace,
            4) with sub-sector code absent on the database.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientInvalidSubSectorCodeTest(String subSectorCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                subSectorCode
        );
        PostApiReqHelper.saveClientAndValidate(client, subSectorCode == null ? SC_OK : SC_SERVER_ERROR);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, subSectorCode == null ? true : false);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(subSectorCode == null ? clientsCountNew - 1 : clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] socialRankCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with social rank code = null,
            2) with social rank code = empty string,
            3) with social rank code = whitespace,
            4) with social rank code absent on the database.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientInvalidSocialRankCodeTest(String socialRankCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                socialRankCode,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, socialRankCode == null ? SC_OK : SC_SERVER_ERROR);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, socialRankCode == null ? true : false);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(socialRankCode == null ? clientsCountNew - 1 : clientsCountNew, clientsCountOld);
    }

    @ParameterizedTest(name = "[{index}] districtCode = {0}")
    @MethodSource("districtCodeValidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with district code that needs to be trimmed,
            2) with district code in lowercase,
            3) with district code in mixed case.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientValidDistrictCodeTest(String districtCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                districtCode,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld + 1);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @MethodSource("clientTypeCodeValidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with client type code that needs to be trimmed,
            2) with client type code in lowercase,
            3) with client type code in mixed case.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientValidClientTypeCodeTest(String clientTypeCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                clientTypeCode,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld + 1);
    }

    @ParameterizedTest(name = "[{index}] subSectorCode = {0}")
    @MethodSource("subSectorCodeValidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with sub-sector code that needs to be trimmed,
            2) with sub-sector code in lowercase,
            3) with sub-sector code in mixed case.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientValidSubSectorCodeTest(String subSectorCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                BASE_SOCIAL_RANK_CODE,
                BASE_DISTRICT_CODE,
                subSectorCode
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld + 1);
    }

    @ParameterizedTest(name = "[{index}] socialRankCode = {0}")
    @MethodSource("socialRankCodeValidRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with social rank code that needs to be trimmed,
            2) with social rank code in lowercase,
            3) with social rank code in mixed case.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientValidSocialRankCodeTest(String socialRankCode) {
        int clientsCountOld = ClientDbHelper.getClientsCount();

        Client client = ClientApiFactory.defaultClientApiRequest(
                BASE_CLIENT_TYPE_CODE,
                socialRankCode,
                BASE_DISTRICT_CODE,
                BASE_SUB_SECTOR_CODE
        );
        PostApiReqHelper.saveClientAndValidate(client, SC_OK);

        ClientDb clientDb = ClientDbHelper.selectClientByCode(client.getClientCode());
        ClientDbAssertions.checkClientPresence(clientDb, true);

        int clientsCountNew = ClientDbHelper.getClientsCount();
        CommonDbAssertions.checkCounts(clientsCountNew, clientsCountOld + 1);
    }


    // generates invalid test codes (null, "", " ", absent in the database)
    private static Stream<Arguments> testCodeInvalidRequest() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }

    // generates valid test codes (untrimmed, lowercase, mixed case)
    private static Stream<Arguments> generateCodeVariations(String testCode) {
        return Stream.of(
                Arguments.of(" " + testCode + " "),
                Arguments.of(testCode.toLowerCase()),
                Arguments.of(testCode.charAt(0) + testCode.substring(1).toLowerCase())
        );
    }

    private static Stream<Arguments> districtCodeValidRequest() {
        return generateCodeVariations(BASE_DISTRICT_CODE);
    }

    private static Stream<Arguments> clientTypeCodeValidRequest() {
        return generateCodeVariations(BASE_CLIENT_TYPE_CODE);
    }

    private static Stream<Arguments> subSectorCodeValidRequest() {
        return generateCodeVariations(BASE_SUB_SECTOR_CODE);
    }

    private static Stream<Arguments> socialRankCodeValidRequest() {
        return generateCodeVariations(BASE_SOCIAL_RANK_CODE);
    }
}
