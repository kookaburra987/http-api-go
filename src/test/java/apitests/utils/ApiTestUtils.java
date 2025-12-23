package apitests.utils;

import org.apache.commons.lang3.RandomUtils;

public class ApiTestUtils {
    private ApiTestUtils() {
    }

    public static String createRandomName() {
        //random name is created so that it does not conflict with data that already exists in database
        int randomInt = RandomUtils.insecure().randomInt();
        return "theName-" + randomInt;
    }
}
