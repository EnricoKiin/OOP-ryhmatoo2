package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnneMangu {

    public static void ava() {
        Stage lava = new Stage();
        lava.setTitle("Mängu seaded");
        lava.initModality(Modality.APPLICATION_MODAL); // stardimenüüs ei saa nuppe vajutada, kuni vaheaken on lahti

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

        alustaNupp.setOnAction(e -> {

            String nimi;
            if (nimiVäli.getText().isEmpty()) {
                nimi = "Piro Kunn";
            }
            else {
                nimi = nimiVäli.getText();
            }


            lava.close();

            // saadame nime edasi ja alustame võitlust
            Stage voitlusLava = new Stage();
            Voitlus voitlus = new Voitlus();
            voitlus.start(voitlusLava);

            Testryhmatoo.main(voitlus.getLogi(), nimi);
        });

        sisu.getChildren().addAll(nimiSilt, nimiVäli, alustaNupp);

        Scene stseen = new Scene(sisu, 400, 350);
        lava.setScene(stseen);
        lava.show();
    }
}
