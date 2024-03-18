package zavarovalnica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class zavarovalnicaKontroler implements Initializable {

    public Label status;
    public ComboBox comboZnamka;
    public ComboBox comboVrstaVozila;
    public ComboBox comboGorivo;
    public Spinner spinnerSedezi;
    public TextField textfProstornina;
    public TextField textfOznaka;
    public TextField textfBarva;
    public Spinner spinnerMoc;
    public RadioButton rbAO;
    public RadioButton rbAOplus;
    public RadioButton rbKaskoPolno;
    public RadioButton rbKaskoOdbitna;
    public RadioButton rbKaskoBrez;
    public TextField textfIme;
    public TextField textfPriimek;
    public DatePicker datepRojstvo;
    public TextField textfUlica;
    public RadioButton rbMladiVoznik;
    public RadioButton rbIzkusenVoznik;
    public TextField textfHisna;
    public TextField textfPostna;
    public TextField textfKraj;
    public DatePicker datepRegistracija;
    public TextField textfRegisterska;
    public TextField textfKrajRegistracije;
    public CheckBox cbSteklo;
    public CheckBox cbParkirisce;
    public CheckBox cbPotniki;
    public CheckBox cbTretjaOseba;
    public CheckBox cbGum;
    public CheckBox cbKraja;
    public CheckBox cbToca;
    public ToggleGroup izbranoZavarovanje;
    public ToggleGroup izbranoKasko;
    public ToggleGroup izbranoIzkusnje;


    public void handleOdpri(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberite datoteko");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            status.setText("Odprli ste datoteko: " + f.getAbsolutePath());
        }
    }

    public void handleShrani(ActionEvent actionEvent) {
        /**
        if (!pravilnoIzpolnjen()) {
            status.setText("Status: Obrazec mora biti pravilno izpolnjen.");
            return;
        }
         **/
        FileChooser fc = new FileChooser();
        fc.setTitle("Mesto shranjevanja datoteke");
        File f = fc.showSaveDialog(null);
        if (f != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(comboZnamka.getValue().toString() + " " + comboVrstaVozila.getValue().toString() + "\n");
                bw.write(comboGorivo.getValue().toString() + " " + textfProstornina.getText() + "\n");
                bw.write(spinnerSedezi.getValue().toString() + " " + textfBarva.getText() + "\n");
                bw.write(spinnerMoc.getValue().toString() + " " + textfOznaka.getText() + "\n\n");      //
                bw.write(textfIme.getText() + " " + textfHisna.getText() + "\n");
                bw.write(textfPriimek.getText() + " " + textfUlica.getText() + "\n");
                bw.write(datepRojstvo.getValue() + " " + textfPostna.getText() + "\n");
                bw.write(rbMladiVoznik.isSelected() + " " + textfKraj.getText() + "\n\n");              //
                bw.write(datepRegistracija.getValue() + " " + textfRegisterska.getText() + "\n");
                bw.write(textfKrajRegistracije.getText() + "\n\n");                                     //
                bw.write(rbAO.isSelected() + "\n");
                bw.write(izbranoKasko.getSelectedToggle() + "\n");
                bw.write(cbSteklo.isSelected() + " " + cbParkirisce.isSelected() + " " + cbPotniki.isSelected()
                        + " " + cbTretjaOseba.isSelected() + " " + cbGum.isSelected() + " " + cbKraja.isSelected() + " " + cbToca.isSelected());
                bw.close();
                status.setText("Shranjevanje uspešno");
            } catch (Exception e) {
                status.setText("Shranjevanje neuspešno");
            }
        }
    }

    public void handleIzhod(ActionEvent actionEvent) { System.exit(0); }

    public void handleClearAll(ActionEvent actionEvent) {
        TextField[] vsaPoljaTF = new TextField[] {textfIme, textfOznaka, textfBarva, textfHisna, textfKraj, textfProstornina,
                textfKrajRegistracije, textfPostna, textfPriimek, textfRegisterska, textfUlica};
        CheckBox[] vsiCB = new CheckBox[] {cbToca, cbSteklo, cbKraja, cbGum, cbTretjaOseba, cbPotniki ,cbParkirisce};

        spinnerSedezi.getValueFactory().setValue(0);
        spinnerSedezi.getValueFactory().setValue(0);
        comboGorivo.getSelectionModel().selectFirst();
        comboZnamka.getSelectionModel().selectFirst();
        comboVrstaVozila.getSelectionModel().selectFirst();
        for (TextField tf : vsaPoljaTF) {
            tf.clear();
        }
        for (CheckBox cb : vsiCB) {
            cb.setSelected(false);
        }
        datepRegistracija.getEditor().clear();
        datepRojstvo.getEditor().clear();
        rbAO.setSelected(true);
        rbIzkusenVoznik.setSelected(true);
        rbKaskoBrez.setSelected(true);
    }

    public void handleCheck(ActionEvent actionEvent) {
        TextField[] vsaPoljaTF = new TextField[] {textfIme, textfOznaka, textfBarva, textfHisna, textfKraj, textfProstornina,
                textfKrajRegistracije, textfPostna, textfPriimek, textfRegisterska, textfUlica};

        if (spinnerSedezi.getValue().equals(0) || spinnerMoc.getValue().equals(0) || jePrazenTF(vsaPoljaTF)) {
            status.setText("Status: Neizpolnjena vsa polja.");
        }
        if (!jeStevilo(textfProstornina) || !jeStevilo(textfHisna) || !jeStevilo(textfPostna))
            status.setText("Status: V poljih za števila so drugi znaki.");

        else status.setText("Status: Polja so pravilno izpolnjena");
    }

    public boolean pravilnoIzpolnjen() {
        TextField[] vsaPoljaTF = new TextField[] {textfIme, textfOznaka, textfBarva, textfHisna, textfKraj, textfProstornina,
                textfKrajRegistracije, textfPostna, textfPriimek, textfRegisterska, textfUlica};
        return jePrazenTF(vsaPoljaTF) && jeStevilo(textfProstornina) && jeStevilo(textfHisna) && jeStevilo(textfPostna);
    }

    public boolean jePrazenTF(TextField[] vsiTF) {
        for (TextField tf : vsiTF) {
            System.out.println(tf.getText());
            if (tf.getText().isEmpty())
                return true;
        }
        return false;
    }

    public boolean jeStevilo(TextField vrednost) {
        try {
            Integer.parseInt(vrednost.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void handleBliznjice(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Bliznjice");
        alert.setHeaderText("Bliznjice gumbov - Shortcuts");
        alert.setContentText("Odpri -> ctrl + O        Shrani -> ctrl + S\n" +
                             "Izhod -> ctrl + X        Ponastavi -> ctrl + Del\n" +
                             "Preveri -> ctrl + G      Bliznjice -> B\nAvtor -> A");
        alert.showAndWait();
    }

    public void handleAvtor(ActionEvent actionEvent) {
        status.setText("Avtor: Matic Zorn");
    }

    public void handleDatumRoj(ActionEvent actionEvent) {
        /**
        System.out.println(datepRojstvo.getValue()); //2021-04-01
        String datRojstva = datepRojstvo.getValue().toString();
        if (datRojstva.charAt(0) < 1 || datRojstva.charAt(1) < 9 || datRojstva.charAt(6) > 1 || datRojstva.charAt(8) > 31)
            status.setText("Status: Vnesite mogoč datum rojstva.");
         **/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> znamke = new ArrayList<String>();
        znamke.add("BMW");
        znamke.add("Audi");
        znamke.add("Volkswagen");
        znamke.add("Fiat");

        List<String> gorivo = new ArrayList<String>();
        gorivo.add("Bencin");
        gorivo.add("Dizel");
        gorivo.add("Elektrika");
        gorivo.add("Drugo");

        List<String> vrstaVozila = new ArrayList<String>();
        vrstaVozila.add("Osebni avto");
        vrstaVozila.add("Motor");
        vrstaVozila.add("Avtobus");
        vrstaVozila.add("Tovornjak");
        vrstaVozila.add("Drugo");

        ObservableList obZnamka = FXCollections.observableList(znamke);
        comboZnamka.setItems(obZnamka);
        ObservableList obGorivo = FXCollections.observableList(gorivo);
        comboGorivo.setItems(obGorivo);
        ObservableList obVrsta = FXCollections.observableList(vrstaVozila);
        comboVrstaVozila.setItems(obVrsta);

        spinnerSedezi.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        spinnerMoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 150, 0));
        comboGorivo.getSelectionModel().selectFirst();
        comboZnamka.getSelectionModel().selectFirst();
        comboVrstaVozila.getSelectionModel().selectFirst();

    }
}
