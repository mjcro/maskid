package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LongXorMaskerTest {
    @Test
    public void testMasking() {
        LongMasker masker = new LongXorMasker(123454321);

        Assert.assertEquals(masker.maskLong(19), 123454306);
        Assert.assertEquals(masker.maskLong(832665199827L), 832584902562L);
        Assert.assertEquals(masker.maskLong(-11), -123454332);

        Assert.assertEquals(masker.unmaskLong(123454306), 19);
        Assert.assertEquals(masker.unmaskLong(832584902562L), 832665199827L);
        Assert.assertEquals(masker.unmaskLong(-123454332), -11);
    }
}