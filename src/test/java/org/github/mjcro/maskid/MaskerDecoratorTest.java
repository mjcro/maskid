package org.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MaskerDecoratorTest {
    @Test
    public void testStringMasking() {
        MaskerDecorator<String> m = new MaskerDecorator<>(
                new MultiplicationShift(13, 5),
                value -> Base64.getEncoder().encodeToString(value.toString().getBytes(StandardCharsets.UTF_8)),
                value -> Long.parseLong(new String(Base64.getDecoder().decode(value)))
        );

        Assert.assertEquals(m.mask(12345), "NjE3Mzg=");
        Assert.assertEquals(m.unmask("NjE3Mzg="), 12345);
    }
}