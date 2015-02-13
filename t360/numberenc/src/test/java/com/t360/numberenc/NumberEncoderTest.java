package com.t360.numberenc;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.t360.numberenc.Dictionary.isPreviousDigit;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Validates {@link NumberEncoder} correctness.
 */
public class NumberEncoderTest {
    private static final String[] NUMBERS = new String[] {
            "902462013",
            "902462013L",
            "902462013-98625",
            "220323430201-5256-93/3-179964",
            "/6/-7-7"
    };

    private static Dictionary dictionary;

    @BeforeClass
    public static void beforeClass() throws Exception {
        URL resource = NumberEncoderTest.class.getClassLoader().getResource("dictionary.txt");
        if (resource == null) {
            throw new IllegalStateException("No dictionary.txt file");
        }
        try (Stream<String> words = Files.lines(Paths.get(resource.toURI()))) {
            dictionary = new Dictionary(words);
        }
    }

    @Test
    public void testEncoder() {
        NumberEncoder ne = new NumberEncoder(dictionary);
        ne.encode(Arrays.stream(NUMBERS))
                .forEach(System.out::println);
    }

    @Test
    public void testEncoder2() throws Exception {
        String[] nums = new String[] {
                "112", "5624-82", "4824",
                "0721/608-4067", "10/783--5",
                "1078-913-5", "381482", "04824"
        };
        URL resource = NumberEncoderTest.class.getClassLoader().getResource("dictionary-sample.txt");
        assertNotNull(resource);

        Dictionary dict;
        try (Stream<String> words = Files.lines(Paths.get(resource.toURI()))) {
            dict = new Dictionary(words);
        }
        StringBuilder sb = new StringBuilder();
        NumberEncoder ne = new NumberEncoder(dict);
        ne.encode(Arrays.stream(nums))
                .forEach((s) -> sb.append(s).append("\n"));
        System.out.println(sb.toString());
    }

    @Test
    public void testEncoder3() throws Exception {
        String[] nums = new String[] {
                "5624-82"
        };
        URL resource = NumberEncoderTest.class.getClassLoader().getResource("dictionary-sample.txt");
        assertNotNull(resource);

        Dictionary dict;
        try (Stream<String> words = Files.lines(Paths.get(resource.toURI()))) {
            dict = new Dictionary(words);
        }
        StringBuilder sb = new StringBuilder();
        NumberEncoder ne = new NumberEncoder(dict);
        ne.encode(Arrays.stream(nums))
                .forEach((s) -> sb.append(s).append("\n"));
        System.out.println(sb.toString());
    }

    @Test
    public void testPreviousDigit() {
        StringBuilder sb = new StringBuilder();
        assertThat(isPreviousDigit(sb), is(false));
        sb.append(" ");
        assertThat(isPreviousDigit(sb), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreviousDigitLengthError() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        isPreviousDigit(sb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreviousDigitError() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        isPreviousDigit(sb);
    }
}
