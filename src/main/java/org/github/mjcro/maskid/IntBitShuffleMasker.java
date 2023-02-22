package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Masker implementation that shuffles bits of int value
 * making result unpredictable for external observer.
 */
public class IntBitShuffleMasker extends BitShuffle implements IntMasker {
    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given seed.
     *
     * @param seed Random seed.
     * @return Masker.
     */
    public static IntBitShuffleMasker ofRandomSeed(final long seed) {
        return ofRandom(new Random(seed));
    }

    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given random number generator.
     *
     * @param rng Random number generator.
     * @return Masker.
     */
    public static IntBitShuffleMasker ofRandom(final Random rng) {
        Objects.requireNonNull(rng, "rng");

        ArrayList<Integer> randomized = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            randomized.add(i, i);
        }
        Collections.shuffle(randomized, rng);

        byte[] matrix = new byte[32];
        for (int i = 0; i < 32; i++) {
            matrix[i] = randomized.get(i).byteValue();
        }

        return new IntBitShuffleMasker(matrix);
    }

    /**
     * Constructs masker that reverses most bits in value.
     *
     * @return Masker.
     */
    public static IntBitShuffleMasker reverse() {
        return new IntBitShuffleMasker(REVERSED, false);
    }

    /**
     * Constructs masker that uses prebuilt random transformation matrix.
     *
     * @return Masker.
     */
    public static IntBitShuffleMasker randomSetOne() {
        return new IntBitShuffleMasker(RANDOM_SET_1, false);
    }

    /**
     * Constructs bit shuffler masker using given transformation matrix.
     *
     * @param maskingMatrix Masking transformation matrix.
     */
    public IntBitShuffleMasker(final byte[] maskingMatrix) {
        this(maskingMatrix, true);
    }

    /**
     * Private constructor, that actually does all work.
     *
     * @param maskingMatrix Masking transformation matrix.
     * @param verify        True to verify matrix.
     */
    private IntBitShuffleMasker(final byte[] maskingMatrix, final boolean verify) {
        super(32, maskingMatrix, verify);
    }

    @Override
    public int maskInt(final int value) {
        if (value == 0) {
            return 0;
        }
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.putInt(value);
        return (int) transform(bb, masking);
    }

    @Override
    public int unmaskInt(final int value) {
        if (value == 0) {
            return 0;
        }
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.putInt(value);
        return (int) transform(bb, unmasking);
    }

    /**
     * Randomly shuffled bits.
     */
    private static final byte[] RANDOM_SET_1 = new byte[]{
            16, 31, 24, 9, 28, 4, 29, 7,
            11, 22, 14, 8, 1, 25, 12, 15,
            18, 30, 5, 21, 19, 17, 26, 10,
            27, 20, 13, 6, 3, 2, 0, 23
    };

    /**
     * Reversed big endian int64 bytes.
     */
    private static final byte[] REVERSED = new byte[]{
            24, 25, 26, 27, 28, 29, 30, 7,
            16, 17, 18, 19, 20, 21, 22, 23,
            8, 9, 10, 11, 12, 13, 14, 15,
            0, 1, 2, 3, 4, 5, 6, 31
    };

    /**
     * Reference.
     */
    private static final byte[] REFERENCE = new byte[]{
            0, 1, 2, 3, 4, 5, 6, 7,
            8, 9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29, 30, 31,
    };
}
