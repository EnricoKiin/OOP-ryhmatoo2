package ryhmatoo.oopryhmatoo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Voitlus extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    private Button looNupp(String tekst) {

        String tavaline = "-fx-background-color: #FF8C00; -fx-font-size: 20px; -fx-font-weight: bold; -fx-min-width: 150px; -fx-min-height: 50px; -fx-cursor: hand;";
        String hover   = "-fx-background-color: #FFA500; -fx-font-size: 20px; -fx-font-weight: bold; -fx-min-width: 150px; -fx-min-height: 50px; -fx-cursor: hand;";

        Button nupp = new Button(tekst);
        nupp.setStyle(tavaline);

        nupp.setOnMouseEntered(e -> nupp.setStyle(hover));
        nupp.setOnMouseExited(e -> nupp.setStyle(tavaline));

        return nupp;
    }


    @Override
    public void start(Stage peaLava) {

        BorderPane suur = new BorderPane();

        // tudengi pilt
        Image pilt = new Image("kriips.png");
        ImageView pildiVaade = new ImageView(pilt);

        // Määrame pildi laiuse
        pildiVaade.setFitWidth(80);
        pildiVaade.setPreserveRatio(true); // Säilitab pildi proportsioonid

        StackPane pildiKonteiner = new StackPane(pildiVaade);
        StackPane.setAlignment(pildiVaade, Pos.BOTTOM_RIGHT);
        pildiKonteiner.setPadding(new Insets(0, 300, 20, 0));

        // paigutame konteineri paremale
        suur.setRight(pildiKonteiner);


        // 3 nuppu
        Button rundaNupp  = looNupp("RÜNDA");
        Button kaitseNupp = looNupp("KAITSE");
        Button raviNupp   = looNupp("RAVI");

        HBox nupud = new HBox(60, rundaNupp, kaitseNupp, raviNupp);
        nupud.setAlignment(Pos.CENTER_LEFT); // vasakule
        nupud.setPadding(new Insets(10, 0, 10, 220));


        // teksti "konsool"
        TextArea logi = new TextArea();
        logi.setEditable(false); // mängija ei saa sinna ise kirjutada
        logi.setWrapText(true); // tekst läheb järgmisele reale, kui aken saab läbi


        VBox alumineAla = new VBox(nupud, logi);
        suur.setBottom(alumineAla);


        Scene stseen1 = new Scene(suur, 1200, 720);

        peaLava.setTitle("Võitlus");

        peaLava.setScene(stseen1);

        peaLava.show();

    }
}
