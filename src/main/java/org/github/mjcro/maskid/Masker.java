package org.github.mjcro.maskid;

/**
 * Defines transformers able to perform reversible int64 (Java long)
 * transformations (masking)
 */
public interface Masker {
    /**
     * Performs masking (reversible identifier transformation).
     *
     * @param value Value to mask.
     * @return Masked value.
     */
    long mask(long value);

    /**
     * Performs unmasking.
     *
     * @param value Value to unmask.
     * @return Original value.
     */
    long unmask(long value);
}
