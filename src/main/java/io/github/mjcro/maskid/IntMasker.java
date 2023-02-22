package io.github.mjcro.maskid;

/**
 * Defines transformers able to perform reversible int32 (Java int)
 * transformations (masking)
 */
public interface IntMasker extends IntMaskerFactory {
    /**
     * Performs masking (reversible identifier transformation).
     *
     * @param value Value to mask.
     * @return Masked value.
     */
    int maskInt(int value);

    /**
     * Performs unmasking.
     *
     * @param value Value to unmask.
     * @return Original value.
     */
    int unmaskInt(int value);

    @Override
    default IntMasker getIntMasker() {
        return this;
    }
}
