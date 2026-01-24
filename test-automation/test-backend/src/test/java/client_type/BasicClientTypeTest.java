package client_type;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicClientTypeTest extends BaseClientTypeTest {

    private final String CLIENT_TYPE_CODE = GeneratorBuilder.generateTestCode();
    private final String CLIENT_TYPE_NAME = ClientTypeName.SS.getText();
    private final String DESCRIPTION = GeneratorBuilder.generateString(1000);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new ClientTypeName object into the Database and checks API for the new added client type.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientTypeDbTest() {
        ClientTypeDb newClientTypeDb = ClientTypeDb.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .description(DESCRIPTION)
                .build();
        int rowsInserted = ClientTypeDbHelper.insertClientType(newClientTypeDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<ClientType> clientTypes = GetApiReqHelper.getClientTypesAndValidate(SC_OK);
        ClientTypeApiAssertions.checkNotNullClientTypes(clientTypes);

        ClientType newAddedClientTypeNameApi = clientTypes
                .stream()
                .filter(c -> c.getClientTypeCode().equals(CLIENT_TYPE_CODE))
                .findFirst()
                .orElse(null);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeName",
                newAddedClientTypeNameApi.getClientTypeName(), CLIENT_TYPE_NAME);
        ClientTypeDbAssertions.checkClientTypeField("description",
                newAddedClientTypeNameApi.getDescription(), DESCRIPTION);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new ClientTypeName object and checks Database for the new added client type.")
    @Severity(SeverityLevel.BLOCKER)
    public void createClientTypeApiTest() {
        ClientType newAddedClientTypeNameApi = ClientType.builder()
                .clientTypeCode(CLIENT_TYPE_CODE)
                .clientTypeName(CLIENT_TYPE_NAME)
                .description(DESCRIPTION)
                .build();
        PostApiReqHelper.saveClientTypeAndValidate(newAddedClientTypeNameApi, SC_OK);

        List<ClientType> currencies = GetApiReqHelper.getClientTypesAndValidate(SC_OK);
        ClientTypeApiAssertions.checkNotNullClientTypes(currencies);

        ClientTypeDb newAddedClientTypeDb = ClientTypeDbHelper.selectClientTypeByCode(CLIENT_TYPE_CODE);
        ClientTypeDbAssertions.checkClientTypePresence(newAddedClientTypeDb, true);
        ClientTypeDbAssertions.checkClientTypeField("clientTypeName",
                newAddedClientTypeNameApi.getClientTypeName(), CLIENT_TYPE_NAME);
        ClientTypeDbAssertions.checkClientTypeField("description",
                newAddedClientTypeNameApi.getDescription(), DESCRIPTION);
    }
}
