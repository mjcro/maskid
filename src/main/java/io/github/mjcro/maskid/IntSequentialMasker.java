package io.github.mjcro.maskid;

import java.util.Collection;
import java.util.Objects;

/**
 * {@link IntMasker} implementation that sequentially runs all given
 * in constructor maskers.
 */
public class IntSequentialMasker implements IntMasker {
    private final IntMasker[] maskers;

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public IntSequentialMasker(final Collection<IntMasker> maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers.stream().toArray(IntMasker[]::new);
    }

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public IntSequentialMasker(final IntMasker... maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers;
    }

    @Override
    public int maskInt(final int value) {
        int masked = value;
        for (final IntMasker masker : maskers) {
            masked = masker.maskInt(masked);
        }
        return masked;
    }

    @Override
    public int unmaskInt(final int value) {
        int unmasked = value;
        for (int i = maskers.length - 1; i >= 0; i--) {
            unmasked = maskers[i].unmaskInt(unmasked);
        }
        return unmasked;
    }

    @Override
    public String toString() {
        return "{SequentialMasker with " + maskers.length + " maskers}";
    }
}
