package com.t360.numberenc;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SearchUtilsTest {
    public static final String[] DICTIONARY = new String[] {"mir", "Mir", "rim"};

    @Test
    public void testMatch() {
        Optional<Entry> e = SearchUtils.match("mir", "5624-82".toCharArray(), 0);
        assertTrue(e.isPresent());
        assertTrue(e.get().word.isPresent());
        assertThat(e.get().next, is(3));
    }

    @Test
    public void testNotMatch() {
        Optional<Entry> e = SearchUtils.match("neu", "5624-82".toCharArray(), 0);
        assertFalse(e.isPresent());
    }

    @Test
    public void testAny() {
        Integer index = SearchUtils.letterAny(DICTIONARY, "m");
        assertThat(index, is(1));

        index = SearchUtils.letterAny(DICTIONARY, "M");
        assertThat(index, is(1));

        index = SearchUtils.letterAny(DICTIONARY, "r");
        assertThat(index, is(2));
    }

    @Test
    public void testAll() {
        List<Integer> indexes = SearchUtils.letterAll(DICTIONARY, "m");
        assertThat(indexes.size(), is(2));
        assertThat(indexes.get(0), is(0));
        assertThat(indexes.get(1), is(1));

        indexes = SearchUtils.letterAll(DICTIONARY, "M");
        assertThat(indexes.size(), is(2));
        assertThat(indexes.get(0), is(0));
        assertThat(indexes.get(1), is(1));

        indexes = SearchUtils.letterAll(DICTIONARY, "r");
        assertThat(indexes.size(), is(1));
        assertThat(indexes.get(0), is(2));
    }

    @Test
    public void testDigit() {
        List<Integer> indexes = SearchUtils.digitAll(DICTIONARY, '2');
        assertFalse(indexes.isEmpty());
        assertThat(indexes.size(), is(1));
        assertThat(indexes.get(0), is(2));
    }

    @Test
    public void testDigitAll() {
        List<Integer> indexes = SearchUtils.digitAll(DICTIONARY, '5');
        assertThat(indexes.size(), is(2));
        assertThat(indexes.get(0), is(0));
        assertThat(indexes.get(1), is(1));
    }

    @Test
    public void testDigitNo() {
        List<Integer> indexes = SearchUtils.digitAll(new String[]{"rim"}, '5');
        assertTrue(indexes.isEmpty());
    }
}
