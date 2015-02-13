package com.t360.numberenc;

import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static com.t360.numberenc.Dictionary.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Validates {@link com.t360.numberenc.NumberEncoder} correctness.
 */
public class DictionaryTest {
    private static final String[] NUMBERS = new String[] {
            "902462013",
            "902462013L",
            "902462013-98625",
            "220323430201-5256-93/3-179964",
            "/6/-7-7"
    };

    @Test
    public void testMatch() {
        Optional<Entry> e = match("mir", "5624-82".toCharArray(), 0);
        assertTrue(e.isPresent());
        assertTrue(e.get().value.word.isPresent());
        assertThat(e.get().value.start, is(3));
    }

    @Test
    public void testNotMatch() {
        Optional<Entry> e = match("neu", "5624-82".toCharArray(), 0);
        assertFalse(e.isPresent());
    }

    @Test
    public void testSearch() {
        List<Integer> indexes = search(new String[]{"mir", "rim"}, '5');
        assertFalse(indexes.isEmpty());
        assertThat(indexes.size(), is(1));
        assertThat(indexes.get(0), is(0));
    }

    @Test
    public void testMultipleSearch() {
        List<Integer> indexes = search(new String[] {"mir", "Mir", "rim"}, '5');
        assertFalse(indexes.isEmpty());
        assertThat(indexes.size(), is(2));
        assertThat(indexes.get(0), is(0));
        assertThat(indexes.get(1), is(2));
    }

    @Test
    public void testSearchIndex() {
        String[] dictionary = {"mir", "Mir", "rim"};
        Integer index = searchIndex(dictionary, "m");
        assertTrue(index >= 0);

        index = searchIndex(dictionary, "M");
        assertTrue(index >= 0);

        index = searchIndex(dictionary, "r");
        assertThat(index, is(2));
    }

    @Test
    public void testNoSearch() {
        List<Integer> indexes = search(new String[] {"rim"}, '5');
        assertTrue(indexes.isEmpty());
    }

    @Test
    public void testEncoder3() throws Exception {
        String[] nums = new String[] {
                "5624-82"
        };
        URL resource = DictionaryTest.class.getClassLoader().getResource("dictionary-sample.txt");
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
}
