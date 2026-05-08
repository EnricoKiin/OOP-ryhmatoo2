package ryhmatoo.oopryhmatoo2;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Voitlus  implements StseeniLooja{

    private SeanssiHaldur vahetaja;
    private Mäng loogika;
    private ImageView vastasePilt;
    private TextArea logi = new TextArea();
    private ArrayList<Button> nupud;
    private boolean tegevusKaib;

    public Voitlus(SeanssiHaldur vahetaja, Mäng loogika) {
        this.vahetaja = vahetaja;
        this.loogika = loogika;
        this.vastasePilt = looVastane(loogika.getVastane());
        nupud = new ArrayList<>(3);
        tegevusKaib = false;
    }

    private ImageView looVastane(Vastane vastane) {
        return new ImageView(new Image(vastane.getNimi() + ".png"));
    }

    public TextArea getLogi() {
        return logi;
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



    public Scene looStseen() {

        BorderPane juur = new BorderPane();

        // Ekraani jaotus
        StackPane ulemineAla = new StackPane();
        HBox alumineAla = new HBox();
        juur.setCenter(ulemineAla);
        juur.setBottom(alumineAla);

        // 70% Kõrgusest ülemise alale ja 30% alumisele
        alumineAla.prefHeightProperty().bind(juur.heightProperty().multiply(0.3));


        // tudengi pilt
        ImageView tegelane = new ImageView(new Image("tegelane.png"));

        StackPane tegelaseKoht = looKaraketeriAla(tegelane, ulemineAla);
        StackPane.setAlignment(tegelaseKoht, Pos.BOTTOM_RIGHT);

        ulemineAla.setBorder(new Border(new BorderStroke(
                Color.BLUE,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2))));

        // Vastase loomine
        paneVastanePaika(ulemineAla);


        // 3 nuppu
        Button rundaNupp  = looNupp("RÜNDA");
        Button kaitseNupp = looNupp("KAITSE");
        Button raviNupp   = looNupp("RAVI");
        nupud.add(rundaNupp);
        nupud.add(kaitseNupp);
        nupud.add(raviNupp);


        rundaNupp.setOnMouseClicked(e -> teeTegevus(Tegevus.RYNDA));
        kaitseNupp.setOnMouseClicked(e -> teeTegevus(Tegevus.KAITSE));
        raviNupp.setOnMouseClicked(e -> teeTegevus(Tegevus.RAVI));


        VBox nupud = new VBox(10, rundaNupp, kaitseNupp, raviNupp);
        nupud.setAlignment(Pos.CENTER); // keskele

        nupud.setBorder(new Border(new BorderStroke(
                Color.RED,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2)
        )));

        // teksti "konsool"
        logi.setEditable(false); // mängija ei saa sinna ise kirjutada
        logi.setWrapText(true); // tekst läheb järgmisele reale, kui aken saab läbi

        alumineAla.getChildren().addAll(logi, nupud);
        logi.prefWidthProperty().bind(alumineAla.widthProperty().multiply(0.7));
        nupud.prefWidthProperty().bind(alumineAla.widthProperty().multiply(0.3));

        // Algväärtustame teksti seisu
        valjastaTegelasteInfo();

        Scene stseen = new Scene(juur, 1200, 720);
        return stseen;

    }

    private void paneVastanePaika(StackPane ulemineAla) {
        StackPane vastaseKoht = looKaraketeriAla(vastasePilt, ulemineAla);
        StackPane.setAlignment(vastaseKoht, Pos.TOP_LEFT);
    }

    private static StackPane looKaraketeriAla(ImageView tegelane, StackPane ulemineAla) {
        //Aluse pilt
        ImageView alus = new ImageView(new Image("Seisukoht.png"));


        //Kombineerime
        StackPane tegelaseKoht = new StackPane(alus, tegelane);
        ulemineAla.getChildren().add(tegelaseKoht);

        //Suuruse fikseerimine
        tegelaseKoht.prefWidthProperty().bind(ulemineAla.widthProperty().multiply(0.3));
        tegelaseKoht.prefHeightProperty().bind(ulemineAla.heightProperty().multiply(0.45));
        tegelaseKoht.maxWidthProperty().bind(tegelaseKoht.prefWidthProperty());
        tegelaseKoht.maxHeightProperty().bind(tegelaseKoht.prefHeightProperty());

        //Piirame objektide suuruseid
        alus.fitWidthProperty().bind(tegelaseKoht.prefWidthProperty());
        alus.fitHeightProperty().bind(tegelaseKoht.prefHeightProperty().multiply(0.2));

        //Paneme tegelased paika
        StackPane.setAlignment(alus, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(tegelaseKoht, Pos.CENTER);

        tegelaseKoht.setBorder(new Border(new BorderStroke(
                Color.GREEN,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2))));


        // Määrame pildi laiuse
        tegelane.fitWidthProperty().bind(tegelaseKoht.prefWidthProperty().multiply(0.3));
        tegelane.setPreserveRatio(true); // Säilitab pildi proportsioonid

        return tegelaseKoht;
    }

    private void teeTegevus(Tegevus tudengiTegevus) {
        if (tegevusKaib) {
            karistus();
        }
        tegevusKaib = true;
        puhastaLogi();

        // Iga kord otsustame suvaliselt vastase tegevuse ja määrame tudengi oma
        loogika.otsustaVastaseTegevus();
        loogika.setTudengiOtsus(tudengiTegevus);

        LahinguTulemus tulemus = loogika.lahing();

        kajastaLahinguTulemus(tulemus);

        PauseTransition maga = new PauseTransition(new Duration(5000));

        maga.setOnFinished(e -> {
            if (tulemus.getSurnud() == null) valjastaTegelasteInfo();
            else voiduKontroll(tulemus);
            tegevusKaib = false;
            muudaNuppudeOlek(true);
        });
        maga.play();
    }

    private void karistus() {
        muudaNuppudeOlek(false);

        Stage lava = new Stage();

        Pane juur = new Pane();

        // Tausta loomine
        ImageView kloun = new ImageView(new Image("Kloun.png"));
        juur.getChildren().add(kloun);

        kloun.fitWidthProperty().bind(juur.widthProperty());
        kloun.fitHeightProperty().bind(juur.heightProperty());

        kloun.setPreserveRatio(false);

        //Ai abiga saadud info, kuidas kasutaja ekraani suurust saada
        Rectangle2D ekraaniSuurus = Screen.getPrimary().getVisualBounds();
        double korgus = ekraaniSuurus.getHeight();
        double laius = ekraaniSuurus.getWidth();

        Scene stseen = new Scene(juur, laius, korgus);
        lava.setTitle("Vaata seda klouni!");
        lava.setScene(stseen);
        lava.show();
    }

    private void voiduKontroll(LahinguTulemus tulemus) {
        if (tulemus.getSurnud().equals(tulemus.getVastane())) {
            // Kas on viimane vastane
            if (loogika.getVastased().size() == loogika.getMitmesVastane()) voitKoik();
            else voitUksik(tulemus);
        }
        else if (tulemus.getSurnud().equals(tulemus.getTudeng())) {
            kaotus(tulemus);
        }

        PauseTransition maga = new PauseTransition(new Duration(5000));
        maga.setOnFinished(e -> valjastaTegelasteInfo());
        maga.play();
    }

    /**
     * Väljastab hetkeste tegelaste elude seisu ja nimed
     */
    private void valjastaTegelasteInfo() {
        puhastaLogi();
        StringBuilder info = new StringBuilder(100);

        info.append(loogika.getVastane().toString() + ": " + loogika.getVastane().getElud() + "hp" + "\n");
        info.append(loogika.getTudeng().toString() + ": " + loogika.getTudeng().getElud() + "hp" + "\n");

        logi.appendText(info.toString());
    }

    /**
     * Paneb tekstikasti lahingu tulemuse info
     * @param tulemus
     */
    private void kajastaLahinguTulemus(LahinguTulemus tulemus) {
        StringBuilder info = new StringBuilder(200);
        info.append(tulemus.getTudengLause());

        // Kui tudengi lauseid pole ainult siis panema tühja rea vahele
        if (!info.isEmpty()) info.append("\n");
        info.append(tulemus.getVastaseLause());

        logi.appendText(info.toString());
    }

    private void voitKoik() {

    }

    /**
     * Kui tudeng suri, siis anname teade, et ta kaotas
     * @param tulemus -- Pakett kus info, et mis juhtus
     */
    private void kaotus(LahinguTulemus tulemus) {
        puhastaLogi();
        StringBuilder info = new StringBuilder(100);

        info.append("Surid ära!\n");
        info.append("Lõpetasid: " + tulemus.getTudeng().getPunkte() + " punktiga" + "\n");

        logi.appendText(info.toString());
    }

    /**
     * Meetod, mis aktiveerub, kui tudeng tappis vastase ning vastane polnud viimane.
     * Ütleb palju punkte tudeng sai ja kasutab vastavaid meetode, et neid lisada
     */
    private void voitUksik(LahinguTulemus tulemus) {
        puhastaLogi();
        StringBuilder info = new StringBuilder(100);

        int vastasePunktid = tulemus.getVastane().getPunkteVaart();
        info.append("Tapsid ära " + tulemus.getVastane().toString() + "\n");
        info.append("Teenisid " + vastasePunktid + " punkti!");
        tulemus.getTudeng().lisaPunkte(vastasePunktid);

        logi.appendText(info.toString());

        // Vahetame vastase
        vahetaVastane();

    }

    private void vahetaVastane() {
        loogika.vahetaVastane();
        vastasePilt.setImage(new Image(loogika.getVastane().getNimi() + ".png"));
    }

    /**
     * Puhastab teksti ala. Peab appendima, muidu läheb läbi ainult viimane tekst setText puhul
     */
    private void puhastaLogi() {
        //logi.appendText("\n".repeat(15));
        logi.clear();
    }

    /**
     * Muudab nuppude vajutamise võimalikkust
     * @param olek -- mis olekus sa tahad et nupud oleksid. Töötab intuitiivselt.
     */
    private void muudaNuppudeOlek(boolean olek) {
        for (Button button : nupud) {
            button.setDisable(!olek);
        }

    }
}
