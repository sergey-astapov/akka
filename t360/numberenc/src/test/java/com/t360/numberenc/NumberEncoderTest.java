package com.t360.numberenc;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

/**
 * Validates {@link NumberEncoder} correctness.
 */
public class NumberEncoderTest {
    private static final String[] NUMBERS = new String[] {
            "112",
            "5624-82",
            "4824",
            "0721/608-4067",
            "10/783--5",
            "1078-913-5",
            "381482",
            "04824"
    };

    private static Dictionary DICT;

    @BeforeClass
    public static void beforeClass() throws Exception {
        URL resource = DictionaryTest.class.getClassLoader().getResource("dictionary-sample.txt");
        assertNotNull(resource);

        try (Stream<String> words = Files.lines(Paths.get(resource.toURI()))) {
            DICT = new Dictionary(words);
        }
    }

    @Test
    public void testEncoder() {
        NumberEncoder ne = new NumberEncoder(DICT);
        ne.encode(Arrays.stream(NUMBERS))
                .forEach(System.out::println);
    }
}
