package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;

public class Controller {
    public TextField tekst;
    public Button dugme;
    public ListView lista;
    public Button dugmePrekini;
    private ObservableList<String> l;
    private String korijen = System.getProperty("user.home");

    public Controller() {
        l = FXCollections.observableArrayList();
    }

    public void initialize() {
        lista.setItems(l);
        dugme.setDisable(false);
        dugmePrekini.setDisable(true);
    }


    public void pretrazi(String put) {
        if (!dugme.isDisabled())
            return;
        File file = new File(put);
        if (file.isDirectory()) {
            if (file.listFiles() == null) return;
            for (File f : file.listFiles()) pretrazi(f.getAbsolutePath());
        } else if (file.isFile()) {
            if (file.getName().contains(tekst.getText()))
                Platform.runLater(() -> {
                    l.add(file.getAbsolutePath());
                });
        }
        if (file.getAbsolutePath().equals(korijen)) {
            dugme.setDisable(false);
            dugmePrekini.setDisable(true);
        }
    }

    public void Trazi(ActionEvent actionEvent) {
        l.clear();
        dugme.setDisable(true);
        dugmePrekini.setDisable(false);
        Thread thread = new Thread(() -> {
            pretrazi(korijen);
        });
        thread.start();
    }

    public void Prekini(ActionEvent actionEvent) {
        dugme.setDisable(false);
        dugmePrekini.setDisable(true);
    }
}
