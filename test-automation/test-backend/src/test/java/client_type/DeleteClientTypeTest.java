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
import org.skopintsev.assertions.db.ClientTypeDbAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_NOT_FOUND;
import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteClientTypeTest extends BaseClientTypeTest {

    private final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();

    @Test
    @Tag("regression")
    @Description("Test creates a new client type in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteClientType() {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        ClientTypeDb clientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(GeneratorBuilder.generateString(10))
                .build();
        int rowsInserted = ClientTypeDbHelper.insertClientType(clientTypeDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteClientTypeAndValidate(CLIENT_TYPE_CODE, SC_OK);

        clientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(CLIENT_TYPE_CODE);
        ClientTypeDbAssertions.checkClientTypePresence(clientTypeDb, false);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientTypeCode = {0}")
    @MethodSource("clientTypeCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to delete a client type:
            1) with code = null,
            2) with code = empty string,
            3) a client type that is absent in the Database.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteClientTypeNegativeTest(String clientTypeCode) {
        int clientTypesCountOld = ClientTypeDbHelper.getClientTypesCount();

        DeleteApiReqHelper.deleteClientTypeAndValidate(clientTypeCode, SC_NOT_FOUND);

        int clientTypesCountNew = ClientTypeDbHelper.getClientTypesCount();
        CommonDbAssertions.checkCounts(clientTypesCountNew, clientTypesCountOld);
    }

    private static Stream<Arguments> clientTypeCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
