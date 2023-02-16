package org.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Function;

public class CustomDecorator<T> {
    private final Masker masker;
    private final Function<Long, T> maskConverter;
    private final Function<T, Long> unmaskConverter;

    public CustomDecorator(
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
