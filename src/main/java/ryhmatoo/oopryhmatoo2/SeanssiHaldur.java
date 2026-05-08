package ryhmatoo.oopryhmatoo2;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class SeanssiHaldur {
    private Stage peaLava;
    private HashMap<String, Scene> ekraanid;

    public SeanssiHaldur(Stage lava) {
        this.peaLava = lava;
        ekraanid = new HashMap<>();

        peaLava.setTitle("Möku's Revenge");
        peaLava.show();
    }

    public void vahetaStseen(String stseeniNimi) throws StseeniErind{
        if (ekraanid.get(stseeniNimi) != null) {
            peaLava.setScene(ekraanid.get(stseeniNimi));
        }
        else throw new StseeniErind("Sellist stseeni pole olemas!");
    }

    public void lisaStseen(String nimi, Scene stseen) throws StseeniErind {
        if (ekraanid.get(nimi) == null || nimi.equals("VOITLUS")) ekraanid.put(nimi, stseen);
        else throw new StseeniErind("Ekraan juba lisatud!");
    }
}
