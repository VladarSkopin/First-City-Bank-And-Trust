package district;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.districts.DistrictDbHelper;

public class BaseDistrictTest {
    @BeforeEach
    public void setUp() {
        DistrictDbHelper.deleteAllTestDistricts();
    }

    @AfterEach
    public void tearDown() {
        DistrictDbHelper.deleteAllTestDistricts();
    }
}
