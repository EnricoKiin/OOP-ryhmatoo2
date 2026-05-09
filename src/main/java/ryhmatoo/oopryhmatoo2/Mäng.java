package ryhmatoo.oopryhmatoo2;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Peamine klass, mis tegelb kogu tegelaste vahelise kokkupuudetega ja mängu loogika
 * Vaõtab parameetriteks Vastase ja Tudengi
 */
public class Mäng {
    private Tudeng tudeng;
    private ArrayList<Vastane> vastased;
    private Vastane vastane;
    private int mitmesVastane;

    /**
     * Määrab tudengile otsuse. Kutsutav väljaspoolt nagu GUI-st, et tagada nuppude ja loogika vastavuse
     * @param tudengiOtsus -- Mis tegevust tudeng teeb
     */
    public void setTudengiOtsus(Tegevus tudengiOtsus) {
        tudeng.setTegevus(tudengiOtsus);
    }

    public Vastane getVastane() {
        return vastane;
    }

    public int getMitmesVastane() {
        return mitmesVastane;
    }

    public ArrayList<Vastane> getVastased() {
        return vastased;
    }

    /**
     * Mängu loogika konstruktor tudengi nime alusel
     * Initsialiseerib vastased
     * @param tudengiNimi -- Tudengi nimi
     */
    public Mäng(String tudengiNimi) {
        this.tudeng = new Tudeng(tudengiNimi,20, 0.5, 5);
        this.vastased = new ArrayList<>();
        looVastased();

        this.vastane = vastased.getFirst();
        this.mitmesVastane = 1;
    }

    /**
     * Loob vastased, kelle vastu hakkame võitlema
     */
    private void looVastased() {
        Baar baar1 = looBaar("Atso", 1);
        Baar baar2 = looBaar("Seik", 2);
        Baar baar3 = looBaar("Möku", 3);
        vastased.add(baar1);
        vastased.add(baar2);
        vastased.add(baar3);
    }

    public Tudeng getTudeng() {
        return tudeng;
    }

    /**
     * Otsutab vastase tegevuse ära juhu arvu abil
     */
    public void otsustaVastaseTegevus() {
        // Vaste tegevuse välja loosimine
        // Tahame ründamist rohkem, et mängija ei saaks end liiga mugavalt tunda
        // Hetkel 60% Ründa, 30% kaitse, 10 boost
        int vastaneTegevus = (int) (Math.random() * 100);

        if (vastaneTegevus <= 59) {
            vastane.setTegevus(Tegevus.RYNDA);
        } else if (vastaneTegevus <= 89) {
            vastane.setTegevus(Tegevus.KAITSE);
        } else {
            vastane.setTegevus(Tegevus.BOOST);
        }
    }


    /**
     * Korraldab Tudengi ja Vastase vahel lahingut
     * Eelistab Tudengi tegevust Vastase omale
     * @return Paketti moodi klassi LahinguTulemus, kus on salvestad hetke lahingu tulemuse info ja kes suri koos lausetega
     */
    public LahinguTulemus lahing () {
        Tegevus tudengiOtsus = tudeng.getTegevus();
        Tegevus vastaseOtsus = vastane.getTegevus();

        LahinguTulemus info = new LahinguTulemus(tudeng, vastane);


        // Tudengil on ründamises eelis
        if (tudengiOtsus == Tegevus.RYNDA) {
            int tudengATK = tudeng.getRynda_dmg();
            if (vastaseOtsus == Tegevus.KAITSE) {
                tudengATK -=  (int)(tudengATK * vastane.getKaitseProtsent());
            }
            vastane.kaotaElud(tudengATK, info);

            //Kui vastane suri ära pole vaja jätkata
            if (!vastane.onElus()) {
                info.setSurnud(vastane);
                return info;
            }
        }


        // Tudeng saab alati elusid endale juurde anda enne kui vastas saab rünnata
        if (tudengiOtsus == Tegevus.RAVI) {
            tudeng.saaStippi(info);
        }

        // Vastase ründeskeem
        if (vastaseOtsus == Tegevus.RYNDA) {
            int vastaseATK = vastane.getRynda_dmg();
            if (tudengiOtsus == Tegevus.KAITSE) {
                vastaseATK -= (int)(vastaseATK * tudeng.getKaitseProtsent());
            }
            tudeng.kaotaElud(vastaseATK, vastane, info);

            // Kui tudeng suri ära, siis pole vaja rohkem kontrollida
            if (!tudeng.onElus()) {
                info.setSurnud(tudeng);
                return info;
            }

        }
        // Vastane boostib enda ATK
        if (vastaseOtsus == Tegevus.BOOST) {
            System.out.println();
            vastane.ryndeBoost(info);
        }

        // Olukord kui mõlemad kaitsevad
        if (vastaseOtsus == Tegevus.KAITSE && tudengiOtsus == Tegevus.KAITSE) {
            info.lisaTudengLause("Kumbki ei julgenud midagi teha. Niisama jõllitasite üksteist.");
        }

        return info;


    }


    /**
     * Abifunktsioon, millega loome Baar vastased. Saab valida raskustaseme
     * @param nimi -- Baari nimi
     * @param raskusTase -- Raskustase. 1 kuni 3 ehk Easy, Medium, Hard
     * @return Tagastab Baari isendi
     */
    public static Baar looBaar(String nimi, int raskusTase) {
        /*
        Easy:
            Elud: 12-17
            KaitseProtsent: 0.1-0.3
            RyndaDmg: 3-4

        Medium:
            Elud: 15-23
            KaitseProtsent: 0.3-0.5
            RyndaDmg: 4-5

         Hard:
            Elud: 18-27
            KaitseProtsent: 0.4-0.6
            RyndaDmg: 5-6
         */


        int elud=0;
        double kaitseProtsent=0.0;
        int rynda_dmg=0;

        switch (raskusTase) {

            // Easy
            case 1:
                elud = (int)(Math.random() * 6) + 12;
                kaitseProtsent = Math.random() * 0.2 + 0.1;
                rynda_dmg = (int)(Math.random() * 2) + 3;
                break;

            // Medium
            case 2:
                elud = (int) (Math.random() * 8) + 15;
                kaitseProtsent = Math.random() * 0.2 + 0.3;
                rynda_dmg = (int) (Math.random() * 2) + 4;
                break;

            // Hard
            case 3:
                elud = (int) (Math.random() * 9) + 18;
                kaitseProtsent = Math.random() * 0.2 + 0.4;
                rynda_dmg = (int) (Math.random() * 2) + 5;
                break;

        }

        Baar bar = new Baar(nimi, elud, kaitseProtsent, rynda_dmg);
        return bar;
    }

    /**
     * Vahetab vastase järgmise peale, kui veel polnud lõplik võit
     */
    public void vahetaVastane() {
        mitmesVastane++;

        // -1, sest mitmes algas 1st
        vastane = vastased.get(mitmesVastane -1);
    }


    /**
     * Lisab mängijale vastase punkte
     * @return Tagastab punktide arvu GUI jaoks
     */
    public int voit() {
        int vastasePunktid = vastane.getPunkteVaart();
        tudeng.lisaPunkte(vastasePunktid);
        return vastasePunktid;
    }
}
