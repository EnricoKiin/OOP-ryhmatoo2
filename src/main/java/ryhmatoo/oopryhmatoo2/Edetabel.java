package ryhmatoo.oopryhmatoo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Edetabel implements StseeniLooja{
    private SeanssiHaldur vahetaja;
    private StackPane juur;
    private ScrollPane tabel;

    public Edetabel(SeanssiHaldur vahetaja) {
        this.vahetaja = vahetaja;
        this.juur = new StackPane();
        this.tabel = looTabel();
    }

    /**
     * Sellega saab väljaspoolt klassi öelda, et nüüd peaks uuendama tabelit enne kui jõutakse siia
     */
    public void uuendaTabelit() {
        tabel = looTabel();
    }

    /**
     * Loob edetabeli
     * @return edetabel
     */
    private ScrollPane looTabel() {
        List<AbiMangija> tulemused = new ArrayList<>();

        try {
            tulemused.addAll(loeSisse());
        }
        // Vea puhul näitame seda ja suuname tagasi stardimenüüle
        catch (IOException e) {
            edetabeliVigaPopUp();
            vahetaja.vahetaStseen("START");
            // jätame tühja edetabeli
            return new ScrollPane();
        }

        //ScrollPane kasutamise idee on saaud AI käes
        ScrollPane tabel = new ScrollPane();
        juur.getChildren().add(tabel);
        VBox read = new VBox(15);

        tabel.setContent(read);

        // Need ka AI käest
        tabel.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tabel.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        //Päise rida
        //Hetkel pole kasutuses, aga jätsin siia ikka, kui tekib tahtmine
        /*
        HBox pais = new HBox(10);
        Text paiseKoht = new Text("Koht");
        Text paiseNimi = new Text("Nimi");
        Text paisePunkte = new Text("Punkte");
        pais.setAlignment(Pos.CENTER);
        pais.getChildren().addAll(paiseKoht, paiseNimi, paisePunkte);
        read.getChildren().add(pais);

         */



        // Mangija info tabelisse
        for (int i = 0; i < tulemused.size(); i++) {
            HBox rida = looRida(read, tulemused, i);

            read.getChildren().add(rida);
        }

        //Asetus
        read.setAlignment(Pos.TOP_CENTER);
        StackPane.setAlignment(tabel, Pos.CENTER);
        tabel.setFitToWidth(true);
        tabel.setFitToHeight(true);


        //Suurused
        tabel.prefWidthProperty().bind(juur.widthProperty().multiply(0.45));
        tabel.prefHeightProperty().bind(juur.heightProperty().multiply(0.85));

        tabel.maxHeightProperty().bind(tabel.prefHeightProperty());
        tabel.maxWidthProperty().bind(tabel.prefWidthProperty());

        // Tabel taust
        // Puhas AI genereeritud rida
        tabel.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        //Debug
        /*
        read.setBorder(new Border(
                new BorderStroke(
                        Color.RED,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(2)
                )
        ));*/
        return tabel;
    }

    /**
     * Kui juhtub edetabeli info hankimisel viga, siis näitame veateadet kasutajale
     */
    private void edetabeliVigaPopUp() {
        Stage lava = new Stage();
        StackPane juur = new StackPane();
        Text tekst = new Text("Edetabeli info saamisel tekkis viga");
        tekst.setFont(Font.font(18));

        // Lisamine ja asukoht
        juur.getChildren().add(tekst);
        StackPane.setAlignment(tekst, Pos.CENTER);

        // Taust
        juur.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene stseen = new Scene(juur, 300, 200);
        lava.setTitle("Edetabeli viga");
        lava.setScene(stseen);
        lava.show();
    }

    /**
     * Loob edetabeli stseeni
     * @return Edetabli stseen
     */
    public Scene looStseen() {
        ImageView taust = new ImageView(new Image("Edetabel_taust.png"));

        //Tausta suurus
        taust.fitHeightProperty().bind(juur.heightProperty());
        taust.fitWidthProperty().bind(juur.widthProperty());

        juur.getChildren().add(0, taust);


        // Nuppu loomine
        Button tagasi = new Button("Tagasi");
        tagasi.setFont(Font.font(15));
        tagasi.setTextFill(Color.WHITESMOKE);
        juur.getChildren().add(tagasi);

        tagasi.setOnMouseClicked(e -> vahetaja.vahetaStseen("START"));

        // Taust
        Image nupuTaust = new Image("Edetabeli_nupp.png");

        BackgroundImage nuppBg = new BackgroundImage(nupuTaust,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,true, true, true, false));

        tagasi.setBackground(new Background(nuppBg));

        //Suuruse muutmine
        tagasi.prefWidthProperty().bind(juur.widthProperty().multiply(0.1));
        tagasi.prefHeightProperty().bind(juur.heightProperty().multiply(0.1));

        // Nuppu asend
        StackPane.setAlignment(tagasi, Pos.TOP_LEFT);

        //Tabel

        Scene stseen = new Scene(juur, 1200, 720);
        return stseen;

    }

    /**
     * Loob edetabli jaoks ridu
     * @param read -- VBox, mis hoiab ridu
     * @param tulemused -- Sorteeritud list võitjatest
     * @param idx -- mitmes võitja hetkel on
     * @return Tagastab edetabeli rea stiliseeritult
     */
    private HBox looRida(VBox read, List<AbiMangija> tulemused, int idx) {
        HBox rida = new HBox(10);
        rida.setAlignment(Pos.CENTER);

        //Info loomine
        Text koht = looKiri((idx + 1) + ".");
        Text nimi = looKiri(tulemused.get(idx).getNimi());
        Text punkte = looKiri(tulemused.get(idx).getPunkte() + "");

        rida.getChildren().addAll(koht, nimi, punkte);

        // Tausta määramine
        Image ajutine;
        switch (idx + 1) {
            case 1:
                ajutine = new Image("kuld_rida.png");
                break;
            case 2:
                ajutine = new Image("hobe_rida.png");
                break;
            case 3:
                ajutine = new Image("pronks_rida.png");
                break;
            default:
                ajutine = new Image("tava_rida.png");
        }

        BackgroundImage taust = new BackgroundImage(ajutine,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        rida.setBackground(new Background(taust));

        //Suurus
        rida.prefHeightProperty().bind(read.heightProperty().multiply(0.05));
        rida.prefWidthProperty().bind(read.widthProperty().multiply(0.4));

        return rida;
    }

    /**
     * Määrab edetabelis rea kirjete jaoks kindla stiili
     * @param s -- Tekst millele stiili määrame
     * @return Stiliseeritud tekst
     */
    private Text looKiri(String s) {
        Text tekst = new Text(s);
        tekst.setFill(Color.WHITESMOKE);
        tekst.setFont(Font.font(17));
        return tekst;
    }

    /**
     * Loeb edetabeli info sisse failist edetabel.dat
     * @return Tagastab sorteeritud listi mängijatest kasutades abiklassi AbiMangija
     * @throws IOException -- Vajalik et töötaks
     */
    private List<AbiMangija> loeSisse() throws IOException{
        List<AbiMangija> tulemused = new ArrayList<>();
        try (DataInputStream sisse = new DataInputStream(new FileInputStream("edetabel.dat"))) {

            int tulemusi = sisse.readInt();
            int punkte;
            String nimi;
            for (int i = 0; i < tulemusi; i++) {
                nimi = sisse.readUTF();
                punkte = sisse.readInt();

                tulemused.add(new AbiMangija(nimi, punkte));
            }
        }

        Collections.sort(tulemused);

        return tulemused;
    }

    /**
     * Abiklass, et saaksin sorteerida edetabeli mängijaid punktide järgi
     */
    public class AbiMangija implements Comparable<AbiMangija>{
        private String nimi;
        private int punkte;

        public AbiMangija(String nimi, int punkte) {
            this.nimi = nimi;
            this.punkte = punkte;
        }

        @Override
        public int compareTo(AbiMangija o2) {
            return Integer.compare(o2.getPunkte(), punkte);
        }

        public int getPunkte() {
            return punkte;
        }

        public String getNimi() {
            return nimi;
        }
    }


}
