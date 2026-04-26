package ryhmatoo.oopryhmatoo2;

import java.util.List;
import javafx.scene.control.TextArea;

/**
 * Peategelane meie loos, kes võitleb erinevate vastastega.
 * Peamine omadus on punktid, mida saab vastaste tapmise eest
 */
public class Tudeng extends Tegelane{

    private int punkte;
    private TextArea teadeteLogi;


    public Tudeng(String nimi, int elud, double kaitseProtsent, int rynda_dmg, TextArea logi) {
        super(nimi, elud, kaitseProtsent, rynda_dmg);
        this.punkte = 0;
        this.teadeteLogi = logi;
    }

    /**
     * Tudengi tegevus, kui ta otsustab teha BOOST. Lisab elusid 30% elusid juurde
     * Ei lähe üle max elude, mis alguses määrati talle
     */
    public void saaStippi() {
        int maxElud = this.getMaxElud();
        int hetkelElud = this.getElud();
        teadeteLogi.appendText("Leidsid maast viieka. Saad edasi juua\n");

        // Elude andmise loogika
        // Esimene haru kui saab max eludeni tagasi ja teine kui ei saa
        int elusidJuurde = (int)(maxElud * 0.3);
        if (hetkelElud + elusidJuurde > maxElud) {
            this.setElud(maxElud);
            teadeteLogi.appendText("Said kõik elud tagasi\n");
        }
        else {
            this.setElud(hetkelElud + elusidJuurde);
            teadeteLogi.appendText("Said " + elusidJuurde + " hp juurde" + "\n");
        }
    }

    /**
     * Punktide lisamine tapetult vastastelt
     * @param punkteJuurde -- mitu punkti oli vastane väärt
     */
    public void lisaPunkte(int punkteJuurde) {
        this.punkte += punkteJuurde;
    }

    public int getPunkte() {
        return this.punkte;
    }


    /**
     * Ülekaetud lisameetod, mis võtab elusid ära tegelaselt ja ütleb vastase ründe lauseid
     * @param dmg -- Kui palju elusid kaotab
     * @param vastane -- Vastane, kelle lauseid kasutada
     */
    public void kaotaElud(int dmg, Vastane vastane) {
        List<String> vastaseLaused = vastane.getRyndeLaused();

        // Lausete suvaline valik ja eraldi haru, kui peategelane otsustas end kaitsta (KAITSE tegevus)
        int valik = (int)(Math.random() * vastaseLaused.size());
        teadeteLogi.appendText(vastaseLaused.get(valik) + "\n");
        if (this.getTegevus() == Tegevus.KAITSE) {
            teadeteLogi.appendText("Kuid su lemmik laul hakkas mängima. Kaotad vähem elusid.\n");
        }
        teadeteLogi.appendText("Kaotasid "  + dmg + " elu" + "\n");

        super.kaotaElud(dmg);

    }
}
