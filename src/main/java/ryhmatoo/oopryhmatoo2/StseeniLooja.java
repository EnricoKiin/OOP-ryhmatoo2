package ryhmatoo.oopryhmatoo2;

import javafx.scene.Scene;

public interface StseeniLooja {

    /**
     * Üks ühine funktsioon, mida kõik stseeni loovad klassid kasutavad, et oleks lihtsam hallata
     * @return stseen, mida seansihaldur saab hallata
     */
    public Scene looStseen();
}
