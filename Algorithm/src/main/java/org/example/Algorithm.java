package org.example;

import org.example.exceptions.AlgorithmException;
import org.example.exceptions.KeyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa implementująca algorytm szyfrowania i deszyfrowania
 * Obsługuje operacje szyfrowania, deszyfrowania, permutacji oraz operacje na plikach
 */
public class Algorithm extends BitOperations {
    private static final int[] P_BOX = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };
    /**
     * Obiekt generatora klucza używanego do szyfrowania i deszyfrowania
     */
    private KeyGenerator keyGenerator;

    /**
     * Tekst do przetworzenia (zaszyfrowania lub odszyfrowania)
     */
    private byte[] text;

    /**
     * Flaga informująca, czy tekst jest już zaszyfrowany
     */
    private boolean isEncrypted;

    public boolean isEncrypted() {
        return isEncrypted;
    }

    /**
     * Konstruktor klasy Algorithm
     *
     * @param text         Tekst do zaszyfrowania lub odszyfrowania
     * @param keyGenerator Generator klucza
     * @param isEncrypted  Flaga informująca, czy tekst jest zaszyfrowany
     * @throws KeyException       Jeśli klucz jest niepoprawny
     * @throws AlgorithmException Jeśli tekst jest pusty
     */
    public Algorithm(byte[] text, KeyGenerator keyGenerator, boolean isEncrypted) throws KeyException, AlgorithmException {
        if (keyGenerator == null) {
            throw new KeyException(KeyException.KEY_VALUE_ERROR);
        } else if (text == null) {
            throw new AlgorithmException(AlgorithmException.NULL_VALUE_ERROR);
        }
        this.text = text;
        this.keyGenerator = keyGenerator;
        this.isEncrypted = isEncrypted;
    }

    // można to przenieść do bitoperations
    private static byte[] xorByteArrays(byte[] a, byte[] b) throws AlgorithmException {
        if (a.length != b.length) {
            throw new AlgorithmException(AlgorithmException.NULL_VALUE_ERROR);
        }
        byte[] xored = new byte[b.length];

        for (int i = 0; i < b.length; i++) {
            xored[i] = (byte) (b[i] ^ a[i]);
        }
        return xored;
    }

    /**
     * Szyfruje tekst
     *
     * @return Zaszyfrowany tekst w formacie szesnastkowym
     */
    public byte[] encrypt() throws AlgorithmException {
        if (isEncrypted) {
            return text;
        }

        this.text = encodeByteArray(text);
        this.isEncrypted = true;
        return text;
    }

    byte[] encodeByteArray(byte[] input) throws AlgorithmException {
        int length = input.length % 8;
        if (length == 0) {
            length = input.length;
        }else {
            length = input.length + (8 - length);
        }

        byte[] output = new byte[length];
        List<byte[]> blocks = getBlocks(input);
        List<byte[]> encryptedBlocks = new ArrayList<>();

        for (byte[] block64bit : blocks) {

            block64bit = initialPermutation(block64bit);

            byte[] left = getBitsAsByteArray(block64bit, 0, 32);
            byte[] right = getBitsAsByteArray(block64bit, 32, 32);

            for (int i = 0; i < 16; i++) {
                byte[] temp = right;
                byte[] func = function(right, keyGenerator.getKeyValues(i));
                right = xorByteArrays(left, func);
                left = temp;
            }

            byte[] combined = new byte[8];

            System.arraycopy(right, 0, combined, 0, 4); // 4 bajty = 32 bity
            System.arraycopy(left, 0, combined, 4, 4);

            byte[] encryptedBlock = finalPermutation(combined);

            encryptedBlocks.add(encryptedBlock);
        }

        int pos = 0;
        for (byte[] part : encryptedBlocks) {
            System.arraycopy(part, 0, output, pos, part.length);
            pos += part.length;
        }

        return output;
    }

    /**
     * Deszyfruje tekst
     *
     * @return Odszyfrowany tekst jako String
     * @throws AlgorithmException Jeśli tekst nie jest w formacie szesnastkowym
     */
    public byte[] decrypt() throws AlgorithmException {
        if (!isEncrypted) {
            return text;
        }

        this.text = Converter.removePadding(decryptByteArray(text));
        this.isEncrypted = false;
        return text;
    }

    /**
     * Deszyfruje dane wejściowe (w postaci bajtów)
     *
     * @param input Bajty tekstu do deszyfrowania
     * @return Deszyfrowane bajty
     */
    byte[] decryptByteArray(byte[] input) throws AlgorithmException {
        byte[] output = new byte[input.length];
        List<byte[]> blocks = getBlocks(input);
        List<byte[]> decryptedBlocks = new ArrayList<>();

        for (byte[] block64bit : blocks) {

            block64bit = initialPermutation(block64bit);

            byte[] left = getBitsAsByteArray(block64bit, 0, 32);
            byte[] right = getBitsAsByteArray(block64bit, 32, 32);

            for (int i = 15; i >= 0; i--) {
                byte[] temp = right;
                byte[] func = function(right, keyGenerator.getKeyValues(i));
                right = xorByteArrays(left, func);
                left = temp;
            }

            byte[] combined = new byte[8];

            System.arraycopy(right, 0, combined, 0, 4); // 4 bajty = 32 bity
            System.arraycopy(left, 0, combined, 4, 4);

            byte[] decryptedBlock = finalPermutation(combined);

            decryptedBlocks.add(decryptedBlock);

        }

        int pos = 0;
        for (byte[] part : decryptedBlocks) {
            System.arraycopy(part, 0, output, pos, part.length);
            pos += part.length;
        }

        return output;
    }

    /**
     * Dzieli dane wejściowe na bloki o określonym rozmiarze
     *
     * @param input Bajty tekstu do podziału
     * @return Lista bloków (BigInteger) reprezentujących dane wejściowe
     */
    public List<byte[]> getBlocks(byte[] input) {
        int blockSize = 8;
        List<byte[]> blocks = new ArrayList<>();


        for (int i = 0; i < input.length; i += blockSize) {
            byte[] block = new byte[blockSize];
            int length = Math.min(blockSize, input.length - i);
            System.arraycopy(input, i, block, 0, length);
            blocks.add(block);
        }

        return blocks;
    }

    /**
     * Wykonuje początkową permutację na wejściu
     *
     * @param input Wejściowy blok do permutacji
     * @return Permutowany blok
     */
    byte[] initialPermutation(byte[] input) {
        int[] IP = {
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7
        };
        return permute(input, IP);
    }

    /**
     * Wykonuje końcową permutację na wejściu
     *
     * @param input Wejściowy blok do permutacji
     * @return Permutowany blok
     */
    byte[] finalPermutation(byte[] input) {
        int[] FP = {
                40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25
        };
        return permute(input, FP);
    }

    /**
     * Funkcja przetwarzania dla algorytmu DES (rozszerzenie, XOR, substytucja, permutacja)
     *
     * @param halfBlock Połowa bloku (prawa część)
     * @param subKey    Podklucz do zastosowania w tej rundzie
     * @return Przetworzona część bloku
     */
    byte[] function(byte[] halfBlock, byte[] subKey) throws AlgorithmException {
        byte[] expandedBlock = expand(halfBlock);
        byte[] xored = xorByteArrays(subKey, expandedBlock);
        byte[] substituted = substitute(xored);

        return permute(substituted, P_BOX);
    }

    /**
     * Rozszerza blok do 48 bitów (rozszerzenie DES)
     *
     * @param input Wejściowy blok o rozmiarze 32 bitów
     * @return Rozszerzony blok o rozmiarze 48 bitów
     */
    byte[] expand(byte[] input) {
        int[] EXPANSION_TABLE = {
                32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1
        };

        return permute(input, EXPANSION_TABLE);
    }


    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    /**
     * Substytucja bloków przy użyciu S-boxów
     *
     * @param input Wejściowy blok do substytucji
     * @return Zastąpiony blok
     */
    byte[] substitute(byte[] input) {
        //6*8 = 48 -> 4*8 => 32
        int inputBitLength = input.length * 8;
        int outputBitLength = (inputBitLength / 6) * 4;
        int outputByteLength = (outputBitLength + 7) / 8;
        byte[] output = new byte[outputByteLength];

        int inputBitPos = 0;
        int outputBitPos = 0;

        while (inputBitPos + 6 <= inputBitLength) {
            int index = 0;
            for (int i = 0; i < 6; i++) {
                index <<= 1;
                index |= getBit(input, inputBitPos + i);
            }

            int row = ((index & 0b100000) >> 4) | (index & 1);
            int col = (index >> 1) & 0xF;

            // numer SBoxa to numer bloku 6-bitowego
            int sBoxNumber = inputBitPos / 6 + 1;
            int sBoxValue = SBox.getByNumber(sBoxNumber).get(row, col); // 4 bity

            for (int i = 3; i >= 0; i--) {
                int bit = (sBoxValue >> i) & 1;
                setBit(output, outputBitPos, bit);
                outputBitPos++;
            }

            inputBitPos += 6;
        }

        return output;
    }

    /**
     * Zwraca aktualny tekst (zaszyfrowany lub odszyfrowany)
     *
     * @return Tekst jako String
     */
    public byte[] getText() {
        return text;
    }
}
