package client_type;

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
import org.skopintsev.assertions.db.ClientTypeDbAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.ClientTypeNameEnum;
import org.skopintsev.model.client.ClientType;
import org.skopintsev.transport.api.ClientsApiClient;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientTypeTest extends BaseClientTypeTest {

    final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    final String CLIENT_TYPE_NAME = ClientTypeNameEnum.SS.getText();

    @Test
    @Tag("regression")
    @Description("Test uses API to post a ClientType that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeAlreadyExistsTest() {
        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        ClientsApiClient.saveClientTypeAndValidate(clientTypeApi, SC_OK);
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientsApiClient.saveClientTypeAndValidate(clientTypeApi, SC_SERVER_ERROR);
        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a ClientType:
            1) with code = null,
            2) with code = empty string,
            3) with code = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeWithInvalidCodeTest(String clientTypeCode) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(clientTypeCode)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        ClientsApiClient.saveClientTypeAndValidate(clientTypeApi, SC_SERVER_ERROR);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @MethodSource("clientTypeCodeProvider")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a ClientType:
            1) with code that needs to be trimmed,
            2) with code that should be modified to upper case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeCodeTrimUppercaseTest(String clientTypeCode) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();
        String clientTypeCodeTrimmedUppercase = clientTypeCode.trim().toUpperCase();

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(clientTypeCode)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        ClientsApiClient.saveClientTypeAndValidate(clientTypeApi, SC_OK);

        ClientTypeDb clientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(clientTypeCodeTrimmedUppercase);
        ClientTypeDbAssertions.checkClientTypePresence(clientTypeDb, true);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeCode", clientTypeDb.getClientTypeCode(),
                clientTypeCodeTrimmedUppercase);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld + 1);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a ClientType with client type name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeNameAlreadyExistsTest() {
        ClientTypeDb clientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        int rowsCount = ClientTypeDbHelper.insertClientType(clientTypeDb);
        CommonDbAssertions.checkRowsInserted(rowsCount);

        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientType clientTypeApiSameName = ClientType.builder()
                .clientTypeCode(GeneratorBuilder.generateTestCode())
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        ClientsApiClient.saveClientTypeAndValidate(clientTypeApiSameName, SC_SERVER_ERROR);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    private static Stream<Arguments> clientTypeCodeProvider() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
