package org.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Component able to construct and return masker.
 * Every masker is a factory to itself.
 */
@FunctionalInterface
public interface LongMaskerFactory {
    /**
     * Constructs masker factory using given supplier.
     *
     * @param supplier Supplier to use.
     * @return Masker factory.
     */
    static LongMaskerFactory ofSupplier(final Supplier<LongMasker> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        return supplier::get;
    }

    /**
     * @return Masker.
     */
    LongMasker getLongMasker();
}
