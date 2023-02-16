package org.github.mjcro.maskid;

import java.util.Collection;
import java.util.Objects;

/**
 * {@link Masker} implementation that sequentially runs all given
 * in constructor maskers.
 */
public class Sequential implements Masker {
    private final Masker[] maskers;

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public Sequential(final Collection<Masker> maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers.stream().toArray(Masker[]::new);
    }

    /**
     * Constructs sequential masker.
     *
     * @param maskers Maskers to use.
     */
    public Sequential(final Masker... maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers;
    }

    @Override
    public long mask(final long value) {
        long masked = value;
        for (final Masker masker : maskers) {
            masked = masker.mask(masked);
        }
        return masked;
    }

    @Override
    public long unmask(final long value) {
        long unmasked = value;
        for (int i = maskers.length - 1; i >= 0; i--) {
            unmasked = maskers[i].unmask(unmasked);
        }
        return unmasked;
    }

    @Override
    public String toString() {
        return "{SequentialMasker with " + maskers.length + " maskers}";
    }
}
