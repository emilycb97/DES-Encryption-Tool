package org.example;

public class BitOperations {

    /**
     * Przeprowadza permutację bitów na podstawie podanej tablicy permutacji
     *
     * @param input            Wartość wejściowa do permutacji
     * @param permutationTable Tablica permutacji
     * @return Wynik permutacji jako BigInteger
     */
    byte[] permute(byte[] input, int[] permutationTable) {
        int outputLength = (permutationTable.length + 7) / 8;
        byte[] output = new byte[outputLength];
        for (int i = 0; i < permutationTable.length; i++) {
            int bitValue = getBit(input, permutationTable[i] - 1); // -1 bo tablica jest 1-indeksowana
            setBit(output, i, bitValue);
        }
        return output;
    }

    int getBit(byte[] data, int bitIndex) {
        int byteIndex = bitIndex / 8;
        int bitInByte = 7 - (bitIndex % 8); // MSB is bit 7
        return (data[byteIndex] >> bitInByte) & 1;
    }

    void setBit(byte[] data, int bitIndex, int bitValue) {
        int byteIndex = bitIndex / 8;
        int bitInByte = 7 - (bitIndex % 8);

        if (bitValue == 1) {
            data[byteIndex] |= (byte) (1 << bitInByte);
        } else {
            data[byteIndex] &= (byte) ~(1 << bitInByte);
        }
    }

    byte[] getBitsAsByteArray(byte[] source, int startBit, int length) {
        int byteLength = (length + 7) / 8; // ile bajtów potrzeba, by zapisać length bitów
        byte[] result = new byte[byteLength];

        for (int i = 0; i < length; i++) {
            int bit = getBit(source, startBit + i);
            setBit(result, i, bit);
        }

        return result;
    }
}
