package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultiplicationShiftTest {
    @DataProvider
    public Object[][] maskingDataProvider() {
        return new Object[][]{
                {0, 13},
                {1, 18},
                {-1, 8},
                {12345, 61738},
        };
    }

    @Test(dataProvider = "maskingDataProvider")
    public void testMasking(final long given, final long expected) {
        Masker m = new MultiplicationShift(13, 5);
        Assert.assertEquals(m.mask(given), expected);
        Assert.assertEquals(m.unmask(expected), given);
    }
}