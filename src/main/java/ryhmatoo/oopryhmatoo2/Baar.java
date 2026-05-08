package ryhmatoo.oopryhmatoo2;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Algse mängu ainuke vastase tüüp.
 */
public class Baar extends Vastane {

    public Baar(String nimi, int elud, double kaitseProtsent, int rynda_dmg) {
        super(nimi, elud, kaitseProtsent, rynda_dmg);
        lisaRyndelaused();
        lisaKaitselaused();
    }

    /**
     * Boostib ründe dmg, kuni surmani. Vt Vastase klassi, et näha kui palju
     */
    @Override
    public void ryndeBoost(LahinguTulemus info) {
        super.ryndeBoost(info);

        info.lisaVastaseLause(this.toString() + " sai turvamehe juurde. Pead ettevaatlikum olema");
        info.lisaVastaseLause(this.toString() + " ründab tugevamalt!");
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
     * @param info -- Vaike klass, mis teeskleb end pakettiks ja liigutab infot ringi.
     */
    @Override
    public void kaotaElud(int dmg, LahinguTulemus info) {
        if (this.getTegevus()==Tegevus.KAITSE)
            info.lisaVastaseLause("Turvamees märkas sind - " + this.getNimi() + " kaotas ainult " + dmg + " elu.");
        else {
            // Lausete valik listist
            List<String> kaitselaused = this.getKaitselaused();
            int lauseValik = (int) (Math.random() * kaitselaused.size());

            info.lisaVastaseLause(kaitselaused.get(lauseValik));
        }

        super.kaotaElud(dmg, info);
    }
}
