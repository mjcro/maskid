package org.github.mjcro.maskid;

public class MultiplicationShift implements Masker {
    private final long shift;
    private final int multiplier;

    public MultiplicationShift(final long shift, final int multiplier) {
        this.shift = shift;
        this.multiplier = multiplier;
    }

    @Override
    public long mask(final long value) {
        return value * multiplier + shift;
    }

    @Override
    public long unmask(final long value) {
        return (value - shift) / multiplier;
    }
}
