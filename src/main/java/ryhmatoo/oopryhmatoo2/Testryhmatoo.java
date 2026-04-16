package ryhmatoo.oopryhmatoo2;

import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.TextArea;

/**
 * Peaklass
 */
public class Testryhmatoo {

    private TextArea teadeteLogi;

    /**
     * Küsib mängijalt nime. Default Piro Kunn
     * @return Tagastab Tudengi nime
     */
    public static String otsustaNimi() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Vali endale nimi (Default: Piro Kunn; Vajuta ENTER): ");
        String nimi = sc.nextLine();
        if (nimi.isEmpty()) return "Piro Kunn";
        else return nimi;
    }


    /**
     * Tekitame Tudengi ja Vastase ning mängime surmani.
     */
    public static void main() {
        String nimi = otsustaNimi();

        TextArea teadeteLogi = new TextArea();

        Tudeng tudeng = new Tudeng(nimi, 20, 0.5, 5, teadeteLogi);

        // Vastaste loomine abifunktsiooniga
        ArrayList<Vastane> vastased = new ArrayList<>();
        Baar baar1 = looBaar("Möku", 1);
        Baar baar2 = looBaar("Atso", 2);
        Baar baar3 = looBaar("Seik", 3);
        vastased.add(baar1);
        vastased.add(baar2);
        vastased.add(baar3);

        // Loopime kõik vastased läbi, kuni keegi sureb.
        for (Vastane vastane : vastased) {
            Mäng mäng = new Mäng(tudeng, vastane, teadeteLogi);
            mäng.mängi();

            // Lisa shade, kui mängija ei saa hakkama
            if (!tudeng.onElus()) {
                teadeteLogi.appendText("Kaotasid mängu. Get good kid\n");
                break;
            }
        }
        if (tudeng.onElus()) {
            teadeteLogi.appendText("Lõpetasid mängu " + tudeng.getPunkte() + " punktiga!" + "\n");
        }
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
