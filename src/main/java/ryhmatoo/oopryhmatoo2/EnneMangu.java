package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnneMangu implements StseeniLooja{

    private SeanssiHaldur vahetaja;

    public EnneMangu(SeanssiHaldur vahetaja) {
        this.vahetaja = vahetaja;
    }

    /**
     * Loob nime valimiseks stseeni
     * @return Stseen, kus saab endale nime valida
     */
    public Scene looStseen() {

        // Kus tahame elemente hoida
        VBox sisu = new VBox(20);
        sisu.setAlignment(Pos.CENTER);
        sisu.setPadding(new Insets(30));

        // mängija nime sisestamine
        Label nimiSilt = new Label("Sisesta oma nimi:");
        TextField nimiVäli = new TextField();
        nimiVäli.setPromptText("Piro Kunn");
        nimiVäli.setMaxWidth(250);



        // captcha kontroll
        Label captchaSilt = new Label("TÕESTA, ET SA POLE ROBOT\nSisesta täisarv vahemikus 1-10:");
        captchaSilt.setAlignment(Pos.CENTER);
        captchaSilt.setTextAlignment(TextAlignment.CENTER);

        TextField captchaVäli = new TextField();
        captchaVäli.setMaxWidth(100);

        // veateade
        Label veaSilt = new Label();
        veaSilt.setStyle("-fx-text-fill: red;");



        // alustamise nupp
        Button alustaNupp = new Button("Alusta");

        alustaNupp.setOnAction(e -> {

            try {

                // proovime muuta captcha sisendi täisarvuks
                int arv = Integer.parseInt(captchaVäli.getText());

                // kontrollime vahemikku
                if (arv < 1 || arv > 10) {
                    throw new IllegalArgumentException("Vale arv");
                }

                // nime kontroll
                String nimi;

                // nime vaikeväärtuseks on "Piro Kunn"
                if (nimiVäli.getText().isEmpty()) {
                    nimi = "Piro Kunn";
                } else {
                    nimi = nimiVäli.getText();
                }

                // mäng käivitub
                Mäng loogika = new Mäng(nimi);

                Voitlus voitlus = new Voitlus(vahetaja, loogika);
                vahetaja.lisaStseen("VOITLUS", voitlus.looStseen());
                vahetaja.vahetaStseen("VOITLUS");

            }

            catch (NumberFormatException ex) {
                veaSilt.setText("Sisesta täisarv!");
            }

            catch (IllegalArgumentException ex) {
                veaSilt.setText(ex.getMessage());
            }

        });

        sisu.getChildren().addAll(
                nimiSilt,
                nimiVäli,
                captchaSilt,
                captchaVäli,
                alustaNupp,
                veaSilt
        );

        Scene stseen = new Scene(sisu, 1200, 720);
        return stseen;

    }
}
