package org.github.mjcro.maskid;

/**
 * Perform multiplication shift masking by formula:
 * masked = original * multiplier + shift
 * <p>
 * Best result achieved when both multiplier and shift are prime numbers.
 */
public class MultiplicationShift implements Masker {
    private final long shift;
    private final int multiplier;

    /**
     * Constructs multiplication shift masker.
     *
     * @param shift      Shift offset.
     * @param multiplier Multiplier.
     */
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

    @Override
    public String toString() {
        return "{Multiplication " + multiplier + " shift " + shift + "}";
    }
}
