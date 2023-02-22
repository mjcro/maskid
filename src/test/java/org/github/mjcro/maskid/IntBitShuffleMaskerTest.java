package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IntBitShuffleMaskerTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void testVerifyNull() {
        new IntBitShuffleMasker(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifySizeLow() {
        new IntBitShuffleMasker(new byte[31]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifySizeHigh() {
        new IntBitShuffleMasker(new byte[33]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifyInvalidOffset() {
        byte[] matrix = new byte[32];
        matrix[0] = 33;
        new IntBitShuffleMasker(matrix);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifyUnique() {
        new IntBitShuffleMasker(new byte[32]);
    }

    @DataProvider
    public Object[][] dataProviderRandomSetOne() {
        return new Object[][]{
                {0, 0},
                {1, 8},
                {2, 4096},
                {3, 4104},
                {16, 134217728},
                {17, 134217736},
                {18, 134221824},
                {321, 16778248},
                {1234, 687902720},
                {37142, 136584192},
                {123345, 151815692},
                {123346, 151819780},
        };
    }

    @Test(dataProvider = "dataProviderRandomSetOne")
    public void testRandomSetOneMasking(final int given, final int expected) {
        IntMasker m = IntBitShuffleMasker.randomSetOne();
        Assert.assertEquals(m.maskInt(given), expected);
        Assert.assertEquals(m.unmaskInt(expected), given);
    }

    @DataProvider
    public Object[][] dataProviderReverse() {
        return new Object[][]{
                {0, 0},
                {1, 16777216},
                {2, 33554432},
                {3, 50331648},
                {4, 67108864},
                {5, 83886080},
                {6, 100663296},
                {1234567890, 1375901385},
                {-1, -1},
                {-2, -16777217},
                {-3, -33554433},
                {-4, -50331649},
                {Integer.MAX_VALUE, 2147483647},
                {Integer.MIN_VALUE, -2147483648},
        };
    }

    @Test(dataProvider = "dataProviderReverse")
    public void testReverseMasking(final int given, final int expected) {
        IntMasker m = IntBitShuffleMasker.reverse();

        Assert.assertEquals(m.maskInt(given), expected);
        Assert.assertEquals(m.unmaskInt(expected), given);
    }
}