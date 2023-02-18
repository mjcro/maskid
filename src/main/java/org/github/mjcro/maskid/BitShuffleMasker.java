package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Masker implementation that shuffles bits of long value
 * making result unpredictable for external observer.
 */
public class BitShuffleMasker implements LongMasker {
    private final byte[] masking;
    private final byte[] unmasking;

    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given seed.
     *
     * @param seed Random seed.
     * @return Masker.
     */
    public static BitShuffleMasker ofRandomSeed(final long seed) {
        return ofRandom(new Random(seed));
    }

    /**
     * Constructs bit shuffling masker using random shuffling
     * matrix generated using given random number generator.
     *
     * @param rng Random number generator.
     * @return Masker.
     */
    public static BitShuffleMasker ofRandom(final Random rng) {
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

        return new BitShuffleMasker(matrix);
    }

    /**
     * Constructs masker that reverses most bits in value.
     *
     * @return Masker.
     */
    public static BitShuffleMasker reverse() {
        return new BitShuffleMasker(REVERSED, false);
    }

    /**
     * Constructs masker that uses prebuilt random transformation matrix.
     *
     * @return Masker.
     */
    public static BitShuffleMasker randomSetOne() {
        return new BitShuffleMasker(RANDOM_SET_1, false);
    }

    /**
     * Constructs bit shuffler masker using given transformation matrix.
     *
     * @param maskingMatrix Masking transformation matrix.
     */
    public BitShuffleMasker(final byte[] maskingMatrix) {
        this(maskingMatrix, true);
    }

    /**
     * Private constructor, that actually does all work.
     *
     * @param maskingMatrix Masking transformation matrix.
     * @param verify        True to verify matrix.
     */
    private BitShuffleMasker(final byte[] maskingMatrix, final boolean verify) {
        if (verify) {
            if (maskingMatrix == null) {
                throw new NullPointerException("maskingMatrix");
            }
            if (maskingMatrix.length != 64) {
                throw new IllegalArgumentException("Masking matrix should have exact 64 positions");
            }
            HashSet<Byte> unique = new HashSet<>();
            for (byte b : maskingMatrix) {
                if (b < 0 || b > 63) {
                    throw new IllegalArgumentException("Invalid offset " + b);
                }
                unique.add(b);
            }
            if (unique.size() != 64) {
                throw new IllegalArgumentException("Exactly 64 unique elements expected");
            }
        }

        this.masking = maskingMatrix;
        this.unmasking = new byte[64];
        for (int i = 0; i < 64; i++) {
            this.unmasking[this.masking[i]] = (byte) i;
        }
    }

    @Override
    public long maskLong(final long value) {
        return transform(value, masking);
    }

    @Override
    public long unmaskLong(final long value) {
        return transform(value, unmasking);
    }

    /**
     * Performs int64 value transformation using given bit matrix.
     *
     * @param value  Source value.
     * @param matrix Transformation matrix.
     * @return Masked value.
     */
    private long transform(final long value, final byte[] matrix) {
        if (value == 0) {
            return 0;
        }

        ByteBuffer sourceBuffer = ByteBuffer.allocate(Long.BYTES);
        sourceBuffer.putLong(value);

        BitSet sourceBitSet = BitSet.valueOf(sourceBuffer.array());
        BitSet resultBitSet = new BitSet(64);

        for (int i = 0; i < 64; i++) {
            resultBitSet.set(matrix[i], sourceBitSet.get(i));
        }

        byte[] out = resultBitSet.toByteArray();
        if (out.length == Long.BYTES) {
            return ByteBuffer.wrap(out).getLong();
        } else {
            byte[] empty = new byte[Long.BYTES];
            System.arraycopy(out, 0, empty, 0, out.length);
            return ByteBuffer.wrap(empty).getLong();
        }
    }

    /**
     * Outputs given value as bits into stdout.
     *
     * @param value Value to dump.
     */
    public static void dump(final long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);

        BitSet bitSet = BitSet.valueOf(buffer.array());
        System.out.print("[" + bitSet.size() + "] ");
        for (int i = 0; i < bitSet.size(); i++) {
            if (i > 0 && i % 8 == 0) {
                System.out.print(" ");
            }
            System.out.print(bitSet.get(i) ? "1" : "0");
        }
        System.out.println(" <- " + value);
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
