package io.github.mjcro.maskid;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LongMaskerDecoratorTest {
    @Test
    public void testStringMasking() {
        LongMaskerDecorator<String> m = new LongMaskerDecorator<>(
                new MultiplicationShiftMasker(13, 5),
                value -> Base64.getEncoder().encodeToString(value.toString().getBytes(StandardCharsets.UTF_8)),
                value -> Long.parseLong(new String(Base64.getDecoder().decode(value)))
        );

        Assert.assertEquals(m.mask(12345), "NjE3Mzg=");
        Assert.assertEquals(m.unmask("NjE3Mzg="), 12345);
    }
}