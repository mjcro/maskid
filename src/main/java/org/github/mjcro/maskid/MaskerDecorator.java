package org.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Function;

/**
 * Decorator over {@link Masker} which allows to mask data
 * into any type (String for example)
 *
 * @param <T> Masking type.
 */
public class MaskerDecorator<T> {
    private final Masker masker;
    private final Function<Long, T> maskConverter;
    private final Function<T, Long> unmaskConverter;

    /**
     * Constructs masking decorator.
     *
     * @param masker          Underlying masker.
     * @param maskConverter   Function to map long values into desired custom type.
     * @param unmaskConverter Function to map custom type into long.
     */
    public MaskerDecorator(
            final Masker masker,
            final Function<Long, T> maskConverter,
            final Function<T, Long> unmaskConverter
    ) {
        this.masker = Objects.requireNonNull(masker, "masker");
        this.maskConverter = Objects.requireNonNull(maskConverter, "maskConverter");
        this.unmaskConverter = Objects.requireNonNull(unmaskConverter, "unmaskConverter");
    }

    public T mask(final long value) {
        return maskConverter.apply(masker.mask(value));
    }

    public long unmask(final T value) {
        return masker.unmask(unmaskConverter.apply(value));
    }
}
