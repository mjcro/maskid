package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Masker implementation that shuffles bits of long value
 * making result unpredictable for external observer.
 */
public class LongBitShuffleMasker extends BitShuffle implements LongMasker {
    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given seed.
     *
     * @param seed Random seed.
     * @return Masker.
     */
    public static LongBitShuffleMasker ofRandomSeed(final long seed) {
        return ofRandom(new Random(seed));
    }

    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given random number generator.
     *
     * @param rng Random number generator.
     * @return Masker.
     */
    public static LongBitShuffleMasker ofRandom(final Random rng) {
        Objects.requireNonNull(rng, "rng");

        ArrayList<Integer> randomized = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            randomized.add(i, i);
        }
        Collections.shuffle(randomized, rng);

        byte[] matrix = new byte[64];
        for (int i = 0; i < 64; i++) {
            matrix[i] = randomized.get(i).byteValue();
        }

        return new LongBitShuffleMasker(matrix);
    }

    /**
     * Constructs masker that reverses most bits in value.
     *
     * @return Masker.
     */
    public static LongBitShuffleMasker reverse() {
        return new LongBitShuffleMasker(REVERSED, false);
    }

    /**
     * Constructs masker that uses prebuilt random transformation matrix.
     *
     * @return Masker.
     */
    public static LongBitShuffleMasker randomSetOne() {
        return new LongBitShuffleMasker(RANDOM_SET_1, false);
    }

    /**
     * Constructs bit shuffler masker using given transformation matrix.
     *
     * @param maskingMatrix Masking transformation matrix.
     */
    public LongBitShuffleMasker(final byte[] maskingMatrix) {
        this(maskingMatrix, true);
    }

    /**
     * Private constructor, that actually does all work.
     *
     * @param maskingMatrix Masking transformation matrix.
     * @param verify        True to verify matrix.
     */
    private LongBitShuffleMasker(final byte[] maskingMatrix, final boolean verify) {
        super(64, maskingMatrix, verify);
    }

    @Override
    public long maskLong(final long value) {
        if (value == 0) {
            return 0;
        }
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(value);
        return transform(bb, masking);
    }

    @Override
    public long unmaskLong(final long value) {
        if (value == 0) {
            return 0;
        }
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(value);
        return transform(bb, unmasking);
    }

    /**
     * Randomly shuffled bits.
     */
    private static final byte[] RANDOM_SET_1 = new byte[]{
            33, 57, 61, 22, 47, 50, 38, 7,
            4, 27, 20, 8, 42, 35, 32, 1,
            21, 53, 16, 51, 37, 39, 63, 9,
            13, 24, 29, 46, 6, 5, 54, 56,
            3, 0, 28, 43, 31, 52, 26, 49,
            41, 44, 58, 18, 34, 15, 36, 2,
            12, 19, 48, 59, 14, 55, 23, 60,
            40, 10, 17, 30, 11, 25, 45, 62
    };

    /**
     * Reversed big endian int64 bytes.
     */
    private static final byte[] REVERSED = new byte[]{
            56, 57, 58, 59, 60, 61, 62, 7,
            48, 49, 50, 51, 52, 53, 54, 55,
            40, 41, 42, 43, 44, 45, 46, 47,
            32, 33, 34, 35, 36, 37, 38, 39,
            24, 25, 26, 27, 28, 29, 30, 31,
            16, 17, 18, 19, 20, 21, 22, 23,
            8, 9, 10, 11, 12, 13, 14, 15,
            0, 1, 2, 3, 4, 5, 6, 63
    };

    /**
     * Reference.
     */
    private static final byte[] REFERENCE = new byte[]{
            0, 1, 2, 3, 4, 5, 6, 7,
            8, 9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29, 30, 31,
            32, 33, 34, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 44, 45, 46, 47,
            48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 58, 59, 60, 61, 62, 63
    };
}
