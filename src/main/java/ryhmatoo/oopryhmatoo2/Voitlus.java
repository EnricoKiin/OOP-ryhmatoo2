package ryhmatoo.oopryhmatoo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Voitlus extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage peaLava) {

        BorderPane suur = new BorderPane();

        // tudengi pilt
        Image pilt = new Image("kriips.png");

        ImageView pildiVaade = new ImageView(pilt);

        // paigutame paremale
        suur.setRight(pildiVaade);

        // teksti "konsool"
        TextArea logi = new TextArea();
        logi.setEditable(false); // mängija ei saa sinna ise kirjutada
        logi.setWrapText(true); // tekst läheb järgmisele reale, kui aken saab läbi

        suur.setBottom(logi);


        Scene stseen1 = new Scene(suur);

        peaLava.setTitle("Võitlus");

        peaLava.setScene(stseen1);

        peaLava.show();

    }
}
