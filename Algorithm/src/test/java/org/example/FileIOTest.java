package org.example;

import org.example.exceptions.AlgorithmException;
import org.example.exceptions.DaoException;
import org.example.exceptions.KeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    @Test
    void readTextFromFile() throws DaoException, KeyException, AlgorithmException {

        byte[] temp = FileIO.readTextFromFile("IS_sem 4.pdf");

        FileIO.saveToFile("copy.pdf", temp);

    }

    @Test
    void saveToFile() throws DaoException {

        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};

        FileIO.saveToFile("test.txt", input);
        Assertions.assertEquals(Arrays.toString(input), Arrays.toString(FileIO.readTextFromFile("test.txt")));

        File file = new File("test.txt");
        boolean deleted = file.delete();
        Assertions.assertTrue(deleted);
    }

    @Test
    void readTextFromFile2() throws DaoException, KeyException, AlgorithmException {

        byte[] temp = FileIO.readTextFromFile("IS_sem 4.pdf");

        byte[] input = {1, 35, 69, 103, -119, -85, -51, -17};
        KeyGenerator keyGenerator = new KeyGenerator(input);
        Algorithm algorithm = new Algorithm(temp, keyGenerator, false);

        FileIO.saveToFile("copy.pdf", algorithm.getText());

        algorithm.encrypt();
        algorithm.decrypt();

        FileIO.saveToFile("copyAfterDecrypt.pdf", algorithm.getText());

    }
}