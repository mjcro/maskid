package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class BitShuffler implements Masker {
    private final byte[] masking;
    private final byte[] unmasking;

    public static BitShuffler ofRandomSeed(final long seed) {
        return ofRandom(new Random(seed));
    }

    public static BitShuffler ofRandom(final Random random) {
        Objects.requireNonNull(random, "random");

        ArrayList<Integer> randomized = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            randomized.add(i, i);
        }
        Collections.shuffle(randomized, random);

        byte[] matrix = new byte[64];
        for (int i = 0; i < 64; i++) {
            matrix[i] = randomized.get(i).byteValue();
        }

        return new BitShuffler(matrix);
    }

    public BitShuffler() {
        this(DEFAULT, false);
    }

    public BitShuffler(final byte[] maskingMatrix) {
        this(maskingMatrix, true);
    }

    private BitShuffler(final byte[] maskingMatrix, final boolean verify) {
        if (verify) {
            if (maskingMatrix == null) {
                throw new NullPointerException("maskingMatrix");
            }
            if (maskingMatrix.length != 64) {
                throw new IllegalArgumentException("Masking matrix should have exact 64 positions");
            }
        }

        this.masking = maskingMatrix;
        this.unmasking = new byte[64];
        for (int i = 0; i < 64; i++) {
            this.unmasking[this.masking[i]] = (byte) i;
        }
    }

    public long mask(final long value) {
        return transform(value, masking);
    }

    public long unmask(final long value) {
        return transform(value, unmasking);
    }

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

    public void dump(final long value) {
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

    private static final byte[] DEFAULT = new byte[]{
            33,
            57,
            61,
            22,
            47,
            50,
            38,
            7, // SIGN
            4,
            27,
            20,
            8,
            42,
            35,
            32,
            1,
            21,
            53,
            16,
            51,
            37,
            39,
            63,
            9,
            13,
            24,
            29,
            46,
            6,
            5,
            54,
            56,
            3,
            0,
            28,
            43,
            31,
            52,
            26,
            49,
            41,
            44,
            58,
            18,
            34,
            15,
            36,
            2,
            12,
            19,
            48,
            59,
            14,
            55,
            23,
            60,
            40,
            10,
            17,
            30,
            11,
            25,
            45,
            62
    };
}
