import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Karistus {

    public void start(Stage lava) {
        Pane juur = new Pane();

        // Tausta loomine
        Image img = new Image("Kloun.png");
        BackgroundImage bg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,true, true, true, true));

        juur.setBackground(new Background(bg));

        Scene stseen = new Scene(juur, 400, 400);
        lava.setTitle("Vaata seda klouni!");
        lava.setScene(stseen);
        lava.show();
    }
}
