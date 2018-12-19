package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class Controller {
    public TextField tekst;
    public Button dugme;
    public ListView lista;
    private ObservableList<String> l;

    public Controller() {
        l = FXCollections.observableArrayList();
    }

    public void initialize() {
        lista.setItems(l);
    }


    public void pretrazi(String put) {
        File file = new File(put);
        if (file.isDirectory()) {
            if (file.listFiles() == null) return;
            for (File f : file.listFiles()) pretrazi(f.getAbsolutePath());
        } else if (file.isFile()) {
            if (file.getName().contains(tekst.getText()))
                l.add(file.getAbsolutePath());
        }
    }

    public void Trazi(ActionEvent actionEvent) {
        pretrazi(System.getProperty("user.home"));
    }
}
