package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Voit implements StseeniLooja {

    private SeanssiHaldur vahetaja;
    int punktid;

    public Voit(SeanssiHaldur vahetaja, int punktid) {
        this.vahetaja=vahetaja;
        this.punktid=punktid;
    }

    public Scene looStseen() {

        BorderPane sisu = new BorderPane();

        // taustapilt
        Image pilt = new Image(
                "party.jpg"
        );

        // taustapildi seaded AI-lt
        BackgroundImage taust = new BackgroundImage(
                pilt,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        100, 100,
                        true, true,
                        true, false
                )
        );

        sisu.setBackground(new Background(taust));


        // kodu nupp
        ImageView koduPilt = new ImageView(
                new Image("kodu.png"
                )
        );

        koduPilt.setFitWidth(120);
        koduPilt.setPreserveRatio(true);

        Button koduNupp = new Button();

        koduNupp.setGraphic(koduPilt);

        koduNupp.setStyle("""
        -fx-background-color: darkred;
        -fx-padding: 10;
        """);

        // kodu nupule vajutades, viib tagasi Stardimenüüsse
        koduNupp.setOnAction(e -> {
            vahetaja.vahetaStseen("START");
        });

        // nupp alla keskele AI abiga
        sisu.setBottom(koduNupp);
        BorderPane.setAlignment(koduNupp, Pos.CENTER);
        koduNupp.setTranslateY(-40);


        // naljakad pildid
        ImageView vasakPilt = new ImageView(new Image("kort.jpg"));
        vasakPilt.setFitWidth(200);
        vasakPilt.setPreserveRatio(true);

        ImageView paremPilt = new ImageView(new Image("kevin.jpg"));
        paremPilt.setFitWidth(200);
        paremPilt.setPreserveRatio(true);

        sisu.setLeft(vasakPilt);
        sisu.setRight(paremPilt);

        // AI abil padding paika pandud
        sisu.setStyle("-fx-padding: 30 50 30 50;");


        // congrats tekst
        Label congrats = new Label("CONGRATS");
        congrats.setStyle("""
            -fx-font-size: 80px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            """);


        // punktide kuvamine
        Label punkteLabel = new Label(punktid + "punkti");

        punkteLabel.setStyle("""
                -fx-font-size: 30px;
                -fx-text-fill: white;
            """);


        // AI abil Pane-ide ja Boxide loomine ning paigutamine
        StackPane center = new StackPane();

        VBox box = new VBox(10, congrats, punkteLabel);
        box.setAlignment(Pos.CENTER);

        congrats.setTranslateY(-60);

        center.getChildren().add(box);
        sisu.setCenter(center);


        return new Scene(sisu, 1200, 720, Color.WHITESMOKE);

    }


}
