package com.t360.numberenc;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.t360.numberenc.Entry.isDigit;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Validates {@link com.t360.numberenc.NumberEncoder} correctness.
 */
public class DictionaryTest {
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
    public void test562482() throws Exception {
        DICT.collect("5624-82").forEach(System.out::println);
    }

    @Test
    public void test4824() throws Exception {
        DICT.collect("4824").forEach(System.out::println);
    }

    @Test
    public void test04824() throws Exception {
        DICT.collect("04824").forEach(System.out::println);
    }

    @Test
    public void test107835() throws Exception {
        DICT.collect("10/783--5").forEach(System.out::println);
    }

    @Test
    public void test107835_() throws Exception {
        DICT.collect("1078-913-5").forEach(System.out::println);
    }

    @Test
    public void test381482() throws Exception {
        DICT.collect("381482").forEach(System.out::println);
    }

    @Test
    public void testCollect() throws Exception {
        Dictionary dict = new Dictionary(Arrays.asList(SearchUtilsTest.DICTIONARY));
        Entry e = dict.collectEncodedEntry(new Entry(0), "5624-82".toCharArray());
        assertTrue(e.children.isPresent());
        List<String> strings = e.traverse().collect(Collectors.toList());
        assertFalse(strings.isEmpty());
    }

    @Test
    public void testIsDigit() {
        assertThat(isDigit(""), is(false));
        assertThat(isDigit("a"), is(false));
        assertThat(isDigit("1"), is(true));
    }
}
