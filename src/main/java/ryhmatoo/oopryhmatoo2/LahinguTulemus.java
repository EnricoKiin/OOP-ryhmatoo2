package ryhmatoo.oopryhmatoo2;

public class LahinguTulemus {
    private Tudeng tudeng;
    private Vastane vastane;
    private Tegelane surnud;
    private StringBuilder tudengLause;
    private StringBuilder vastaseLause;

    /**
     * Loob esialgse paketti moodi klassi
     * @param tudeng -- Kes on tudeng
     * @param vastane -- Kes on vastane
     * Meetod on ainult selleks, et mängu loogika klassist Mäng saaks edastada infot GUI-le ilma,
     * et nad teaksid üksteisest eriti palju. Aitab hoida loogika ja GUI lahus.
     */
    public LahinguTulemus(Tudeng tudeng, Vastane vastane) {
        this.tudeng = tudeng;
        this.vastane = vastane;
        this.surnud = null;
        this.tudengLause = new StringBuilder(100);
        this.vastaseLause = new StringBuilder(100);
    }

    public Tudeng getTudeng() {
        return tudeng;
    }

    public Vastane getVastane() {
        return vastane;
    }

    public Tegelane getSurnud() {
        return surnud;
    }

    public StringBuilder getTudengLause() {
        return tudengLause;
    }

    public StringBuilder getVastaseLause() {
        return vastaseLause;
    }

    public void setSurnud(Tegelane surnud) {
        this.surnud = surnud;
    }

    public void lisaTudengLause(String tudengLause) {
        this.tudengLause.append(tudengLause + "\n");
    }

    public void lisaVastaseLause(String vastaseLause) {
        this.vastaseLause.append(vastaseLause + "\n");
    }
}
