package io.github.mjcro.maskid;

/**
 * Performs value masking using XOR operation.
 */
public class LongXorMasker implements LongMasker {
    private final long xorSecret;

    public LongXorMasker(final long xorSecret) {
        this.xorSecret = xorSecret;
    }

    @Override
    public long maskLong(final long value) {
        return value ^ xorSecret;
    }

    @Override
    public long unmaskLong(final long value) {
        return value ^ xorSecret;
    }

    @Override
    public String toString() {
        return "{XOR " + xorSecret + "}";
    }
}
