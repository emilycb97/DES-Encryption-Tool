package org.example;

import org.example.exceptions.KeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Klasa testów dla KeyGenerator
 * Sprawdza poprawność generowania kluczy rundowych na podstawie podanych wartości wejściowych
 */
public class KeyGeneratorTest {

    private static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    private static final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    public static String byteArrayToBitString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            // Konwersja bajtu do stringa binarnego, z uwzględnieniem znaku
            String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            sb.append(binary); // spacja opcjonalna
        }
        return sb.toString().trim();
    }

    /**
     * Test sprawdzający poprawność generowania kluczy rundowych dla klucza "0123456789abcdef".
     *
     * @throws KeyException Jeśli wystąpi błąd przy inicjalizacji KeyGenerator
     */
    @Test
    void keyTest1() throws KeyException {
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        KeyGenerator key = new KeyGenerator(input);

        Assertions.assertEquals("0000000100100011010001010110011110001001101010111100110111101111", byteArrayToBitString(input));

        Assertions.assertEquals(("000010110000001001100111100110110100100110100101")
                , byteArrayToBitString(key.getKeyValues(0)));
//        011010 011010 011001 011001 001001 010110 101000 100110
        Assertions.assertEquals(("011010011010011001011001001001010110101000100110")
                , byteArrayToBitString(key.getKeyValues(1)));
        Assertions.assertEquals(("010001011101010010001010101101000010100011010010")
                , byteArrayToBitString(key.getKeyValues(2)));
        Assertions.assertEquals(("011100101000100111010010101001011000001001010111")
                , byteArrayToBitString(key.getKeyValues(3)));
        Assertions.assertEquals(("001111001110100000000011000101111010011011000010")
                , byteArrayToBitString(key.getKeyValues(4)));
        Assertions.assertEquals(("001000110010010100011110001111001000010101000101")
                , byteArrayToBitString(key.getKeyValues(5)));
        Assertions.assertEquals(("011011000000010010010101000010101110010011000110")
                , byteArrayToBitString(key.getKeyValues(6)));
        Assertions.assertEquals(("010101111000100000111000011011001110010110000001")
                , byteArrayToBitString(key.getKeyValues(7)));
        Assertions.assertEquals(("110000001100100111101001001001101011100000111001")
                , byteArrayToBitString(key.getKeyValues(8)));
        Assertions.assertEquals(("100100011110001100000111011000110001110101110010")
                , byteArrayToBitString(key.getKeyValues(9)));
        Assertions.assertEquals(("001000010001111110000011000011011000100100111010")
                , byteArrayToBitString(key.getKeyValues(10)));
        Assertions.assertEquals(("011100010011000011100101010001010101110001010100")
                , byteArrayToBitString(key.getKeyValues(11)));
        Assertions.assertEquals(("100100011100010011010000010010011000000011111100")
                , byteArrayToBitString(key.getKeyValues(12)));
        Assertions.assertEquals(("010101000100001110110110100000011101110010001101")
                , byteArrayToBitString(key.getKeyValues(13)));
        Assertions.assertEquals(("101101101001000100000101000010100001011010110101")
                , byteArrayToBitString(key.getKeyValues(14)));
        Assertions.assertEquals(("110010100011110100000011101110000111000000110010")
                , byteArrayToBitString(key.getKeyValues(15)));
    }

    /**
     * Test sprawdzający poprawność generowania kluczy rundowych dla klucza "133457799BBCDFF1".
     *
     * @throws KeyException Jeśli wystąpi błąd przy inicjalizacji KeyGenerator
     */
    @Test
    void keyTest2() throws KeyException {
//        00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001
        byte[] input = {19, 52, 87, 121, -101,-68, -33, -15};
        KeyGenerator key = new KeyGenerator(input);
        Assertions.assertEquals(("000110110000001011101111111111000111000001110010")
                , byteArrayToBitString(key.getKeyValues(0)));
        Assertions.assertEquals(("011110011010111011011001110110111100100111100101")
                , byteArrayToBitString(key.getKeyValues(1)));
        Assertions.assertEquals(("010101011111110010001010010000101100111110011001")
                , byteArrayToBitString(key.getKeyValues(2)));
        Assertions.assertEquals(("011100101010110111010110110110110011010100011101")
                , byteArrayToBitString(key.getKeyValues(3)));
        Assertions.assertEquals(("011111001110110000000111111010110101001110101000")
                , byteArrayToBitString(key.getKeyValues(4)));
        Assertions.assertEquals(("011000111010010100111110010100000111101100101111")
                , byteArrayToBitString(key.getKeyValues(5)));
        Assertions.assertEquals(("111011001000010010110111111101100001100010111100")
                , byteArrayToBitString(key.getKeyValues(6)));
        Assertions.assertEquals(("111101111000101000111010110000010011101111111011")
                , byteArrayToBitString(key.getKeyValues(7)));
        Assertions.assertEquals(("111000001101101111101011111011011110011110000001")
                , byteArrayToBitString(key.getKeyValues(8)));
        Assertions.assertEquals(("101100011111001101000111101110100100011001001111")
                , byteArrayToBitString(key.getKeyValues(9)));
        Assertions.assertEquals(("001000010101111111010011110111101101001110000110")
                , byteArrayToBitString(key.getKeyValues(10)));
        Assertions.assertEquals(("011101010111000111110101100101000110011111101001")
                , byteArrayToBitString(key.getKeyValues(11)));
        Assertions.assertEquals(("100101111100010111010001111110101011101001000001")
                , byteArrayToBitString(key.getKeyValues(12)));
        Assertions.assertEquals(("010111110100001110110111111100101110011100111010")
                , byteArrayToBitString(key.getKeyValues(13)));
        Assertions.assertEquals(("101111111001000110001101001111010011111100001010")
                , byteArrayToBitString(key.getKeyValues(14)));
        Assertions.assertEquals(("110010110011110110001011000011100001011111110101")
                , byteArrayToBitString(key.getKeyValues(15)));
    }

    @Test
    void stepByStepTest() throws KeyException {
        //00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        KeyGenerator keyGenerator = new KeyGenerator(input);

        //11110000 11001100 10101010 00001010 10101100 11001111 00000000
        byte[] key56 = {-16, -52, -86, 10, -84, -49, 0};
        Assertions.assertArrayEquals(key56, keyGenerator.permute(input, PC1));

        //podział na dwa klucze
//        C0 = 11110000 11001100 10101010 0000 0000
//        D0 = 10101010 11001100 11110000 0000 0000
        byte[] c0 = {-16, -52, -86, 0};
        byte[] d0 = {-86, -52, -16, 0};

        Assertions.assertArrayEquals(c0, keyGenerator.getBitsAsByteArray(key56, 0, 28));
        Assertions.assertArrayEquals(d0, keyGenerator.getBitsAsByteArray(key56, 28, 28));

        //przesunięcia bitów
//        C1 = 11100001 10011001 01010100 0001 0000
//        D1 = 01010101 10011001 11100000 0001 0000
        byte[] c1 = {-31, -103, 84, 16};
        byte[] d1 = {85, -103, -32, 16};
        Assertions.assertArrayEquals(c1, keyGenerator.shiftBitsByOneHalfKey(c0));
        Assertions.assertArrayEquals(d1, keyGenerator.shiftBitsByOneHalfKey(d0));


        //przesunięcia bitów
//        C2 = 11000011 00110010 10101000 0011 0000
//        D2 = 10101011 00110011 11000000 0010 0000
        byte[] c2 = {-61, 50, -88, 48};
        byte[] d2 = {-85, 51, -64, 32};
        Assertions.assertArrayEquals(c2, keyGenerator.shiftBitsByOneHalfKey(c1));
        Assertions.assertArrayEquals(d2, keyGenerator.shiftBitsByOneHalfKey(d1));

        //przesunięcia bitów
//        C3 = 00001100 11001010 10100000 1111 0000
//        D3 = 10101100 11001111 00000000 1010 0000
        byte[] c3 = {12, -54, -96, -16};
        byte[] d3 = {-84, -49, 0, -96};
        Assertions.assertArrayEquals(c3, keyGenerator.shiftBitsByTwoHalfKey(c2));
        Assertions.assertArrayEquals(d3, keyGenerator.shiftBitsByTwoHalfKey(d2));

// i tak dalej przejdźmy do łączenia
//        CD3 = 00001100 11001010 10100000 11111010 11001100 11110000 00001010
        d3 = keyGenerator.shiftDkey(d3);
        byte[] cd3 = {12, -54, -96, -6, -52, -16, 10};

        Assertions.assertArrayEquals(cd3, KeyGenerator.combine(c3, d3));

        //permutacja PC-2
//        K3 = 01000101 11010100 10001010 10110100 00101000 11010010
        byte[] k3 = {69, -44, -118, -76, 40, -46};
        Assertions.assertArrayEquals(k3, keyGenerator.permute(cd3, PC2));


    }

    @Test
    void keyGeneratorTest() throws KeyException {
        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        KeyGenerator keyGenerator = new KeyGenerator(input);

        byte[] key = keyGenerator.generateKey();
        for (int i = 0; i < key.length; i++) {
            int onesCount = Integer.bitCount(key[i] & 0xFF); // liczymy jedynki w bajcie
            Assertions.assertEquals(1, onesCount % 2, "Byte " + i + " does not have odd parity");
        }

    }
}