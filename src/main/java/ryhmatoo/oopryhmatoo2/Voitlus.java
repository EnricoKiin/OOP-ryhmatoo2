package ryhmatoo.oopryhmatoo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Voitlus  implements StseeniLooja{

    private SeanssiHaldur vahetaja;
    private Mäng loogika;
    private ImageView vastasePilt;

    public Voitlus(SeanssiHaldur vahetaja, Mäng loogika) {
        this.vahetaja = vahetaja;
        this.loogika = loogika;
        this.vastasePilt = looVastane(loogika.getVastane());
    }

    private ImageView looVastane(Vastane vastane) {
        return new ImageView(new Image(vastane.getNimi() + ".png"));
    }


    private TextArea logi = new TextArea();

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
        StackPane vastaseKoht = looKaraketeriAla(vastasePilt, ulemineAla);
        StackPane.setAlignment(vastaseKoht, Pos.TOP_LEFT);


        // 3 nuppu
        Button rundaNupp  = looNupp("RÜNDA");
        Button kaitseNupp = looNupp("KAITSE");
        Button raviNupp   = looNupp("RAVI");


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

        Scene stseen = new Scene(juur, 1200, 720);
        return stseen;

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

        // Iga kord otsustame suvaliselt vastase tegevuse ja määrame tudengi oma
        loogika.otsustaVastaseTegevus();
        loogika.setTudengiOtsus(tudengiTegevus);

        loogika.lahing();
    }
}
