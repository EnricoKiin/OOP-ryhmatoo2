package ryhmatoo.oopryhmatoo2;

/**
 * Ülemklass, millest kõik vastased ja peategelased tekivad
 * Määrab peamised omadused nagu nimi, elud ja rünne ning muud
 */
public class Tegelane {

    private String nimi;
    private int elud;
    private double kaitseProtsent;
    private int rynda_dmg;
    private int maxElud;
    private Tegevus tegevus;

    public Tegelane(String nimi, int elud, double kaitseProtsent, int rynda_dmg) {
        this.nimi = nimi;
        this.elud = elud;
        this.kaitseProtsent = kaitseProtsent;
        this.rynda_dmg = rynda_dmg;
        this.maxElud = elud;
    }
    // Igasuguste eludega tegelemiseks
    public  int getElud() {
        return this.elud;
    }
    public  void setElud(int uuedElud) {
        this.elud = uuedElud;
    }

    // Nime määramiseks
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public String getNimi() {
        return this.nimi;
    }

    /**
     * Võtab vastavalt tegelaselt sisestatud arv elusid ära. EI lähe alla 0
     * @param dmg -- Kuib palju elusid kaotab. Alati positiivne arv
     */
    public void kaotaElud(int dmg) {
        if (this.elud - dmg < 0) {
            this.elud = 0;
        }
        else {
            this.elud -= dmg;
        }
    }

    /**
     * Kontrollib, kas vastav tegelane on elus
     * @return boolean
     */
    public boolean onElus() {
        if (this.elud != 0) {
            return true;
        }
        return false;
    }

    // Siit alla on erinevad getterid ja setterid, mida alamklassid kasutavad, et pääseda ligi andmetele

    public double getKaitseProtsent() {
        return kaitseProtsent;
    }

    public int getRynda_dmg() {
        return rynda_dmg;
    }

    public String toString() {
        return this.nimi;
    }

    public int getMaxElud() {
        return maxElud;
    }

    public void setRynda_dmg(int rynda_dmg) {
        this.rynda_dmg = rynda_dmg;
    }

    public Tegevus getTegevus() {
        return tegevus;
    }

    public void setTegevus(Tegevus tegevus) {
        this.tegevus = tegevus;
    }


}
