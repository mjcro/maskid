package org.github.mjcro.maskid;

/**
 * Defines transformers able to perform reversible int32 (Java int)
 * transformations (masking)
 */
public interface IntMasker {
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
}
