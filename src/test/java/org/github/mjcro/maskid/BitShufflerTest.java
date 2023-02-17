package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BitShufflerTest {
    @DataProvider
    public Object[][] maskingDataProvider() {
        return new Object[][]{
                {0, 0},
                {1, 0},
                {2, 0},
                {3, 0},
                {16, 0},
                {17, 0},
                {18, 0},
                {321, 0},
                {1234, 0},
                {37142, 0},
                {123345, 0},
                {123346, 0},
        };
    }

    @Test(dataProvider = "maskingDataProvider")
    public void testMasking(final long given, final long expected) {
        Masker m = new BitShuffler();
        Assert.assertEquals(m.mask(given), expected);
        Assert.assertEquals(m.unmask(expected), given);
    }
}