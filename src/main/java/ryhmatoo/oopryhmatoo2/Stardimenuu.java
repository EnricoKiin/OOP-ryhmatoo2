package ryhmatoo.oopryhmatoo2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Stardimenuu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage lava) {
        BorderPane juur = new BorderPane();

        // Jagan ekraani kaheks. Vasakul on nupud
        Pane vasakPool = new Pane();
        vasakPool.prefWidthProperty().bind(juur.widthProperty().divide(2));
        vasakPool.prefHeightProperty().bind(juur.heightProperty());
        juur.setLeft(vasakPool);

        // Kõik nupud on omakord VBox-s
        VBox nupud = new VBox(15);
        vasakPool.getChildren().add(nupud);

        nupud.layoutXProperty().bind(vasakPool.widthProperty().multiply(0.2));
        nupud.layoutYProperty().bind(vasakPool.heightProperty().multiply(0.4));
        nupud.prefWidthProperty().bind(vasakPool.widthProperty().multiply(0.2));
        nupud.prefHeightProperty().bind(vasakPool.heightProperty().multiply(0.05 * nupud.getChildren().size()));
        nupud.setFillWidth(true);


        Button mangiNupp = new Button("Alusta uus mäng");
        Button edetabel = new Button("Vaata edetabelit");

        // avaneb vaheekraan, andmete sisestamiseks
        mangiNupp.setOnAction(e -> EnneMangu.ava());

        nupud.getChildren().addAll(mangiNupp, edetabel);


        // Paremal pool on mängu pealkiri
        StackPane paremPool = new StackPane();
        juur.setRight(paremPool);
        paremPool.prefWidthProperty().bind(juur.widthProperty().divide(2));
        paremPool.prefHeightProperty().bind(juur.heightProperty());

        Text pealkiri = new Text("Tudengi seiklus");
        paremPool.getChildren().add(pealkiri);

        Scene stseen = new Scene(juur, 1200, 720);
        lava.setTitle("Stardi menüü");
        lava.setScene(stseen);
        lava.show();
    }
}
