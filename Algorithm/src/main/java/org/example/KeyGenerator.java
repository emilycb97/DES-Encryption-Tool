package org.example;

import org.example.exceptions.KeyException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za generowanie kluczy dla argorytmu DES
 * Obsługuje generowanie klucza, permutacje oraz zapisywanie i odczytywanie pliku
 */
public class KeyGenerator extends BitOperations{

    /**
     * Tablica PC1 - permutacja redukująca klucz z 64 do 56 bitów
     */
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

    /**
     * Tablica PC2 - redukcja klucza z 56 do 48 bitów
     */
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

    /**
     * Liczba przesunięć bitowych dla każdej rundy
     */
    private static final int[] shift = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    /**
     * Lista 16 kluczy rundowych
     */
    private final List<byte[]> keyValues;
    /**
     * Główny klucz DES
     */
    private final byte[] keyValue;

    public byte[] getKeyValue() {
        return keyValue;
    }

    /**
     * Konstruktor klasy KeyGenerator
     * Jeśli klucz jest pusty, generuje nowy losowy klucz
     * Jesli klucz ma niepoprawną długość, zgłasza wyjątek
     *
     * @param keyValue Klucz w postaci szesnastkowej (16 znaków)
     * @throws KeyException Jeśli długość klucza jest niepoprawna
     */
    public KeyGenerator(byte[] keyValue) throws KeyException {
        if (keyValue == null) {
            keyValue = generateKey(); //Generowanie losowego klucza
        } else if (keyValue.length != 8) {
            throw new KeyException(KeyException.KEY_VALUE_ERROR);
        }

        this.keyValue = keyValue;
        this.keyValues = new ArrayList<>();

        //Kompresja klucza za pomocą PC1

        byte[] keyValue56 = permute(keyValue, PC1);

        //Podział na dwie części po 28 bitów
        byte[] keyValue28C = getBitsAsByteArray(keyValue56, 0, 28);
        byte[] keyValue28D = getBitsAsByteArray(keyValue56, 28, 28);

        for (int i = 0; i < 16; i++) {

            if (shift[i] == 1) {
                keyValue28C = shiftBitsByOneHalfKey(keyValue28C);
                keyValue28D = shiftBitsByOneHalfKey(keyValue28D);
            } else {
                keyValue28C = shiftBitsByTwoHalfKey(keyValue28C);
                keyValue28D = shiftBitsByTwoHalfKey(keyValue28D);
            }

            byte[] keyValue28DShifted = shiftDkey(keyValue28D);


            byte[] combined = combine(keyValue28C, keyValue28DShifted);


            keyValues.add(permute(combined, PC2));

        }

    }

    static byte[] combine(byte[] keyValue28C, byte[] keyValue28D) {
        byte[] combined = new byte[7];
        System.arraycopy(keyValue28C, 0, combined, 0, 3);
        System.arraycopy(keyValue28D, 1, combined, 4, 3);
        combined[3] = (byte) ((keyValue28C[3] & 0xF0) | (keyValue28D[0] & 0x0F));
        return combined;
    }

    byte[] shiftDkey(byte[] keyValue28D) {
        byte[] temp = new byte[4];
        int carry = 0;
        for (int j = 0; j < keyValue28D.length; j++) {
            int current = keyValue28D[j] & 0xFF;
            temp[j] = (byte) ((current >> 4) | carry);
            carry = (current & 0x0F) << 4;
        }

        keyValue28D = temp;
        return keyValue28D;
    }

    public byte[] getKeyValues(int number) {
        return keyValues.get(number);
    }

    byte[] shiftBitsByOneHalfKey(byte[] halfKey) {
        halfKey = shiftBitsByOne(halfKey);
        int carry = halfKey[3] & 0xF0;
        halfKey[3] = (byte) ((halfKey[3] << 4) | carry);
        return halfKey;
    }

    byte[] shiftBitsByTwoHalfKey(byte[] halfKey) {
        halfKey = shiftBitsByTwo(halfKey);
        int carry = halfKey[3] & 0xF0;
        halfKey[3] = (byte) ((halfKey[3] << 4) | carry);
        return halfKey;
    }

    byte[] shiftBitsByTwo(byte[] source) {
        byte[] result = new byte[source.length];
        int carry = (source[0] & 0xC0) >> 6;
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = (byte) ((source[i] << 2) | carry);

            carry = (source[i] & 0xC0) >> 6;
        }
        return result;
    }

    byte[] shiftBitsByOne(byte[] source) {
        byte[] result = new byte[source.length];
        int carry = (source[0] & 0x80) >> 7;
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = (byte) ((source[i] << 1) | carry);

            carry = (source[i] & 0x80) >> 7;
        }

        return result;
    }

    /**
     * Generuje losowy 64-bitowy klucz
     *
     * @return Wygenerowany klucz jako BigInteger
     */
    byte[] generateKey() {
        byte[] key = new byte[8];
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < key.length; i++) {
            int data = random.nextInt(128); // 7-bitowe dane (0-127)

            // Obliczamy bit parzystości - ustawiamy go tak, żeby cała liczba miała nieparzystą liczbę jedynek
            int parity = (Integer.bitCount(data) % 2 == 0) ? 1 : 0;

            key[i] = (byte) ((data << 1) | parity); // dodajemy bit parzystości jako najmłodszy bit
        }

        return key;
    }

}

