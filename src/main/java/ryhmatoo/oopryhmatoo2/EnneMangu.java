package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnneMangu implements StseeniLooja{

    private SeanssiHaldur vahetaja;

    public EnneMangu(SeanssiHaldur vahetaja) {
        this.vahetaja = vahetaja;
    }


    public Scene looStseen() {

        VBox sisu = new VBox(20);
        sisu.setAlignment(Pos.CENTER);
        sisu.setPadding(new Insets(30));

        // mängija nime sisestamine
        Label nimiSilt = new Label("Sisesta oma nimi:");
        TextField nimiVäli = new TextField();
        nimiVäli.setPromptText("Piro Kunn");
        nimiVäli.setMaxWidth(250);


        // alustamise nupp
        Button alustaNupp = new Button("Alusta");

        alustaNupp.setOnMouseClicked(e -> {
            String nimi;
            if (nimiVäli.getText().isEmpty()) {
                nimi = "Piro Kunn";
            }
            else {
                nimi = nimiVäli.getText();
            }
            Mäng loogika = new Mäng(nimi);

            Voitlus voitlus = new Voitlus(vahetaja, loogika);
            vahetaja.lisaStseen("VOITLUS", voitlus.looStseen());
            vahetaja.vahetaStseen("VOITLUS");

        });

        sisu.getChildren().addAll(nimiSilt, nimiVäli, alustaNupp);

        Scene stseen = new Scene(sisu, 1200, 720);
        return stseen;

    }
}
