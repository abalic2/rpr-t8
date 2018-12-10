package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class Controller {
    public TextField tekst;
    public Button dugme;

    public void initialize() {
    }


    public static void displayDirectoryContents(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    displayDirectoryContents(file);
                } /*else {
                        System.out.println("     file:" + file.getCanonicalPath());
                    }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Trazi(ActionEvent actionEvent) {
        File dat = new File(System.getProperty("user.home"));
        displayDirectoryContents(dat);
    }
}
