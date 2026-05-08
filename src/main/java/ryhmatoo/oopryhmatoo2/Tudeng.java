package ryhmatoo.oopryhmatoo2;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Peategelane meie loos, kes võitleb erinevate vastastega.
 * Peamine omadus on punktid, mida saab vastaste tapmise eest
 */
public class Tudeng extends Tegelane{

    private int punkte;


    public Tudeng(String nimi, int elud, double kaitseProtsent, int rynda_dmg) {
        super(nimi, elud, kaitseProtsent, rynda_dmg);
        this.punkte = 0;
    }

    /**
     * Tudengi tegevus, kui ta otsustab teha BOOST. Lisab elusid 30% elusid juurde
     * Ei lähe üle max elude, mis alguses määrati talle
     */
    public void saaStippi(LahinguTulemus info) {
        int maxElud = this.getMaxElud();
        int hetkelElud = this.getElud();
        info.lisaTudengLause("Leidsid maast viieka. Saad edasi juua");

        // Elude andmise loogika
        // Esimene haru kui saab max eludeni tagasi ja teine kui ei saa
        int elusidJuurde = (int)(maxElud * 0.3);
        if (hetkelElud + elusidJuurde > maxElud) {
            this.setElud(maxElud);
            info.lisaTudengLause("Said kõik elud tagasi");
        }
        else {
            this.setElud(hetkelElud + elusidJuurde);
           info.lisaTudengLause("Said " + elusidJuurde + " hp juurde");
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
    public void kaotaElud(int dmg, Vastane vastane, LahinguTulemus info) {
        List<String> vastaseLaused = vastane.getRyndeLaused();

        // Lausete suvaline valik ja eraldi haru, kui peategelane otsustas end kaitsta (KAITSE tegevus)
        int valik = (int)(Math.random() * vastaseLaused.size());
        info.lisaTudengLause(vastaseLaused.get(valik));
        if (this.getTegevus() == Tegevus.KAITSE) {
            info.lisaTudengLause("Kuid su lemmik laul hakkas mängima. Kaotad vähem elusid");
        }
        info.lisaTudengLause("Kaotasid "  + dmg + " elu");

        super.kaotaElud(dmg);

    }
}
