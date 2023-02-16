package org.github.mjcro.maskid;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class BitShuffler implements Masker {
    private final byte[] masking;
    private final byte[] unmasking;

    public BitShuffler() {
        this(DEFAULT);
    }

    public BitShuffler(final byte[] masking) {
        if (masking == null) {
            throw new NullPointerException("masking");
        }
        if (masking.length != 64) {
            throw new IllegalArgumentException("Masking rules array should have exact 64 positions");
        }

        this.masking = masking;
        this.unmasking = new byte[64];
        for (int i=0; i < 64; i++) {
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

        for (int i=0; i < 64; i++) {
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
