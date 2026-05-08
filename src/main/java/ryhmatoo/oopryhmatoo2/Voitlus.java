package ryhmatoo.oopryhmatoo2;

import javafx.animation.PauseTransition;
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

    /**
     * Voitlus GUI konstruktor
     * @param vahetaja -- seansihaldur, mis suudab edasi suunata järgmistele ekraanidele
     * @param loogika -- mängu loogika haldur
     */
    public Voitlus(SeanssiHaldur vahetaja, Mäng loogika) {
        this.vahetaja = vahetaja;
        this.loogika = loogika;
        this.vastasePilt = looVastane(loogika.getVastane());
        nupud = new ArrayList<>(3);
        tegevusKaib = false;
    }

    /**
     * Ette antud vaste puhul võtab selle pildi ja jätab meelde
     * @param vastane -- Vastane kellest pilti teeme
     * @return Vastase pilt
     */
    private ImageView looVastane(Vastane vastane) {
        return new ImageView(new Image(vastane.getNimi() + ".png"));
    }

    public TextArea getLogi() {
        return logi;
    }


    /**
     * Annab nuppudele stiili ja loob need
     * @param tekst -- Mis tekst nupu sees on
     * @return Stiliseeritud nupu
     */
    private Button looNupp(String tekst) {

        // AI abil tehtud stiilid
        String tavaline = "-fx-background-color: #FF8C00; -fx-font-size: 20px; -fx-font-weight: bold; -fx-min-width: 150px; -fx-min-height: 50px; -fx-cursor: hand;";
        String hover   = "-fx-background-color: #FFA500; -fx-font-size: 20px; -fx-font-weight: bold; -fx-min-width: 150px; -fx-min-height: 50px; -fx-cursor: hand;";

        Button nupp = new Button(tekst);
        nupp.setStyle(tavaline);

        nupp.setOnMouseEntered(e -> nupp.setStyle(hover));
        nupp.setOnMouseExited(e -> nupp.setStyle(tavaline));

        return nupp;
    }


    /**
     * Loob võitluse Stseeni
     * @return võitluse stseen
     */
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


        // Vastase loomine
        paneVastanePaika(ulemineAla);


        // 3 nuppu
        Button rundaNupp  = looNupp("RÜNDA");
        Button kaitseNupp = looNupp("KAITSE");
        Button raviNupp   = looNupp("RAVI");
        nupud.add(rundaNupp);
        nupud.add(kaitseNupp);
        nupud.add(raviNupp);


        // Nupud püüavad kui liiga palju nuppe vajutatud
        rundaNupp.setOnMouseClicked(e -> {
            try{
                teeTegevus(Tegevus.RYNDA);
            }
            catch (NuppuErind i) {karistus();}
        });
        kaitseNupp.setOnMouseClicked(e -> {
            try{
                teeTegevus(Tegevus.KAITSE);
            }
            catch (NuppuErind i) {karistus();}
        });
        raviNupp.setOnMouseClicked(e -> {
            try {
                teeTegevus(Tegevus.RAVI);
            }
            catch (NuppuErind i) {karistus();}
        });

        // Paneb nupud ühte kasti
        VBox nupud = new VBox(10, rundaNupp, kaitseNupp, raviNupp);
        nupud.setAlignment(Pos.CENTER); // keskele


        // teksti "konsool" - sain omadused AI-lt (editable, wraptext)
        logi.setEditable(false); // mängija ei saa sinna ise kirjutada
        logi.setWrapText(true); // tekst läheb järgmisele reale, kui aken saab läbi

        // Alumise ala elementide suurust timmimine
        alumineAla.getChildren().addAll(logi, nupud);
        logi.prefWidthProperty().bind(alumineAla.widthProperty().multiply(0.7));
        nupud.prefWidthProperty().bind(alumineAla.widthProperty().multiply(0.3));

        // Algväärtustame teksti seisu
        valjastaTegelasteInfo();

        Scene stseen = new Scene(juur, 1200, 720);

        // Debuggimiseks abi
        /*
        nupud.setBorder(new Border(new BorderStroke(
                Color.RED,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2)
        )));
        */
        ulemineAla.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2))));



        return stseen;

    }

    /**
     * Tekitab vastase kastikese.
     * @param ulemineAla -- Ala kuhu see kast panna
     */
    private void paneVastanePaika(StackPane ulemineAla) {
        StackPane vastaseKoht = looKaraketeriAla(vastasePilt, ulemineAla);
        StackPane.setAlignment(vastaseKoht, Pos.TOP_LEFT);
    }

    /**
     * Meetod, mis loob tegelase pildi abil selle jaoks vastava kasti, mida panna ulemisse ekraani ossa.
     * Täpset kasti asukohta määrab looStseen
     * @param tegelane -- Tegelase pilt, keda hakkame kombineerima kastiks
     * @param ulemineAla -- Ala kuhu hiljem ta lisame. Sellelt saab ka ta oma möödud
     * @return -- StackPane kast, kus on tegelase ja aluse pilt kokku pandud.
     */
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

        //Debuggimiseks abi ja iluks
        /*
        tegelaseKoht.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2))));

         */



        // Määrame pildi laiuse
        tegelane.fitWidthProperty().bind(tegelaseKoht.prefWidthProperty().multiply(0.6));
        tegelane.fitHeightProperty().bind(tegelaseKoht.prefHeightProperty().multiply(0.8));
        tegelane.setPreserveRatio(true); // Säilitab pildi proportsioonid

        return tegelaseKoht;
    }


    /**
     * Kui kasutaja otsustab mingise tegevuse kasuks, siis see funktsioon lükkab loogika käima ning
     * seejärel annab lahingu info edasi, et kajasta ekraanil
     * @param tudengiTegevus
     */
    private void teeTegevus(Tegevus tudengiTegevus) {
        if (tegevusKaib) {
            muudaNuppudeOlek(false);
            throw new NuppuErind("Klound oled!");
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
        });
        maga.play();
    }

    /**
     * Karistus juhtub siis, kui kasutaja teeb midagi valesti
     * Viskab musta ekraani popup akna, kus paneb sind elu üle mõtlema
     */
    private void karistus() {
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


    /**
     * Kontrollib, kas keegi on võitnud. Eeldab, et on kontrollitud, kas surnud == null
     * @param tulemus -- Paketti klass, kus on viimase lahingu tulemuse info
     */
    private void voiduKontroll(LahinguTulemus tulemus) {

        if (tulemus.getSurnud().equals(tulemus.getVastane())) {
            // Kas on viimane vastane
            if (loogika.getVastased().size() == loogika.getMitmesVastane()) voitKoik();
            else voitUksik(tulemus);
        }
        else if (tulemus.getSurnud().equals(tulemus.getTudeng())) {
            kaotus(tulemus);
        }

        // Väikene paus enne kui tagasi lähme
        PauseTransition maga = new PauseTransition(new Duration(5000));
        maga.setOnFinished(e -> valjastaTegelasteInfo());
        maga.play();
    }

    /**
     * Väljastab hetkeste tegelaste elude seisu ja nimed.
     * Lähtestab ka nuppud ja tegevuse oleku.
     */
    private void valjastaTegelasteInfo() {
        puhastaLogi();
        StringBuilder info = new StringBuilder(100);

        // Tegelaste nimed ja elud
        info.append(loogika.getVastane().toString() + ": " + loogika.getVastane().getElud() + "hp" + "\n");
        info.append(loogika.getTudeng().toString() + ": " + loogika.getTudeng().getElud() + "hp" + "\n");

        logi.appendText(info.toString());

        // Tegevuse oleku algväärtustamine ja nuppude lubamine
        tegevusKaib = false;
        muudaNuppudeOlek(true);
    }

    /**
     * Paneb tekstikasti lahingu tulemuse info
     * @param tulemus -- Paketti klass, mis hoiab viimase lahingu tulemuse infot
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

        // kaotuse stseenile viskamine
        Voit voitis = new Voit(vahetaja, loogika.getTudeng().getPunkte());
        vahetaja.lisaStseen("VOIT",voitis.looStseen());
        vahetaja.vahetaStseen("VOIT");
    }

    /**
     * Kui tudeng suri, siis anname teade, et ta kaotas
     * @param tulemus -- Pakett kus info, et mis juhtus viimases lahingus
     */
    private void kaotus(LahinguTulemus tulemus) {
        puhastaLogi();
        StringBuilder info = new StringBuilder(100);

        info.append("Surid ära!\n");
        info.append("Lõpetasid: " + tulemus.getTudeng().getPunkte() + " punktiga" + "\n");

        logi.appendText(info.toString());

        // kaotuse stseenile viskamine
        Kaotus kaotas = new Kaotus(vahetaja);
        vahetaja.lisaStseen("KAOTUS",kaotas.looStseen());
        vahetaja.vahetaStseen("KAOTUS");
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

    /**
     * Meetod, mis vahetab vastast nii loogikas kui ka GUI-s, kui on veel järgmiseid vastaseid
     */
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
