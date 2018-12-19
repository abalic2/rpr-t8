package sample;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

        lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("slanje.fxml"));
                        Stage myStage = new Stage();
                        myStage.setTitle("Unos podataka");
                        myStage.resizableProperty().setValue(false);
                        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                        myStage.show();
                    } catch (IOException ignore) {
                        return;
                    }
                }
            }
        });
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
