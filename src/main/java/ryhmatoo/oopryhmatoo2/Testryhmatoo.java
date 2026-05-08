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


    // käivitame Stardimenüü
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Tekitame Stardimenüü ja selle kaks järgmist ekraani ning anname töö üle GUI-le ning selle haldurile
     */
    public void start(Stage lava) throws StseeniErind {

        SeanssiHaldur vahetaja = new SeanssiHaldur(lava);

        // Ekraanide loome
        Stardimenuu algus = new Stardimenuu(vahetaja);
        EnneMangu nimeValik = new EnneMangu(vahetaja);


        vahetaja.lisaStseen("START",algus.looStseen());
        vahetaja.lisaStseen("NIMI", nimeValik.looStseen());

        vahetaja.vahetaStseen("START");
    }
}
