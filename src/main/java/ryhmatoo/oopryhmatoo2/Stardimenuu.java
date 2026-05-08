package ryhmatoo.oopryhmatoo2;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Stack;

public class Stardimenuu implements StseeniLooja{
    private SeanssiHaldur vahetaja;

    public Stardimenuu(SeanssiHaldur vahetaja) {
        this.vahetaja = vahetaja;
    }

    public Scene looStseen() throws StseeniErind {
        Pane juur = new Pane();

        // Tausta loomine
        Image img = new Image("Stardiekraan.png");
        BackgroundImage bg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,true, true, true, true));

        juur.setBackground(new Background(bg));

        // Kõik nupud on omakord VBox-s
        Pane menüü = new Pane();
        VBox nupud = new VBox(15);
        juur.getChildren().add(menüü);

        Button mangiNupp = new Button("Alusta uus mäng");
        Button edetabel = new Button("Vaata edetabelit");
        nupud.getChildren().addAll(mangiNupp, edetabel);


        //Menüü suurus
        menüü.prefWidthProperty().bind(juur.widthProperty().multiply(0.2));
        menüü.prefHeightProperty().bind(juur.heightProperty().multiply(0.45));

        //Menüü taust
        ImageView nupudImg = new ImageView(new Image("Vbox.png"));
        nupudImg.fitWidthProperty().bind(menüü.widthProperty());
        nupudImg.fitHeightProperty().bind(menüü.heightProperty());
        nupudImg.setPreserveRatio(false);

        menüü.getChildren().addAll(nupudImg, nupud);

        // Menüü asukoht
        menüü.layoutXProperty().bind(juur.widthProperty().multiply(0.02));
        menüü.layoutYProperty().bind(juur.heightProperty().multiply(0.04));

        //Vbox asukoht
        // AI abi kasutatud, et leida viis, kuidas Pane sees suurust muuta
        nupud.layoutXProperty().bind(
                menüü.widthProperty()
                        .subtract(nupud.widthProperty())
                        .divide(2)
        );

        nupud.layoutYProperty().bind(
                menüü.heightProperty()
                        .subtract(nupud.heightProperty())
                        .divide(2)
        );

        //Vbox suurus
        nupud.prefWidthProperty().bind(nupudImg.fitWidthProperty().multiply(0.9));
        nupud.prefHeightProperty().bind(nupudImg.fitHeightProperty().multiply(0.65));

        nupud.setAlignment(Pos.CENTER);

        // Nuppude stiilid
        stiiliNupp(mangiNupp, nupud);
        stiiliNupp(edetabel, nupud);

        // avaneb vaheekraan, andmete sisestamiseks
        mangiNupp.setOnAction(e -> vahetaja.vahetaStseen("NIMI"));



        // Paremal pool on mängu pealkiri

        Image pealkiri = new Image("Pealkiri.png");
        ImageView pealkiriImg = new ImageView(pealkiri);

        // Asukoha määramine
        // Siin kasutasin AI abi, et see lihtsalt tööle saada
        // Nkn mingi parem viis, aga ma läksin juba vaikselt hulluks
        pealkiriImg.layoutXProperty().bind(
                juur.widthProperty().multiply(0.65)
                        .subtract(pealkiriImg.fitWidthProperty().divide(2))
        );

        pealkiriImg.layoutYProperty().bind(
                Bindings.createDoubleBinding(
                        () -> juur.getHeight() * 0.2
                                - pealkiriImg.getBoundsInLocal().getHeight() / 2,

                        juur.heightProperty(),
                        pealkiriImg.boundsInLocalProperty()
                )
        );


        // Suuruse määramine
        pealkiriImg.fitWidthProperty().bind(juur.widthProperty().multiply(0.5));
        pealkiriImg.setPreserveRatio(true);


        juur.getChildren().add(pealkiriImg);


        //Debuggimiseks
        //kusKuratOnSeeKast(nupud, menüü);

        Scene stseen = new Scene(juur, 1200, 720);
        return stseen;
    }

    //Ainult nuppude ja menüü debuggimiseks, sest need vihkasid mind
    private void stiiliNupp(Button nupp, VBox nupud) {

        //Iga nupp saab täpselt sama palju ruumi
        nupp.prefHeightProperty().bind(nupud.heightProperty().multiply(0.45));
        nupp.prefWidthProperty().bind(nupud.prefWidthProperty().multiply(0.9));

        Image bg = new Image("nupp.png");

        BackgroundImage bgImg = new BackgroundImage(bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100,100,true,true, true, false));

        nupp.setBackground(new Background(bgImg));
        nupp.setTextFill(Color.GOLD);
        nupp.setFont(Font.font(15));
    }

    private static void kusKuratOnSeeKast(VBox nupud, Pane menüü) {
        menüü.setBorder(new Border(
                new BorderStroke(
                        Color.RED,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(2)
                )
        ));

        nupud.setBorder(new Border(
                new BorderStroke(
                        Color.RED,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(2)
                )
        ));
    }
}
