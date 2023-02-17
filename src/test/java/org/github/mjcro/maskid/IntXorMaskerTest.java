package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IntXorMaskerTest {
    @Test
    public void testMasking() {
        IntMasker masker = new IntXorMasker(123454321);

        Assert.assertEquals(masker.maskInt(19), 123454306);
        Assert.assertEquals(masker.maskInt(8173642), 120027963);
        Assert.assertEquals(masker.maskInt(-11), -123454332);

        Assert.assertEquals(masker.unmaskInt(123454306), 19);
        Assert.assertEquals(masker.unmaskInt(120027963), 8173642);
        Assert.assertEquals(masker.unmaskInt(-123454332), -11);
    }
}