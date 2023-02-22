package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class IntDictionaryTest {
    @Test
    public void testMapping() {
        HashMap<Integer, Integer> m = new HashMap<>();
        m.put(1, 12345);
        m.put(2, 22);
        IntMasker masker = new IntDictionary(m);

        Assert.assertEquals(masker.maskInt(1), 12345);
        Assert.assertEquals(masker.unmaskInt(12345), 1);

        Assert.assertEquals(masker.maskInt(2), 22);
        Assert.assertEquals(masker.unmaskInt(22), 2);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testMappingUnknown() {
        HashMap<Integer, Integer> m = new HashMap<>();
        m.put(1, 12345);
        m.put(2, 22);
        IntMasker masker = new IntDictionary(m);

        masker.maskInt(3);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testUnmappingUnknown() {
        HashMap<Integer, Integer> m = new HashMap<>();
        m.put(1, 12345);
        m.put(2, 22);
        IntMasker masker = new IntDictionary(m);

        masker.unmaskInt(3);
    }
}