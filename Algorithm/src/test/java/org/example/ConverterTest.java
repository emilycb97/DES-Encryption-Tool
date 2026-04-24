package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void bytesToHex() {
        //String originalText = "HelloWor";
        //48 65 6C 6C 6F 57 6F 72
        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114};
        String hex = "48656C6C6F576F72";

        Assertions.assertEquals(hex, Converter.bytesToHex(message));
    }

    @Test
    void hexToBytes() {

        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114};
        String hex = "48656C6C6F576F72";

        Assertions.assertArrayEquals(message, Converter.hexToBytes(hex));
    }

    @Test
    void bytesToString() {
        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114};
        String originalText = "HelloWor";
        //48 65 6C 6C 6F 57 6F 72

        Assertions.assertEquals(originalText, new String(message, StandardCharsets.UTF_8));
    }

    @Test
    void StringToBytes() {
        byte[] message = {72, 101, 108, 108, 111, 87, 111, 114};
        String originalText = "HelloWor";
        Assertions.assertArrayEquals(message, originalText.getBytes(StandardCharsets.UTF_8));
    }
}