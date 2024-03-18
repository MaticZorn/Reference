package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ComboBox combo;
    public Spinner spinner;
    public ToggleGroup izbrana_akcija;
    public RadioButton odstrani1RB;
    public RadioButton odstranixRB;
    public RadioButton dodaj;
    public Label status;
    public Label sporocila;
    public TextField textf;
    public CheckBox nedvojnik;

    public String message = "Status: ";

    //DATOTEKA
    public void odpriCB(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberite datoteko");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            status.setText("Odprli ste datoteko: " + f.getAbsolutePath());
        }
    }

    public void pobrisiCB(ActionEvent actionEvent) {
        message = "Status: ";
        status.setText(message);
    }

    public void izhodCB(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void avtorCB(ActionEvent actionEvent) {
        status.setText("Avtor: User");
    }


    //KRAJI
    public void izvediB(ActionEvent actionEvent) {
        if (odstrani1RB.isSelected()) {
            if (combo.getItems().isEmpty()) { sporocila.setText("Seznam je prazen"); }
            else {
                combo.getItems().remove(0);
                sporocila.setText("Odstranjujem prvega");
            }
        }
        if (odstranixRB.isSelected()) {
            if (combo.getItems().isEmpty() || combo.getSelectionModel().isEmpty()) { sporocila.setText("Seznam je prazen"); }
            else {
                combo.getItems().remove(combo.getSelectionModel().getSelectedIndex());
                sporocila.setText("Odstranjujem izbranega");
            }
        }

        if (dodaj.isSelected()) {
            if (textf.getText().isEmpty()) sporocila.setText("Polje za vnos teksta je prazno");
            else {
                if (nedvojnik.isSelected() && combo.getItems().contains(textf.getText())) {
                    sporocila.setText("Kraj je že v seznamu");
                }
                else {
                    combo.getItems().add(textf.getText());
                    sporocila.setText("Dodajam");
                }
            }
        }
    }


    //CRKE
    public void izbire1m(ActionEvent actionEvent) {
        status.setText(message += 'M');
    }

    public void izbire1a(ActionEvent actionEvent) {
        status.setText(message += 'A');
    }

    public void izbire1t(ActionEvent actionEvent) {
        status.setText(message += 'T');
    }

    public void izbire1i(ActionEvent actionEvent) {
        status.setText(message += 'I');
    }

    public void izbire1c(ActionEvent actionEvent) {
        status.setText(message += 'C');
    }

    public void izbire2z(ActionEvent actionEvent) {
        status.setText(message += 'Z');
    }

    public void izbire2o(ActionEvent actionEvent) {
        status.setText(message += 'O');
    }

    public void izbire2r(ActionEvent actionEvent) {
        status.setText(message += 'R');
    }

    public void izbire2n(ActionEvent actionEvent) {
        status.setText(message += 'N');
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));
        spinner.valueProperty().addListener((obser, oldV,  newV) ->{
            int novElement = Integer.parseInt(newV.toString());

            if (novElement >= combo.getItems().size()) {
                sporocila.setText("Ni elementa");
            }
            else sporocila.setText(combo.getItems().get(novElement).toString());
        });
    }
}
