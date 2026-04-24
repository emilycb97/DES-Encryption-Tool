package org.example;

public class Converter {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] removePadding(byte[] input) {
        int lastIndex = input.length - 1;

        // Znajdujemy pierwsze niezerowe bajty od końca
        while (lastIndex >= 0 && input[lastIndex] == 0) {
            lastIndex--;
        }

        // Tworzymy nową tablicę, która kończy się na ostatnim niezerowym bajcie
        byte[] result = new byte[lastIndex + 1];
        System.arraycopy(input, 0, result, 0, lastIndex + 1);

        return result;
    }



}
