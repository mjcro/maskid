package org.github.mjcro.maskid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Masker implementation that works over small set of
 * values covered by dictionary.
 */
public class LongDictionary implements LongMasker {
    private final HashMap<Long, Long> matrix;
    private final HashMap<Long, Long> reverse;

    /**
     * Constructs new dictionary masker.
     *
     * @param dictionary Dictionary to use.
     */
    public LongDictionary(final Map<Long, Long> dictionary) {
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
    public long maskLong(final long value) {
        Long masked = matrix.get(value);
        if (masked == null) {
            throw new IndexOutOfBoundsException("No forward mapping for " + value);
        }
        return masked;
    }

    @Override
    public long unmaskLong(final long value) {
        Long unmasked = reverse.get(value);
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
