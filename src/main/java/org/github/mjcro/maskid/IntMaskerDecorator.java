package org.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Function;

/**
 * Decorator over {@link IntMasker} which allows masking data
 * into any type (String for example)
 *
 * @param <T> Masking type.
 */
public class IntMaskerDecorator<T> {
    private final IntMasker masker;
    private final Function<Integer, T> maskConverter;
    private final Function<T, Integer> unmaskConverter;

    /**
     * Constructs masking decorator.
     *
     * @param masker          Underlying masker.
     * @param maskConverter   Function to map long values into desired custom type.
     * @param unmaskConverter Function to map custom type into long.
     */
    public IntMaskerDecorator(
            final IntMasker masker,
            final Function<Integer, T> maskConverter,
            final Function<T, Integer> unmaskConverter
    ) {
        this.masker = Objects.requireNonNull(masker, "masker");
        this.maskConverter = Objects.requireNonNull(maskConverter, "maskConverter");
        this.unmaskConverter = Objects.requireNonNull(unmaskConverter, "unmaskConverter");
    }

    public T mask(final int value) {
        return maskConverter.apply(masker.maskInt(value));
    }

    public int unmask(final T value) {
        return masker.unmaskInt(unmaskConverter.apply(value));
    }
}
