package district;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.district.DistrictDbHelper;

public class BaseDistrictTest {
    @BeforeEach
    public void setUp() {
        DistrictDbHelper.deleteAllDistricts();
    }

    @AfterEach
    public void tearDown() {
        DistrictDbHelper.deleteAllDistricts();
    }
}
