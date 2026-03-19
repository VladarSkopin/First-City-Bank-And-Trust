package org.skopintsev.districts_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.District;
import org.skopintsev.models.api.factory.DistrictFactory;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class BaseDistrictTest extends BaseTest {

    List<District> BASE_DISTRICTS_LIST = DistrictFactory.generateDistrictsList();

    @BeforeEach
    public void openDistrictsPage() {
        PostApiResponseHelper.stubGetDistricts(BASE_DISTRICTS_LIST);
        OpenUrl.openDistrictsPage();
    }

}
