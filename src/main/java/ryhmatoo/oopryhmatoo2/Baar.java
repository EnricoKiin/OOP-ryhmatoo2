package ryhmatoo.oopryhmatoo2;

import java.util.List;
import javafx.scene.control.TextArea;

/**
 * Algse mängu ainuke vastase tüüp.
 */
public class Baar extends Vastane {

    private TextArea teadeteLogi;

    public Baar(String nimi, int elud, double kaitseProtsent, int rynda_dmg, TextArea logi) {
        super(nimi, elud, kaitseProtsent, rynda_dmg);
        lisaRyndelaused();
        lisaKaitselaused();
        this.teadeteLogi = logi;
    }

    /**
     * Boostib ründe dmg, kuni surmani. Vt Vastase klassi, et näha kui palju
     */
    @Override
    public void ryndeBoost() {
        super.ryndeBoost();

        teadeteLogi.appendText((this.toString() + " sai turvamehe juurde. Pead ettevaatlikum olema" + "\n");
        teadeteLogi.appendText(this.toString() + " ründab tugevamalt!" + "\n");
    }

    /**
     * Lisame ründamise laused
      */
    @Override
    public void lisaRyndelaused() {
        String[] laused = {"Turva ei usu su vanust. Võtab su ID kaardi ära",
                "WC järjekorras on 5 inimest. Pead vastu pidama",
                "Baari tuleb sõber, kellele oled võlgu. Pead joogi välja tegema",
                "Jõid liiga palju, pead oksendama.",
                "Keegi müksas sind, su jook lendas maha.",
                "Tähelepanematuses varastas keegi su tupsukarbi ära",
                "Vaatasid Moodlesse, mata tunnika hinne tuli välja",

        };
        setRyndeLaused(laused);
    }

    /**
     * Lisame kaitsmise laused
     */
    @Override
    public void lisaKaitselaused() {
        String[] laused = {"Vajutasid seinapealsest lülitist kõik tuled kustu.",
                "Lõhkusid " + this.getNimi() + " akna. ",
                "Jätsid WC-s kraani jooksma.",
                "Istusid " + this.getNimi() + " tooli katki",
                "Kusesid pika järjekorra pärast " + this.getNimi() + " seinale",
                "Oksendasid baari rõdult alla, kukkus turvamehele pähe",
        };
        setKaitselaused(laused);
    }

    /**
     * Baari elude kaotamise loogika. Kasutab ülemklassi, et elusid kaotada, aga alamklassi ülekattet
     * et lauseid välja öelda
     * @param dmg -- Kuib palju elusid kaotab. Alati positiivne arv
     */
    @Override
    public void kaotaElud(int dmg) {
        if (this.getTegevus()==Tegevus.KAITSE)
            System.out.println("Turvamees märkas sind - " + this.getNimi() + " kaotas ainult " + dmg + " elu.");
        else {
            // Lausete valik listist
            List<String> kaitselaused = this.getKaitselaused();
            int lauseValik = (int) (Math.random() * kaitselaused.size());

            System.out.println(kaitselaused.get(lauseValik));
            System.out.println(this.getNimi() + " kaotas " + dmg + " elu.");
        }

        super.kaotaElud(dmg);
    }
}
