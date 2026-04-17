package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.api.vaults.VaultStatsApiAssertions;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.SearchByEnum;
import org.skopintsev.model.vaults.vaultstats.SearchParams;
import org.skopintsev.model.vaults.vaultstats.VaultStatsRequest;
import org.skopintsev.model.vaults.vaultstats.VaultStatsResponse;
import org.skopintsev.transport.api.VaultsApiClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VaultStatsNegativeTest extends BaseVaultTest {

    final int LIMIT_SINGLE = 1;

    @ParameterizedTest(name = "[{index}] systemName = {0}")
    @MethodSource("invalidParameterProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to get vault statistics:
        1) with systemName = null,
        2) with systemName = empty string,
        3) with systemName = whitespace,
        4) with systemName = random string.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void vaultStatsInvalidRequestSystemNameTest(String systemName) {
        SearchParams searchParams = SearchParams.builder()
                .searchBy(SearchByEnum.SUBSECTOR.getText())
                .searchString(BASE_SUB_SECTOR_CODE)
                .build();
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder()
                .limit(LIMIT_SINGLE)
                .searchParams(searchParams)
                .systemName(systemName)
                .build();
        VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_SERVER_ERROR);
    }

    @ParameterizedTest(name = "[{index}] searchBy = {0}")
    @MethodSource("invalidParameterProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to get vault statistics:
        1) with searchBy = null,
        2) with searchBy = empty string,
        3) with searchBy = whitespace,
        4) with searchBy = random string.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void vaultStatsInvalidRequestSearchByTest(String searchBy) {
        SearchParams searchParams = SearchParams.builder()
                .searchBy(searchBy)
                .searchString(BASE_SUB_SECTOR_CODE)
                .build();
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder()
                .limit(LIMIT_SINGLE)
                .searchParams(searchParams)
                .systemName(SYSTEM_NAME_CORE)
                .build();
        VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_SERVER_ERROR);
    }

    @ParameterizedTest(name = "[{index}] searchString = {0}")
    @MethodSource("invalidParameterProvider")
    @Tag("regression")
    @Description(
        """
        Test uses API to get vault statistics:
        1) with searchString = null,
        2) with searchString = empty string,
        3) with searchString = whitespace,
        4) with searchString = random string.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void vaultStatsInvalidRequestSearchStringTest(String searchString) {
        SearchParams searchParams = SearchParams.builder()
                .searchBy(SearchByEnum.SUBSECTOR.getText())
                .searchString(searchString)
                .build();
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder()
                .limit(LIMIT_SINGLE)
                .searchParams(searchParams)
                .systemName(SYSTEM_NAME_CORE)
                .build();

        if (searchString == null) {
            VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_SERVER_ERROR);
        } else {
            VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_OK);

            VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), 0);
            VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
            VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                    vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
            VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                    vaultStatsResponse.getVaults(), Collections.emptyList());
        }

    }


    private static Stream<Arguments> invalidParameterProvider() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateString(10))
        );
    }
}
