package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultiplicationShiftMaskerTest {
    @DataProvider
    public Object[][] maskingLongDataProvider() {
        return new Object[][]{
                {0, 13},
                {1, 18},
                {-1, -18},
                {12345, 61738},
                {-12345, -61738},
                {Long.MAX_VALUE - 1, 3},
                {Long.MAX_VALUE, 8},
                {Long.MIN_VALUE, -13},
                {Long.MIN_VALUE + 1, -8},
        };
    }

    @Test(dataProvider = "maskingLongDataProvider")
    public void testMaskingLong(final long given, final long expected) {
        LongMasker m = new MultiplicationShiftMasker(13, 5);
        Assert.assertEquals(m.maskLong(given), expected);
        Assert.assertEquals(m.unmaskLong(expected), given);
    }

    @DataProvider
    public Object[][] maskingIntDataProvider() {
        return new Object[][]{
                {0, 13},
                {1, 18},
                {-1, -18},
                {12345, 61738},
                {-12345, -61738},
                {Integer.MAX_VALUE - 1, 3},
                {Integer.MAX_VALUE, 8},
                {Integer.MIN_VALUE, -13},
                {Integer.MIN_VALUE + 1, -8},
        };
    }

    @Test(dataProvider = "maskingIntDataProvider")
    public void testMaskingInt(final int given, final int expected) {
        IntMasker m = new MultiplicationShiftMasker(13, 5);
        Assert.assertEquals(m.maskInt(given), expected);
        Assert.assertEquals(m.unmaskInt(expected), given);
    }
}