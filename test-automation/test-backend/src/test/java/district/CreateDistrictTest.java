package district;

import org.junit.jupiter.api.TestInstance;
import org.skopintsev.helper.GeneratorBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateDistrictTest {

    String districtCode = GeneratorBuilder.generateTestCode();
    String districtName = GeneratorBuilder.generateString(3);


}
