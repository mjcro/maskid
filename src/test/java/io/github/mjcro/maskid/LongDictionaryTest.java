package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class LongDictionaryTest {
    @Test
    public void testMapping() {
        HashMap<Long, Long> m = new HashMap<>();
        m.put(1L, 12345L);
        m.put(2L, 22L);
        LongMasker masker = new LongDictionary(m);

        Assert.assertEquals(masker.maskLong(1), 12345);
        Assert.assertEquals(masker.unmaskLong(12345), 1);

        Assert.assertEquals(masker.maskLong(2), 22);
        Assert.assertEquals(masker.unmaskLong(22), 2);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testMappingUnknown() {
        HashMap<Long, Long> m = new HashMap<>();
        m.put(1L, 12345L);
        m.put(2L, 22L);
        LongMasker masker = new LongDictionary(m);

        masker.maskLong(3);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testUnmappingUnknown() {
        HashMap<Long, Long> m = new HashMap<>();
        m.put(1L, 12345L);
        m.put(2L, 22L);
        LongMasker masker = new LongDictionary(m);

        masker.unmaskLong(3);
    }
}