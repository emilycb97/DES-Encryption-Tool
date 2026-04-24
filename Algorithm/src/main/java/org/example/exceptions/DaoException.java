package org.example.exceptions;

/**
 * Klasa wyjątku dla błędu dostępu do danych (DAO)
 */
public class DaoException extends Exception {

    /**
     * Stała reprezentująca błąd odczytu do pliku
     */
    public static final String READ_ERROR = "read.error";

    /**
     * Stała reprezetująca błąd zapisu do pliku
     */
    public static final String WRITE_ERROR = "write.error";

    /**
     * Stała reprezentująca brak klasy w systemie
     */
    public static final String NO_CLASS_ERROR = "no.class.error";

    /**
     * Stała reprezentująca brak zasobu
     */
    public static final String NO_RESOURCE_ERROR = "no.resource.error";

    public static final String NO_FILE_ERROR = "no.file.error";

    /**
     * Konstruktor wyjątku z komunkatem błedu
     *
     * @param message Treść komunikatu błedu
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Konstruktor wyjątku z komunikatem błedu i przyczyną
     *
     * @param message Treść komunikatu błedu
     * @param cause Przyczyna błedu
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
