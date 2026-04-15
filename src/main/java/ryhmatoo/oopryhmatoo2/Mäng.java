package ryhmatoo.oopryhmatoo2;

import java.util.Scanner;
import javafx.scene.control.TextArea;

/**
 * Peamine klass, mis tegelb kogu tegelaste vahelise kokkupuudetega ja mängu loogika
 * Vaõtab parameetriteks Vastase ja Tudengi
 */
public class Mäng {
    private Tudeng tudeng;
    private Vastane vastane;
    private TextArea teadeteLogi;

    public Mäng(Tudeng tudeng, Vastane vastane) {
        this.tudeng = tudeng;
        this.vastane = vastane;
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
        System.out.println(vastane.toString() + ": " + vastane.getElud() + "hp");
        System.out.println(tudeng.toString() + ": " + tudeng.getElud() + "hp");
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
        Scanner sc = new Scanner(System.in);
        int TudengiOtsus;
        int vastaneTegevus;


        // Tegevuste valimise ja lahingute tsükkel, mis kestab kuni keegi sureb
        while (vastane.onElus() && tudeng.onElus()) {
            puhastaEkraan();
            mänguSeis();

            // Vaste tegevuse välja loosimine
            // Tahame ründamist rohkem, et mängija ei saaks end liiga mugavalt tunda
            // Hetkel 60% Ründa, 30% kaitse, 10 boost
            vastaneTegevus = (int) (Math.random() * 100);

            if (vastaneTegevus <= 59) {
                vastane.setTegevus(Tegevus.RYNDA);
            } else if (vastaneTegevus <= 89) {
                vastane.setTegevus(Tegevus.KAITSE);
            } else {
                vastane.setTegevus(Tegevus.BOOST);
            }

            // Tudengi tegevuse määramine
            // Tegeleb erinditega ja ei lase valet väärtust sisestada
            while (true) {
                System.out.println("Vali tegevus:");
                System.out.println("1 - ründa    2 - kaitse    3 - saa stippi");
                if (sc.hasNextInt()) {
                    TudengiOtsus = sc.nextInt();
                    if (TudengiOtsus >=1 && TudengiOtsus <= 3) {
                        break;
                    } else {
                        System.out.println("Vale sisend!");
                    }
                } else {
                    System.out.println("Vale sisend!");
                    sc.next();
                }
            }

            // Määrab vastavalt otsusele tegevuse
            switch (TudengiOtsus) {
                case 1:
                    tudeng.setTegevus(Tegevus.RYNDA);
                    break;
                case 2:
                    tudeng.setTegevus(Tegevus.KAITSE);
                    break;
                case 3:
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
        System.out.println("Surid ära!");
        System.out.println("Lõpetasid: " + tudeng.getPunkte() + " punktiga");
    }

    /**
     * Meetod, mis aktiveerub, kui tudeng tappis vastase.
     * Ütleb palju punkte tudeng sai ja kasutab vastavaid meetode, et neid lisada
     */
    public void voit() {
        puhastaEkraan();
        int vastasePunktid = vastane.getPunkteVaart();
        System.out.println("Tapsid ära " + vastane.toString());
        System.out.println("Teenisid " + vastasePunktid + " punkti!");
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
            System.out.println("Kumbki ei julgenud midagi teha. Niisama jõllitasite üksteist.");
        }

        // null kui keegi ei surnud
        return null;


    }


}
