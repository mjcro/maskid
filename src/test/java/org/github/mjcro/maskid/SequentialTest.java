package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SequentialTest {
    @DataProvider
    public Object[][] maskingDataProvider() {
        return new Object[][]{
                {0, 52},
                {1, 73},
                {-1, 31},
                {12345, 259297},
        };
    }

    @Test(dataProvider = "maskingDataProvider")
    public void testMasking(final long given, final long expected) {
        Masker m = new Sequential(
                new MultiplicationShift(5,3),
                new MultiplicationShift(17,7)
        );
        Assert.assertEquals(m.mask(given), expected);
        Assert.assertEquals(m.unmask(expected), given);
    }
}