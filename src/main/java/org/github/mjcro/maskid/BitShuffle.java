package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashSet;

/**
 * Base abstract class for bit shuffle operations.
 */
public abstract class BitShuffle {
    private final int bitSize, byteSize;
    protected final byte[] masking;
    protected final byte[] unmasking;

    /**
     * Private constructor, that actually does all work.
     *
     * @param size          Transformation matrix size.
     * @param maskingMatrix Masking transformation matrix.
     * @param verify        True to verify matrix.
     */
    protected BitShuffle(final int size, final byte[] maskingMatrix, final boolean verify) {
        if (verify) {
            if (maskingMatrix == null) {
                throw new NullPointerException("maskingMatrix");
            }
            if (maskingMatrix.length != size) {
                throw new IllegalArgumentException("Masking matrix should have exact " + size + " positions");
            }
            HashSet<Byte> unique = new HashSet<>();
            for (byte b : maskingMatrix) {
                if (b < 0 || b > size - 1) {
                    throw new IllegalArgumentException("Invalid offset " + b);
                }
                unique.add(b);
            }
            if (unique.size() != size) {
                throw new IllegalArgumentException("Exactly " + size + " unique elements expected");
            }
        }

        this.bitSize = size;
        this.byteSize = size / 8;
        this.masking = maskingMatrix;
        this.unmasking = new byte[size];
        for (int i = 0; i < bitSize; i++) {
            this.unmasking[this.masking[i]] = (byte) i;
        }
    }

    /**
     * Performs value transformation using given bit matrix.
     *
     * @param sourceBuffer Source value.
     * @param matrix       Transformation matrix.
     * @return Masked value.
     */
    protected long transform(final ByteBuffer sourceBuffer, final byte[] matrix) {
        BitSet sourceBitSet = BitSet.valueOf(sourceBuffer.array());
        BitSet resultBitSet = new BitSet(bitSize);

        for (int i = 0; i < bitSize; i++) {
            resultBitSet.set(matrix[i], sourceBitSet.get(i));
        }

        byte[] out = resultBitSet.toByteArray();
        ByteBuffer wrapped;
        if (out.length == byteSize) {
            wrapped = ByteBuffer.wrap(out);
        } else {
            byte[] empty = new byte[byteSize];
            System.arraycopy(out, 0, empty, 0, out.length);
            wrapped = ByteBuffer.wrap(empty);
        }

        return byteSize == 4
                ? wrapped.getInt()
                : wrapped.getLong();
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
        for (int i = 0; i < 64; i++) {
            if (i > 0 && i % 8 == 0) {
                System.out.print(" ");
            }
            System.out.print(bitSet.get(i) ? "1" : "0");
        }
        System.out.println(" <- " + value);
    }

    /**
     * Outputs given value as bits into stdout.
     *
     * @param value Value to dump.
     */
    public static void dump(final int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);

        BitSet bitSet = BitSet.valueOf(buffer.array());
        for (int i = 0; i < 32; i++) {
            if (i > 0 && i % 8 == 0) {
                System.out.print(" ");
            }
            System.out.print(bitSet.get(i) ? "1" : "0");
        }
        System.out.println(" <- " + value);
    }
}
