package client_type;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.ClientTypeApiAssertions;
import org.skopintsev.assertions.db.ClientTypeDbAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.client_types.ClientTypeDb;
import org.skopintsev.database.client_types.ClientTypeDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.ClientTypeName;
import org.skopintsev.model.ClientType;
import org.skopintsev.transport.api.ClientsApiClient;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicClientTypeTest extends BaseClientTypeTest {

    final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    final String CLIENT_TYPE_NAME = ClientTypeName.SS.getText();
    final String DESCRIPTION = GeneratorBuilder.generateString(1000);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new ClientType object into the Database and checks API for the new added client type.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientTypeDbTest() {
        ClientTypeDb newClientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .description(DESCRIPTION)
                .build();
        int rowsInserted = ClientTypeDbHelper.insertClientType(newClientTypeDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<ClientType> clientTypes = ClientsApiClient.getClientTypesAndValidate(SC_OK);
        ClientTypeApiAssertions.checkNotNullClientTypes(clientTypes);

        ClientType newAddedClientTypeApi = clientTypes
                .stream()
                .filter(c -> c.getClientTypeCode().equals(CLIENT_TYPE_CODE))
                .findFirst()
                .orElse(null);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeName",
                newAddedClientTypeApi.getClientTypeName(), CLIENT_TYPE_NAME);
        ClientTypeDbAssertions.checkClientTypeField("description",
                newAddedClientTypeApi.getDescription(), DESCRIPTION);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new ClientType object and checks Database for the new added client type.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientTypeApiTest() {
        ClientType newAddedClientTypeApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .description(DESCRIPTION)
                .build();
        ClientsApiClient.saveClientTypeAndValidate(newAddedClientTypeApi, SC_OK);

        List<ClientType> currencies = ClientsApiClient.getClientTypesAndValidate(SC_OK);
        ClientTypeApiAssertions.checkNotNullClientTypes(currencies);

        ClientTypeDb newAddedClientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(CLIENT_TYPE_CODE);
        ClientTypeDbAssertions.checkClientTypePresence(newAddedClientTypeDb, true);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeName",
                newAddedClientTypeApi.getClientTypeName(), CLIENT_TYPE_NAME);
        ClientTypeDbAssertions.checkClientTypeField("description",
                newAddedClientTypeApi.getDescription(), DESCRIPTION);
    }
}
