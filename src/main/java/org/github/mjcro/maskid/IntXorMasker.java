package org.github.mjcro.maskid;

/**
 * Performs value masking using XOR operation.
 */
public class IntXorMasker implements IntMasker {
    private final int xorSecret;

    public IntXorMasker(final int xorSecret) {
        this.xorSecret = xorSecret;
    }

    @Override
    public int maskInt(final int value) {
        return value ^ xorSecret;
    }

    @Override
    public int unmaskInt(final int value) {
        return value ^ xorSecret;
    }

    @Override
    public String toString() {
        return "{XOR " + xorSecret + "}";
    }
}
