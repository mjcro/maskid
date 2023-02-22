package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LongSequentialMaskerTest {
    @DataProvider
    public Object[][] maskingDataProvider() {
        return new Object[][]{
                {0, 52},
                {1, 73},
                {-1, -73},
                {12345, 259297},
        };
    }

    @Test(dataProvider = "maskingDataProvider")
    public void testMasking(final long given, final long expected) {
        LongMasker m = new LongSequentialMasker(
                new MultiplicationShiftMasker(5,3),
                new MultiplicationShiftMasker(17,7)
        );
        Assert.assertEquals(m.maskLong(given), expected);
        Assert.assertEquals(m.unmaskLong(expected), given);
    }
}