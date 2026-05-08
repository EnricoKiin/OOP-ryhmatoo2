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
    private TextArea teadeteLogi;
    private Tegevus tudengiOtsus;

    public void setTudengiOtsus(Tegevus tudengiOtsus) {
        this.tudengiOtsus = tudengiOtsus;
    }

    public Mäng(Tudeng tudeng, Vastane vastane, TextArea logi) {
        this.tudeng = tudeng;
        this.vastane = vastane;
        this.teadeteLogi = logi;
    }

    public void setVastane(Vastane vastane) {
        this.vastane = vastane;
    }

    public Vastane getVastane() {
        return vastane;
    }

    public Mäng(String tudengiNimi) {
        this.tudeng = new Tudeng(tudengiNimi,20, 0.5, 5);
        this.vastased = new ArrayList<>();
        looVastased();

        this.vastane = vastased.getFirst();
    }

    private void looVastased() {
        Baar baar1 = looBaar("Atso", 1);
        Baar baar2 = looBaar("Seik", 2);
        Baar baar3 = looBaar("Möku", 3);
        vastased.add(baar1);
        vastased.add(baar2);
        vastased.add(baar3);
    }

    /**
     * Lahendab kõik elu probleemid ainult 20 sout'ga
     * Teeb nagu nimi ütleb
     */
    public void puhastaEkraan() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    /**
     * Prindib pärast igat lahingu etappi hetkese mängu seisu meetodis mängi()
     */
    public void mänguSeis() {
        puhastaEkraan();
        Platform.runLater(() -> teadeteLogi.appendText(vastane.toString() + ": " + vastane.getElud() + "hp" + "\n"));
        Platform.runLater(() -> teadeteLogi.appendText(tudeng.toString() + ": " + tudeng.getElud() + "hp" + "\n"));
    }

    /**
     * Paneb mängu seisma kindlaks määratud ajaks, et jõuaks teksti lugeda
     * @param aeg -- millisekundites antud aeg
     */
    public void maga(int aeg) {
        try {
            Thread.sleep(aeg);
        }
        catch (InterruptedException ignored) {}
    }

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
     * Peameetod, mis tegeleb kogu mängu loogikaga
     * Peamine flow on:
     * 1. Vastase tegevuse välja loosimine
     * 2. Tudeng otsustab oma tegevuse terminali kirjutades
     * 3. lahing tehakse läbi
     * 4. Kontrollitakse tegelaste elus olekut
     * 5. Kui tudeng sureb, siis kaotus(). Kui võidab siis voit()
     */
    public void mängi() {
        int TudengiOtsus;
        int vastaneTegevus;


        // Tegevuste valimise ja lahingute tsükkel, mis kestab kuni keegi sureb
        while (vastane.onElus() && tudeng.onElus()) {
            puhastaEkraan();
            mänguSeis();

            // Määrab vastavalt otsusele tegevuse
            switch (tudengiOtsus) {
                case Tegevus.RYNDA:
                    tudeng.setTegevus(Tegevus.RYNDA);
                    break;
                case Tegevus.KAITSE:
                    tudeng.setTegevus(Tegevus.KAITSE);
                    break;
                case Tegevus.RAVI:
                    tudeng.setTegevus(Tegevus.RAVI);
                    break;
            }
            // Lahing ja ootamine, et näha lauseid
            lahing();
            maga(5000);
        }

        // Kontrollimine, et kes suri
        if (!tudeng.onElus()) {
            kaotus();
        }
        else {
            voit();
        }
        maga(5000);
        puhastaEkraan();


    }

    /**
     * Meetod, mis kuvab tudengi saadud punktide arvu pärast surma. Kogu mängu lõpp
     */
    public void kaotus() {
        puhastaEkraan();
        Platform.runLater(() -> teadeteLogi.appendText("Surid ära!\n"));
        Platform.runLater(() -> teadeteLogi.appendText("Lõpetasid: " + tudeng.getPunkte() + " punktiga" + "\n"));
    }

    /**
     * Meetod, mis aktiveerub, kui tudeng tappis vastase.
     * Ütleb palju punkte tudeng sai ja kasutab vastavaid meetode, et neid lisada
     */
    public void voit() {
        puhastaEkraan();
        int vastasePunktid = vastane.getPunkteVaart();
        Platform.runLater(() -> teadeteLogi.appendText("Tapsid ära " + vastane.toString() + "\n"));
        Platform.runLater(() -> teadeteLogi.appendText("Teenisid " + vastasePunktid + " punkti!" + "\n"));
        tudeng.lisaPunkte(vastasePunktid);
    }

    /**
     * Korraldab Tudengi ja Vastase vahel lahingut
     * Eelistab Tudengi tegevust Vastase omale
     * @return Tagastab Vastava klassi isendi, kes ära suri. Muidu null. Hetkel pole kasutatud tagastusväärtust
     */
    public Tegelane lahing () {
        Tegevus tudengiOtsus = tudeng.getTegevus();
        Tegevus vastaseOtsus = vastane.getTegevus();
        puhastaEkraan();

        // Tudengil on ründamises eelis
        if (tudengiOtsus == Tegevus.RYNDA) {
            int tudengATK = tudeng.getRynda_dmg();
            if (vastaseOtsus == Tegevus.KAITSE) {
                tudengATK -=  (int)(tudengATK * vastane.getKaitseProtsent());
            }
            vastane.kaotaElud(tudengATK);
            if (!vastane.onElus()) {
                return vastane;
            }
        }
        // Tudeng saab alati elusid endale juurde anda enne kui vastas saab rünnata
        if (tudengiOtsus == Tegevus.RAVI) {
            tudeng.saaStippi();
        }

        // Vastase ründeskeem
        if (vastaseOtsus == Tegevus.RYNDA) {
            int vastaseATK = vastane.getRynda_dmg();
            if (tudengiOtsus == Tegevus.KAITSE) {
                vastaseATK -= (int)(vastaseATK * tudeng.getKaitseProtsent());
            }
            System.out.println();
            tudeng.kaotaElud(vastaseATK, vastane);
            if (!tudeng.onElus()) {
                return tudeng;
            }

        }
        // Vastane boostib enda ATK
        if (vastaseOtsus == Tegevus.BOOST) {
            System.out.println();
            vastane.ryndeBoost();
        }

        // Olukord kui mõlemad kaitsevad
        if (vastaseOtsus == Tegevus.KAITSE && tudengiOtsus == Tegevus.KAITSE) {
            Platform.runLater(() -> teadeteLogi.appendText("Kumbki ei julgenud midagi teha. Niisama jõllitasite üksteist." + "\n"));
        }

        // null kui keegi ei surnud
        return null;


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
            RyndaDmg: 3-5

        Medium:
            Elud: 15-23
            KaitseProtsent: 0.3-0.5
            RyndaDmg: 4-6

         Hard:
            Elud: 18-27
            KaitseProtsent: 0.4-0.6
            RyndaDmg: 5-7
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

}
