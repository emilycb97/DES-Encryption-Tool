package org.example.exceptions;

/**
 * Klasa wyjątku dla błędów związanych z algorytmem
 */
public class AlgorithmException extends Exception {

    /**
     * Stała reprezentująca błąd związany z wartością null
     */
    public static final String NULL_VALUE_ERROR = "null.value.error";

    /**
     * Konstruktor wyjątku z komunikatem błędu
     *
     * @param message Treść komunikatu błędu
     */
    public AlgorithmException(String message) {
        super(message);
    }

    /**
     * Konstruktor wyjątku z komunikatem błędu i przyczyną
     *
     * @param message Treść komuniatu błędu
     * @param cause Przyczyna błędu
     */
    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
}
