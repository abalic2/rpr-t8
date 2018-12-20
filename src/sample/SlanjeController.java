package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class SlanjeController {
    public TextField ime;
    public TextField prezime;
    public ComboBox grad;
    public TextField adresa;
    public TextField postanskiBroj;

    private boolean validnoImePrezime(String s) {
        if (s.length() == 0 || s.length() > 20) return false;
        if (!Character.isUpperCase(s.charAt(0))) return false;
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isLowerCase(s.charAt(i))) return false;
        }
        return true;
    }

    private static boolean validnoImeGrada(String s) {
        if (s.length() == 0) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) return false;
        }
        return true;
    }

    private boolean validnaAdresa(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) return false;
        }
        return true;
    }

    @FXML
    public void initialize() {
        grad.setItems(FXCollections.observableArrayList("Sarajevo", "Mostar", "Banja Luka", "Bihać", "Trebinje","Travnik"));
        ime.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> {
                        if (validnoImePrezime(ime.getText())) {
                            ime.getStyleClass().removeAll("poljeNijeIspravno");
                            ime.getStyleClass().add("poljeIspravno");
                        } else {
                            ime.getStyleClass().removeAll("poljeIspravno");
                            ime.getStyleClass().add("poljeNijeIspravno");
                        }
                    }).start();
                }
            }
        });
        prezime.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> {
                        if (validnoImePrezime(prezime.getText())) {
                            prezime.getStyleClass().removeAll("poljeNijeIspravno");
                            prezime.getStyleClass().add("poljeIspravno");
                        } else {
                            prezime.getStyleClass().removeAll("poljeIspravno");
                            prezime.getStyleClass().add("poljeNijeIspravno");
                        }
                    }).start();
                }
            }
        });
        adresa.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> {
                        if (validnaAdresa(adresa.getText())) {
                            adresa.getStyleClass().removeAll("poljeNijeIspravno");
                            adresa.getStyleClass().add("poljeIspravno");
                        } else {
                            adresa.getStyleClass().removeAll("poljeIspravno");
                            adresa.getStyleClass().add("poljeNijeIspravno");
                        }
                    }).start();
                }
            }
        });

        grad.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> {
                        if (validnoImeGrada((String) grad.getValue())) {
                            grad.getStyleClass().removeAll("poljeNijeIspravno");
                            grad.getStyleClass().add("poljeIspravno");
                        } else {
                            grad.getStyleClass().removeAll("poljeIspravno");
                            grad.getStyleClass().add("poljeNijeIspravno");
                        }
                    }).start();
                }
            }
        });

        postanskiBroj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> {
                        if (validanPostanskiBroj(postanskiBroj.getText())) {
                            postanskiBroj.getStyleClass().removeAll("poljeNijeIspravno");
                            postanskiBroj.getStyleClass().add("poljeIspravno");
                        } else {
                            postanskiBroj.getStyleClass().removeAll("poljeIspravno");
                            postanskiBroj.getStyleClass().add("poljeNijeIspravno");
                        }
                    }).start();
                }
            }
        });

    }

    private boolean validanPostanskiBroj(String value) {
        String s = new String("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + value);
        try {
            URL url = new URL(s);
            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String linija = new String();
            linija = ulaz.readLine();
            if (linija.equals("OK")) return true;
        } catch (MalformedURLException e) {
            System.out.println("String " + adresa + postanskiBroj.getText() + "ne predstavlja validan URL!");
        } catch (IOException e) {
            System.out.println("Greška pri kreiranju ulaznog toka!");
        }
        return false;
    }

    boolean jeLiSveValidno() {
        if(ime.getText().length() == 0 || prezime.getText().length() == 0 ) return false;
        ArrayList<ObservableList<String>> validnost = new ArrayList<>();
        validnost.add(ime.getStyleClass()); validnost.add(prezime.getStyleClass());
        validnost.add(adresa.getStyleClass()); validnost.add(grad.getStyleClass());
        validnost.add(postanskiBroj.getStyleClass());
        for(ObservableList<String> o : validnost){
            if(o.contains("poljeNijeIspravno"))
                return false;
        }
        return true;
    }
}
