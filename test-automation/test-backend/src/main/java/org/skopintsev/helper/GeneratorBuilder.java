package org.skopintsev.helper;

import java.util.Random;
import java.util.stream.Collectors;

public class GeneratorBuilder {

    private static final Random random = new Random();

    // both lower and upper limits are inclusive
    public static int generateRandomNumberInclusive(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    public static String generateTestCode() {
        return "TEST-" + generateRandomNumberInclusive(100, 999);
    }

    public static String generateString(int size) {
        String characters = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        return new Random()
                .ints(size, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public static int generateAmount() {
        return generateRandomNumberInclusive(1, 999999999);
    }
}
