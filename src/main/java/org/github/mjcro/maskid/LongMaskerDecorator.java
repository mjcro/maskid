package org.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Function;

/**
 * Decorator over {@link LongMasker} which allows masking data
 * into any type (String for example)
 *
 * @param <T> Masking type.
 */
public class LongMaskerDecorator<T> {
    private final LongMasker masker;
    private final Function<Long, T> maskConverter;
    private final Function<T, Long> unmaskConverter;

    /**
     * Constructs masking decorator.
     *
     * @param masker          Underlying masker.
     * @param maskConverter   Function to map long values into desired custom type.
     * @param unmaskConverter Function to map custom type into long.
     */
    public LongMaskerDecorator(
            final LongMasker masker,
            final Function<Long, T> maskConverter,
            final Function<T, Long> unmaskConverter
    ) {
        this.masker = Objects.requireNonNull(masker, "masker");
        this.maskConverter = Objects.requireNonNull(maskConverter, "maskConverter");
        this.unmaskConverter = Objects.requireNonNull(unmaskConverter, "unmaskConverter");
    }

    public T mask(final long value) {
        return maskConverter.apply(masker.maskLong(value));
    }

    public long unmask(final T value) {
        return masker.unmaskLong(unmaskConverter.apply(value));
    }
}
