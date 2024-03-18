package urejevalnikHTML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrejevalnikKontroler implements Initializable {

    public Label status;
    public TitledPane urejevalnikHTML;
    public HTMLEditor editorHTML;
    public TitledPane urejevalnikSimple;
    public TextArea editorSimple;
    public TitledPane dnevnik;
    public TextArea log;
    public Accordion accordion;
    public TextField search;
    public TextField replace;



    public void handleOdpri(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberite HTML datoteko");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                editorHTML.setHtmlText(sb.toString());
                editorSimple.setText(sb.toString());

                status.setText("Ime odprte datoteke: " + f.getName() + "   Velikost odprte datoteke: " + f.length() + "B");
                log.appendText("Datoteka prebrana" + f.getAbsolutePath() + "\n");
            } catch (Exception e) {
                status.setText("Odpiranje datoteke neuspešno");
                log.appendText("Odpiranje datoteke neuspešno" + "\n");
            }
        }
    }

    public void handleShrani(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberite datoteko za shranjevanje");
        File f = fc.showSaveDialog(null);
        if (f != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(editorHTML.getHtmlText());
                bw.close();
                log.appendText("Datoteka shranjena" + f.getAbsolutePath() + "\n");
            } catch (Exception e) {
                status.setText("Shranjevanje datoteke neuspešno");
                log.appendText("Shranjevanje datoteke neuspešno" + "\n");
            }
        }
    }

    public void handleIzhod(ActionEvent actionEvent) { System.exit(0); }

    public void handleNajdi(ActionEvent actionEvent) {
        if (search.getText().isEmpty()) {
            status.setText("Polje za iskanje je prazno");
            log.appendText("Ni besedila v polju za iskanje" + "\n");
        }
        else {
            String text = editorHTML.getHtmlText();
            String najdi = search.getText();

            if (!text.contains(najdi)) {
                status.setText("Ni zadetkov");
                log.appendText("Ni zadetkov za -" + najdi + "\n");
            }
            else {
                status.setText("Najdena beseda " + najdi);
                log.appendText("Poiskali ste besedo "+ najdi + "\n");
            }
        }
    }

    public void handleNajdiVse(ActionEvent actionEvent) {
        if (editorHTML.getHtmlText().isEmpty()) {
            status.setText("Polje za tekst je prazno");
            log.appendText("Ni besedila" + "\n");
        }
        if (search.getText().isEmpty() || replace.getText().isEmpty()) {
            status.setText("Izpolnite oba polja za iskanje in menjavo");
            log.appendText("Ni besedila" + "\n");
        }
        else {
            String zamenjaj = search.getText();
            String text = editorHTML.getHtmlText();

            // Stetje pojavitev
            int i = 0;
            Pattern p = Pattern.compile(zamenjaj);
            Matcher m = p.matcher(text);
            while (m.find()) {
                i++;
            }
            text = text.replaceAll(zamenjaj, replace.getText());
            editorHTML.setHtmlText(text);

            status.setText("Iskano besedilo = " + zamenjaj + ". Zamenjanih je bilo " + i + " pojavitev");
            log.appendText("Zamenjano besedilo -" + zamenjaj+ " z -" + replace.getText() + "\n");
        }
    }

    public void handleAvtor(ActionEvent actionEvent) {
        String avtor = "Matic Zorn";
        status.setText("Avtor: " + avtor);
        log.appendText("Izpis avtorja: " + avtor + "\n");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accordion.setExpandedPane(urejevalnikHTML);
    }

    public void handleHB(MouseEvent mouseEvent) { editorSimple.setText(editorHTML.getHtmlText()); }

    public void handleBH(MouseEvent mouseEvent) { editorHTML.setHtmlText(editorSimple.getText()); }
}