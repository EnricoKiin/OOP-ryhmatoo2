package ryhmatoo.oopryhmatoo2;

import java.util.ArrayList;
import java.util.List;

/**
 * Kõikide vastaste ülemklass, määrab ära peamise käitumise.
 * Lisab vastastele ründeLaused
 */
public abstract class Vastane extends Tegelane{

    private int punkteVaart;
    private List<String> ryndeLaused = new ArrayList<>();
    private List<String> kaitseLaused = new ArrayList<>();

    public Vastane(String nimi, int elud, double kaitseProtsent, int rynda_dmg) {
        super(nimi, elud, kaitseProtsent, rynda_dmg);
        this.punkteVaart = arvutaPunkte();
    }

    /**
     * Pseudo valemid arvutab vastastele punkte. Peamine idee, et mida tugevam vastane, seda rohkem punkte
     * @return punktide arv
     */
    public int arvutaPunkte() {
        int punkte = (int)(this.getMaxElud() * 0.7) + this.getRynda_dmg() * 4 + (int)(this.getKaitseProtsent() * 10);
        return punkte;
    }

    public int getPunkteVaart() {
        return this.punkteVaart;
    }

    /**
     * Vastase tegevuse, kui tal tuleb BOOST tegevus
     */
    public void ryndeBoost() {
        int boost = 2;

        this.setRynda_dmg(this.getRynda_dmg() + boost);
    }

    // Ründe ja kaitse lausete sättijad

    public abstract void lisaRyndelaused();
    public abstract void lisaKaitselaused();

    public void setRyndeLaused(String[] ryndeLaused) {
        for (String s : ryndeLaused) {
            this.ryndeLaused.add(s);
        }
    }

    public void setKaitselaused(String[] kaitseLaused) {
        for (String s : kaitseLaused) {
            this.kaitseLaused.add(s);
        }
    }

    public List<String> getRyndeLaused() {
        return ryndeLaused;
    }

    public List<String> getKaitselaused() {
        return kaitseLaused;
    }
}
