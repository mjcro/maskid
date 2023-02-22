package org.github.mjcro.maskid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Masker implementation that works over small set of
 * values covered by dictionary.
 */
public class IntDictionary implements IntMasker {
    private final HashMap<Integer, Integer> matrix;
    private final HashMap<Integer, Integer> reverse;

    /**
     * Constructs new dictionary masker.
     *
     * @param dictionary Dictionary to use.
     */
    public IntDictionary(final Map<Integer, Integer> dictionary) {
        if (dictionary == null || dictionary.isEmpty()) {
            throw new IllegalArgumentException("Empty dictionary");
        }

        this.matrix = new HashMap<>(dictionary);
        this.reverse = dictionary.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getValue,
                Map.Entry::getKey,
                (a, b) -> a,
                HashMap::new
        ));

        if (this.matrix.size() != this.reverse.size()) {
            throw new IllegalArgumentException("Dictionary contains not unique values");
        }
    }

    @Override
    public int maskInt(final int value) {
        Integer masked = matrix.get(value);
        if (masked == null) {
            throw new IndexOutOfBoundsException("No forward mapping for " + value);
        }
        return masked;
    }

    @Override
    public int unmaskInt(final int value) {
        Integer unmasked = reverse.get(value);
        if (unmasked == null) {
            throw new IndexOutOfBoundsException("No reverse mapping for " + value);
        }
        return unmasked;
    }

    @Override
    public String toString() {
        return "{Dictionary with " + this.matrix.size() + " elements}";
    }
}
