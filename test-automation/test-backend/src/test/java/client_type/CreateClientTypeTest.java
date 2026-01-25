package client_type;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import org.skopintsev.helper.enums.ClientTypeName;
import org.skopintsev.model.ClientType;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateClientTypeTest extends BaseClientTypeTest {

    private final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    private final String CLIENT_TYPE_NAME = ClientTypeName.SS.getText();

    @Test
    @Tag("regression")
    @Description("Test uses API to post a ClientType that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeAlreadyExists() {
        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_OK);
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_SERVER_ERROR);
        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a ClientType:
            1) with code = null,
            2) with code = empty string.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeWithInvalidCode(String clientTypeCode) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(clientTypeCode)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_SERVER_ERROR);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @MethodSource("clientTypeCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a ClientType:
            1) with code that needs to be trimmed,
            2) with code that should be modified to upper case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeCodeTrimUppercase(String clientTypeCode) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();
        String clientTypeCodeTrimmedUppercase = clientTypeCode.trim().toUpperCase();

        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(clientTypeCode)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_OK);

        ClientTypeDb clientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(clientTypeCodeTrimmedUppercase);
        ClientTypeDbAssertions.checkClientTypePresence(clientTypeDb, true);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeCode", clientTypeDb.getClientTypeCode(),
                clientTypeCodeTrimmedUppercase);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew - 1, clientTypesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a ClientType with client type name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createClientTypeNameAlreadyExists() {
        ClientType clientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApi, SC_OK);

        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientType clientTypeApiSameName = ClientType.builder()
                .clientTypeCode(GeneratorBuilder.generateTestCode())
                .clientTypeName(CLIENT_TYPE_NAME)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(clientTypeApiSameName, SC_SERVER_ERROR);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    private static Stream<Arguments> clientTypeCodeRequest() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
