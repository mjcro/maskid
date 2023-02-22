package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LongBitShuffleMaskerTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void testVerifyNull() {
        new LongBitShuffleMasker(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifySizeLow() {
        new LongBitShuffleMasker(new byte[63]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifySizeHigh() {
        new LongBitShuffleMasker(new byte[65]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifyInvalidOffset() {
        byte[] matrix = new byte[64];
        matrix[0] = 64;
        new LongBitShuffleMasker(matrix);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVerifyUnique() {
        new LongBitShuffleMasker(new byte[64]);
    }

    @DataProvider
    public Object[][] dataProviderRandomSetOne() {
        return new Object[][]{
                {0, 0},
                {1, 65536},
                {2, 1125899906842624L},
                {3, 1125899906908160L},
                {16, 2251799813685248L},
                {17, 2251799813750784L},
                {18, 3377699720527872L},
                {321, 4503599629533184L},
                {1234, 3377699722625344L},
                {37142, 25897896880635920L},
                {123345, 6896136931737680L},
                {123346, 8022036838514768L},
        };
    }

    @Test(dataProvider = "dataProviderRandomSetOne")
    public void testRandomSetOneMasking(final long given, final long expected) {
        LongMasker m = LongBitShuffleMasker.randomSetOne();
        Assert.assertEquals(m.maskLong(given), expected);
        Assert.assertEquals(m.unmaskLong(expected), given);
    }

    @DataProvider
    public Object[][] dataProviderReverse() {
        return new Object[][]{
                {0, 0},
                {1, 72057594037927936L},
                {2, 144115188075855872L},
                {3, 216172782113783808L},
                {4, 288230376151711744L},
                {5, 360287970189639680L},
                {6, 432345564227567616L},
                {1234567890, 5909450901340291200L},
                {-1, -1L},
                {-2, -72057594037927937L},
                {-3, -144115188075855873L},
                {-4, -216172782113783809L},
                {Long.MAX_VALUE, 9223372036854775807L},
                {Long.MIN_VALUE, -9223372036854775808L},
        };
    }

    @Test(dataProvider = "dataProviderReverse")
    public void testReverseMasking(final long given, final long expected) {
        LongMasker m = LongBitShuffleMasker.reverse();

        Assert.assertEquals(m.maskLong(given), expected);
        Assert.assertEquals(m.unmaskLong(expected), given);
    }
}