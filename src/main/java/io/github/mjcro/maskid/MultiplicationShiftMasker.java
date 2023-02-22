package io.github.mjcro.maskid;

import java.math.BigInteger;

/**
 * Perform multiplication shift masking by formula:
 * masked = original * multiplier + shift.
 * Sign of given value is never changed - positive remains positive and negative remains negative.
 * <p>
 * Best result achieved when both multiplier and shift are prime numbers.
 */
public class MultiplicationShiftMasker implements IntMasker, LongMasker {
    private final BigInteger modLong = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
    private final BigInteger modInt = BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.ONE);
    private final BigInteger shift;
    private final BigInteger multiplier;

    /**
     * Constructs multiplication shift masker.
     *
     * @param shift      Shift offset.
     * @param multiplier Multiplier.
     */
    public MultiplicationShiftMasker(final long shift, final long multiplier) {
        if (shift < 0) {
            throw new IllegalArgumentException("Shift should be positive, " + shift + " given");
        }
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier should be positive, " + multiplier + " given");
        }

        this.shift = BigInteger.valueOf(shift);
        this.multiplier = BigInteger.valueOf(multiplier);
    }

    private BigInteger mask(final BigInteger modulus, BigInteger bi) {
        bi = bi.multiply(multiplier);
        if (bi.signum() >= 0) {
            bi = bi.add(shift);
            return bi.mod(modulus);
        } else {
            bi = bi.subtract(shift);
            return bi.negate().mod(modulus).negate();
        }
    }

    @Override
    public int maskInt(final int value) {
        return mask(modInt, BigInteger.valueOf(value)).intValue();
    }

    @Override
    public long maskLong(final long value) {
        return mask(modLong, BigInteger.valueOf(value)).longValue();
    }

    private BigInteger unmask(final BigInteger modulus, BigInteger bi) {
        if (bi.signum() >= 0) {
            bi = bi.subtract(shift);
            bi = bi.divide(multiplier);
            if (bi.signum() < 0) {
                bi = bi.add(modulus);
            }
        } else {
            bi = bi.add(shift);
            bi = bi.divide(multiplier);
            if (bi.signum() >= 0) {
                bi = bi.subtract(modulus);
            }
        }
        return bi;
    }

    @Override
    public int unmaskInt(final int value) {
        return unmask(modInt, BigInteger.valueOf(value)).intValue();
    }

    @Override
    public long unmaskLong(final long value) {
        return unmask(modLong, BigInteger.valueOf(value)).longValue();
    }

    @Override
    public String toString() {
        return "{Multiplication " + multiplier + " shift " + shift + "}";
    }
}
