package io.github.mjcro.maskid;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Component able to construct and return masker.
 * Every masker is a factory to itself.
 */
@FunctionalInterface
public interface IntMaskerFactory {
    /**
     * Constructs masker factory using given supplier.
     *
     * @param supplier Supplier to use.
     * @return Masker factory.
     */
    static IntMaskerFactory ofSupplier(final Supplier<IntMasker> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        return supplier::get;
    }

    /**
     * @return Masker.
     */
    IntMasker getIntMasker();
}
