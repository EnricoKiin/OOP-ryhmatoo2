package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;

public class EnneMangu implements StseeniLooja{

    private SeanssiHaldur vahetaja;
    private int gnome;

    public EnneMangu(SeanssiHaldur vahetaja) {
        this.vahetaja = vahetaja;
        // Ainult selleks et piirata gnome arvu
        gnome = 0;
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

        //Klava event
        Label saladus = new Label("Mida iganes sa teed ära vajut g tähte");
        saladus.setFont(Font.font(18));

        sisu.getChildren().addAll(
                nimiSilt,
                nimiVäli,
                captchaSilt,
                captchaVäli,
                alustaNupp,
                veaSilt,
                saladus
        );

        Scene stseen = new Scene(sisu, 1200, 720);

        //AI abiga sain teada, et vaja teha läbi eventFilter, sest tavalise setOnKeyPressed ei saanud hakkama
        stseen.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.G) {
                if (gnome % 10 == 0) klavaPopUp();
                gnome++;
            }
        });

        return stseen;

    }


    /**
     * Karistab kasutajat selle eest, et ta julges vajutada keelatud tähte
     * Gnobbling gnome
     */
    private void klavaPopUp() {
        Stage lava = new Stage();
        Pane juur = new Pane();

        // AI abiga, sain java.fx.media ning selle abil loodud video esitamise viisi
        Media meedia = new Media(getClass().getResource("/gnome.mp4").toExternalForm());

        MediaPlayer player = new MediaPlayer(meedia);
        MediaView view = new MediaView(player);

        juur.getChildren().add(view);

        //Suurus
        view.fitHeightProperty().bind(juur.heightProperty());
        view.fitWidthProperty().bind(juur.widthProperty());
        view.setPreserveRatio(false);

        Scene stseen = new Scene(juur, 600, 600);
        lava.setTitle("Gnome");
        lava.setScene(stseen);
        lava.show();
        player.play();

    }
}
