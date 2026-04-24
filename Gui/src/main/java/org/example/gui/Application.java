package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    /**
     * Metoda uruchamiająca aplikację i konfigurująca główne okno
     *
     * @param stage Główne okno aplikacji (Stage)
     * @throws IOException Jeśli wystąpi błąd przy ładowaniu pliku FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gui-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/gui/icon.png")));
        stage.getIcons().add(icon);
        stage.setTitle("DES Encryption GUI");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Główna metoda uruchamiająca aplikację
     *
     * @param args Argumenty wiersza poleceń (nieużywane w tej aplikacji)
     */
    public static void main(String[] args) {
        launch();
    }
}