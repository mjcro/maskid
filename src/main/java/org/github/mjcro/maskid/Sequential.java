package org.github.mjcro.maskid;

import java.util.Collection;
import java.util.Objects;

public class Sequential implements Masker {
    private final Masker[] maskers;

    public Sequential(final Collection<Masker> maskers) {
        Objects.requireNonNull(maskers, "maskers");
        this.maskers = maskers.toArray(Masker[]::new);
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
}
