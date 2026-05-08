package ryhmatoo.oopryhmatoo2;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Peaklass
 */
public class Testryhmatoo extends Application {

    private TextArea teadeteLogi;


    // käivitame Stardimenüü
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Tekitame Tudengi ja Vastase ning mängime surmani.
     */
    public void start(Stage lava) throws StseeniErind {

        SeanssiHaldur vahetaja = new SeanssiHaldur(lava);

        // Ekraanide loome
        Stardimenuu algus = new Stardimenuu(vahetaja);
        EnneMangu nimeValik = new EnneMangu(vahetaja);


        vahetaja.lisaStseen("START",algus.looStseen());
        vahetaja.lisaStseen("NIMI", nimeValik.looStseen());

        vahetaja.vahetaStseen("START");


        /*
        Tudeng tudeng = new Tudeng(nimi, 20, 0.5, 5, teadeteLogi);

        // Vastaste loomine abifunktsiooniga
        ArrayList<Vastane> vastased = new ArrayList<>();
        Baar baar1 = looBaar("Atso", 1, teadeteLogi);
        Baar baar2 = looBaar("Seik", 2, teadeteLogi);
        Baar baar3 = looBaar("Möku", 3, teadeteLogi);
        vastased.add(baar1);
        vastased.add(baar2);
        vastased.add(baar3);

        // Loopime kõik vastased läbi, kuni keegi sureb.
        for (Vastane vastane : vastased) {
            Mäng mäng = new Mäng(tudeng, vastane, teadeteLogi);
            mäng.mängi();

            // Lisa shade, kui mängija ei saa hakkama
            if (!tudeng.onElus()) {
                Platform.runLater(() ->teadeteLogi.appendText("Kaotasid mängu. Get good kid\n"));
                break;
            }
        }
        if (tudeng.onElus()) {
            Platform.runLater(() -> teadeteLogi.appendText("Lõpetasid mängu " + tudeng.getPunkte() + " punktiga!" + "\n"));
        }

         */
    }
}
