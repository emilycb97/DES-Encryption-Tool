package org.example;

import org.example.exceptions.DaoException;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {

    public static byte[] readTextFromFile(String fileName) throws DaoException {
        try {
            return Files.readAllBytes(Path.of(fileName));
        } catch (IOException e) {
            throw new DaoException(DaoException.READ_ERROR, e);
        }
    }

    public static void saveToFile(String fileName, byte[] text) throws DaoException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bos.write(text);
        } catch (IOException e) {
            throw new DaoException(DaoException.WRITE_ERROR, e);
        }
    }

}
