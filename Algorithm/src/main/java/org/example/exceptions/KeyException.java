package org.example.exceptions;

/**
 * Klasa wyjątku dla błędów związanych z kluczem.
 */
public class KeyException extends Exception {

    /**
     * Stała reprezentująca bład związany z nieprawidłową wartością klucza
     */
    public static final String KEY_VALUE_ERROR = "key.value.error";

    /**
     * Konstruktor wyjątku z komunikatem błedu
     *
     * @param message Treść komunikatu błędu
     */
    public KeyException(String message) {
        super(message);
    }

    /**
     * Konstruktor wyjątku z komunkatem błędu i przyczyną
     *
     * @param message Treść komunikatu błędu
     * @param cause Przyczyna błędu
     */
    public KeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
