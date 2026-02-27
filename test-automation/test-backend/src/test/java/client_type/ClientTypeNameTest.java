package client_type;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.ClientTypeDbAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.ClientTypeName;
import org.skopintsev.model.ClientType;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientTypeNameTest extends BaseClientTypeTest {

    final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    private static final String CLIENT_TYPE_NAME = ClientTypeName.SS.getText();

    @ParameterizedTest(name = "[{index}] clientTypeName = {0}")
    @MethodSource("invalidClientTypeNameRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client type:
            1) with invalid name,
            2) with name = null,
            3) with name = empty string.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createSocialRankWithInvalidPrivilegeLevelTest(String clientTypeName) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(clientTypeName)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_SERVER_ERROR);

        ClientTypeDb socialRankDb = ClientTypeDbHelper.selectClientTypeByCode(CLIENT_TYPE_CODE);
        ClientTypeDbAssertions.checkClientTypePresence(socialRankDb, false);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeName = {0}")
    @MethodSource("validClientTypeNameProvider")
    @Tag("regression")
    @Description("""
            Test uses API to post a client type:
            1) with abbreviated name,
            2) with name in lowercase,
            3) with name in uppercase,
            4) with name in mixed case,
            5) with name to be trimmed.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createClientTypeWithValidPrivilegeLevelTest(String clientTypeName) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();
        String expectedClientTypeName = validateAndGetClientTypeName(clientTypeName.trim());

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(clientTypeName)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_OK);

        ClientTypeDb clientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(CLIENT_TYPE_CODE);
        ClientTypeDbAssertions.checkClientTypePresence(clientTypeDb, true);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeName", clientTypeDb.getClientTypeName(),
                expectedClientTypeName);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld + 1);
    }


    private static Stream<Arguments> invalidClientTypeNameRequest() {
        return Stream.of(
                Arguments.of(GeneratorBuilder.generateString(10)),
                Arguments.of(""),
                null
        );
    }

    private static Stream<Arguments> validClientTypeNameProvider() {
        return Stream.of(
                Arguments.of(ClientTypeName.SS.name()),
                Arguments.of(CLIENT_TYPE_NAME.toLowerCase()),
                Arguments.of(CLIENT_TYPE_NAME.toUpperCase()),
                Arguments.of(CLIENT_TYPE_NAME.charAt(0) + CLIENT_TYPE_NAME.substring(1).toLowerCase()),
                Arguments.of(" " + CLIENT_TYPE_NAME + " ")
        );
    }

    private static String validateAndGetClientTypeName(String clientTypeName) {
        try {
            // Convert string to enum to validate it exists
            ClientTypeName clientType = ClientTypeName.valueOf(clientTypeName.toUpperCase());
            return clientType.getText();
        } catch (IllegalArgumentException e) {
            // Try matching by text
            for (ClientTypeName ctn : ClientTypeName.values()) {
                if (ctn.getText().equalsIgnoreCase(clientTypeName)) {
                    return ctn.getText();
                }
            }
            throw new IllegalArgumentException("Invalid client type name: " + clientTypeName);
        }
    }
}
