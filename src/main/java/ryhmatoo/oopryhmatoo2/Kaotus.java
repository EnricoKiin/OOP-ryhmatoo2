package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;


public class Kaotus implements StseeniLooja{

    private SeanssiHaldur vahetaja;

    public Kaotus(SeanssiHaldur vahetaja) {
        this.vahetaja=vahetaja;
    }

    /**
     * Looba kaotus ekraani stseeni
     * @return Kaotus ekraani stseen
     */
    public Scene looStseen() {

        StackPane sisu = new StackPane();

        // taustapilt
        Image pilt = new Image(
                "you_died.jpg"
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

        // nupp alla keskele
        StackPane.setAlignment(koduNupp, Pos.BOTTOM_CENTER);
        koduNupp.setTranslateY(-40);

        sisu.getChildren().add(koduNupp);

        return new Scene(sisu, 1200, 720, Color.BLACK);
    }
}
