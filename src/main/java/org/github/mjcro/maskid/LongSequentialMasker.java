package org.github.mjcro.maskid;

import java.util.Collection;
import java.util.Objects;

/**
 * {@link LongMasker} implementation that sequentially runs all given
 * in constructor maskers.
 */
public class LongSequentialMasker implements LongMasker {
    private final LongMasker[] maskers;

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public LongSequentialMasker(final Collection<LongMasker> maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers.stream().toArray(LongMasker[]::new);
    }

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public LongSequentialMasker(final LongMasker... maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers;
    }

    @Override
    public long maskLong(final long value) {
        long masked = value;
        for (final LongMasker masker : maskers) {
            masked = masker.maskLong(masked);
        }
        return masked;
    }

    @Override
    public long unmaskLong(final long value) {
        long unmasked = value;
        for (int i = maskers.length - 1; i >= 0; i--) {
            unmasked = maskers[i].unmaskLong(unmasked);
        }
        return unmasked;
    }

    @Override
    public String toString() {
        return "{SequentialMasker with " + maskers.length + " maskers}";
    }
}
