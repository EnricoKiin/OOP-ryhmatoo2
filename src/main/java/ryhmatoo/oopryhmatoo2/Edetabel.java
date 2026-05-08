package ryhmatoo.oopryhmatoo2;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Edetabel implements StseeniLooja{
    private SeanssiHaldur vahetaja;




    public Scene looStseen() {
        Pane juur = new Pane();

        Image taust = new Image("Edetabel_taust.png");
        BackgroundImage bg = new BackgroundImage(taust,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,true, true, false, false));

        juur.setBackground(new Background(bg));
        Scene stseen = new Scene(juur, 1200, 720);

        return new Scene(juur);

    }

}
