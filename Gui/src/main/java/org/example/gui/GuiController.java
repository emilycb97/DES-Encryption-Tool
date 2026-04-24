package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Algorithm;
import org.example.Converter;
import org.example.FileIO;
import org.example.KeyGenerator;
import org.example.exceptions.AlgorithmException;
import org.example.exceptions.DaoException;
import org.example.exceptions.KeyException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GuiController {
    @FXML
    private TextArea decryptedText;  // Pole tekstowe do wyświetlania odszyfrowanego tekstu
    @FXML
    private TextArea encryptedText;  // Pole tekstowe do wyświetlania zaszyfrowanego tekstu
    @FXML
    private Button encryptButton;  // Przycisk do szyfrowania tekstu
    @FXML
    private Button decryptButton;  // Przycisk do odszyfrowania tekstu
    @FXML
    private Button writeDecryptButton;  // Przycisk do zapisywania odszyfrowanego tekstu
    @FXML
    private Button writeEncryptButton;  // Przycisk do zapisywania zaszyfrowanego tekstu
    @FXML
    private TextField keyTextField;  // Pole tekstowe do wprowadzania klucza
    @FXML
    private Button writeKeyButton;  // Przycisk do zapisywania klucza
    @FXML
    private Button readDecryptButton;  // Przycisk do odczytywania odszyfrowanego tekstu z pliku
    @FXML
    private Button readEncryptButton;  // Przycisk do odczytywania zaszyfrowanego tekstu z pliku
    @FXML
    private Button readKeyButton;  // Przycisk do odczytywania klucza z pliku
    @FXML
    private RadioButton radioButtonFile;  // RadioButton do wyboru trybu pliku
    @FXML
    public RadioButton radioButtonField;

    private KeyGenerator keyGenerator;  // Generator klucza
    private byte[] readPlainTextFromFile;
    private byte[] readEncryptedFromFile;

    /**
     * Ustawienie wartości klucza z pola tekstowego
     * Sprawdza, czy wprowadzony klucz jest w formacie szesnastkowym
     */
    @FXML
    public void submitKeyValue() {
        if (!keyTextField.getText().isEmpty()) {
            if (keyTextField.getText().matches("^[0-9A-Fa-f]+$")) {
                try {
                    byte [] keyBytes = Converter.hexToBytes(keyTextField.getText());
                    keyGenerator = new KeyGenerator(keyBytes);
                } catch (KeyException e) {
                    errorPopUp("Niepoprawna długość klucza");
                }
            } else {
                errorPopUp("Niepoprawny klucz, klucz powinien być zapisany w systemie szesnastkowym");
            }
        } else {
            errorPopUp("Klucz nie może być pusty");
        }
    }


    /**
     * Generuje nowy klucz i ustawia go w polu tekstowym
     */
    @FXML
    public void setGenerateButton() {
        try {
            keyGenerator = new KeyGenerator(null);
            keyTextField.setText(Converter.bytesToHex(keyGenerator.getKeyValue()));

        } catch (KeyException e) {
            errorPopUp("Niepoprawna wartość");
        }
    }

    /**
     * Funkcja do szyfrowania lub odszyfrowania tekstu w zależności od wybranego przycisku
     * Obsługuje również tryb pracy z plikami
     */
    @FXML
    public void submit(ActionEvent event) {
        try {
            validateKey();

            if (event.getSource() == encryptButton) {
                handleEncryption();
            } else {
                handleDecryption();
            }
        } catch (KeyException | AlgorithmException e) {
            errorPopUp("Operacja nieudana: " + e.getMessage());
        }
    }

    private void validateKey() throws KeyException {
        if (keyGenerator == null || keyGenerator.getKeyValue() == null) {
            throw new KeyException("Klucz nie został ustawiony");
        }
    }

    private void handleEncryption() throws AlgorithmException, KeyException {
        byte[] inputData = getInputDataForEncryption();
        Algorithm algorithm = new Algorithm(inputData, keyGenerator, false);
        algorithm.encrypt();

        encryptedText.setText(Converter.bytesToHex(algorithm.getText()));
        readEncryptedFromFile = algorithm.getText();
    }

    private byte[] getInputDataForEncryption() throws AlgorithmException {
        if (radioButtonFile.isSelected()) {
            if (readPlainTextFromFile == null) {
                throw new AlgorithmException("Najpierw wczytaj plik do szyfrowania");
            }
            return readPlainTextFromFile;
        }
        return decryptedText.getText().getBytes(StandardCharsets.UTF_8);
    }

    private void handleDecryption() throws AlgorithmException, KeyException {
        byte[] inputData = getInputDataForDecryption();
        Algorithm algorithm = new Algorithm(inputData, keyGenerator, true);
        algorithm.decrypt();

        decryptedText.setText(new String(algorithm.getText(), StandardCharsets.UTF_8));
        readPlainTextFromFile = algorithm.getText();
    }

    private byte[] getInputDataForDecryption() throws AlgorithmException {
        if (radioButtonFile.isSelected()) {
            if (readEncryptedFromFile == null) {
                throw new AlgorithmException("Najpierw wczytaj plik do deszyfrowania");
            }
            return readEncryptedFromFile;
        }

        String encrypted = encryptedText.getText();
        if (!encrypted.matches("^[0-9A-Fa-f]+$")) {
            throw new AlgorithmException("Niepoprawny format szyfrogramu");
        }
        return Converter.hexToBytes(encrypted);
    }

    /**
     * Funkcja do zapisywania tekstu do pliku
     * Obsługuje różne przypadki zapisu (odszyfrowany tekst, zaszyfrowany tekst, klucz)
     */
    @FXML
    public void save(ActionEvent event) {
        try {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showSaveDialog(stage.getScene().getWindow());
        if (selectedFile != null) {
                if (event.getSource() == writeDecryptButton) {
                   byte[] data = radioButtonFile.isSelected() ?
                           readPlainTextFromFile : decryptedText.getText().getBytes();
                   FileIO.saveToFile(selectedFile.getAbsolutePath(), data);
                } else if (event.getSource() == writeEncryptButton) {
                  byte [] data = radioButtonFile.isSelected() ?
                          readEncryptedFromFile : Converter.hexToBytes(encryptedText.getText());
                  FileIO.saveToFile(selectedFile.getAbsolutePath(), data);
                } else if (event.getSource() == writeKeyButton) {
                    if (!keyTextField.getText().isEmpty() || keyGenerator != null) {
                        FileIO.saveToFile(selectedFile.getAbsolutePath(), keyGenerator.getKeyValue());
                    } else {
                        errorPopUp("Wygeneruj najpierw klucz");
                    }
                }

        } else {
            infoPopUp("Anulowano operację");
        }
        } catch (DaoException e) {
        errorPopUp("Błąd zapisu do pliku");
    }
    }

    /**
     * Funkcja do odczytywania danych z pliku
     * Obsługuje różne przypadki odczytu (klucz, zaszyfrowany tekst, odszyfrowany tekst)
     */
    @FXML
    public void read(ActionEvent event) {


        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFile = fileChooser.showOpenDialog(stage.getScene().getWindow());
        if (selectedFile != null) {

            if (event.getSource() == readKeyButton) {
                try {
                    byte[] temp = FileIO.readTextFromFile(selectedFile.getAbsolutePath());
                    keyGenerator = new KeyGenerator(Converter.hexToBytes(new String(temp, StandardCharsets.UTF_8)));
                    keyTextField.setText(Converter.bytesToHex(keyGenerator.getKeyValue()));
                } catch (DaoException e) {
                    errorPopUp("Niepoprawny format klucza");
                } catch (KeyException e) {
                    errorPopUp("Błąd klucza");
                }
            } else {
                if (event.getSource() == readDecryptButton || event.getSource() == encryptButton) {
                    try {
                        readPlainTextFromFile = FileIO.readTextFromFile(selectedFile.getAbsolutePath());
                        decryptedText.setText(new String(readPlainTextFromFile));
                    } catch (DaoException e) {
                        if (Objects.equals(e.getMessage(), "key.value.error")) {
                            errorPopUp("Wygeneruj najpierw klucz");
                        } else {
                            errorPopUp("Błąd odczytu");
                        }
                    }
                } else if (event.getSource() == readEncryptButton || event.getSource() == decryptButton) {
                    try {
                        readEncryptedFromFile = FileIO.readTextFromFile(selectedFile.getAbsolutePath());
                        encryptedText.setText(Converter.bytesToHex(readEncryptedFromFile));
                    } catch (DaoException e) {
                        if (Objects.equals(e.getMessage(), "key.value.error")) {
                            errorPopUp("Wygeneruj najpierw klucz");
                        } else {
                            errorPopUp("Błąd odczytu");
                        }
                    }
                }
            }
        } else {
            infoPopUp("Nie wybrano pliku");
        }
    }

    /**
     * Wyświetla okno błędu z odpowiednim komunikatem
     *
     * @param e - komunikat błędu
     */
    public void errorPopUp(String e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Błąd");
        alert.setContentText(e);
        alert.showAndWait();
    }

    /**
     * Wyświetla okno z informacją (np. o anulowaniu akcji)
     *
     * @param e - komunikat informacyjny
     */
    public void infoPopUp(String e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Info");
        alert.setContentText(e);
        alert.showAndWait();
    }

    @FXML
    public void handleRadioButtonClick() {
        boolean isFileMode = radioButtonFile.isSelected();
        readEncryptButton.setVisible(isFileMode);
        readEncryptButton.setManaged(isFileMode);
        readDecryptButton.setVisible(isFileMode);
        readDecryptButton.setManaged(isFileMode);
    }

}