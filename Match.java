public class Match {
    private static int matchAdder = 1;
    private int matchNumber;
    private String killer;
    private String survivor1;
    private String survivor2;
    private String survivor3;
    private String survivor4;
    private String map;
    private boolean wasKiller;
    private int escapes;
    private int disconnects;

    public Match(String killer, String survivor1, String survivor2, String survivor3, String survivor4,
                 String map, boolean wasKiller, int escapes, int disconnects) {
        this.matchNumber = matchAdder++; //Set ID then adds one to the adder for the next match. No two alike, no editing of match numbers
        this.killer = killer;
        this.survivor1 = survivor1;
        this.survivor2 = survivor2;
        this.survivor3 = survivor3;
        this.survivor4 = survivor4;
        this.map = map;
        this.wasKiller = wasKiller;
        this.escapes = escapes;
        this.disconnects = disconnects;
    }

    public Match() { //For temporary data only
        this.matchNumber = 0;
        this.killer = "";
        this.survivor1 = "";
        this.survivor2 = "";
        this.survivor3 = "";
        this.survivor4 = "";
        this.map = "";
        this.wasKiller = false;
        this.escapes = 0;
        this.disconnects = 0;
    }

    //------SETTERS------
    public void setKiller(String killer) {
        this.killer = killer;
    }

    public void setSurvivor1(String survivor1) {
        this.survivor1 = survivor1;
    }

    public void setSurvivor2(String survivor2) {
        this.survivor2 = survivor2;
    }

    public void setSurvivor3(String survivor3) {
        this.survivor3 = survivor3;
    }

    public void setSurvivor4(String survivor4) {
        this.survivor4 = survivor4;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setKillerBool(boolean wasKiller) {
        this.wasKiller = wasKiller;
    }

    public void setEscapes(int escapes) {
        this.escapes = escapes;
    }

    public void setDisconnects(int disconnects) {
        this.disconnects = disconnects;
    }

    public void setMatchID(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public void setMatchAdder() {
        matchAdder--;
    }
    //------END SETTERS------

    //------GETTERS------
    public String getKiller() {
        return killer;
    }

    public String getSurvivor1() {
        return survivor1;
    }

    public String getSurvivor2() {
        return survivor2;
    }

    public String getSurvivor3() {
        return survivor3;
    }

    public String getSurvivor4() {
        return survivor4;
    }

    public String getMap() {
        return map;
    }

    public boolean getKillerBool() {
        return wasKiller;
    }

    public int getEscapes() {
        return escapes;
    }

    public int getDisconnects() {
        return disconnects;
    }

    public int getMatchNumber() {
        return matchNumber;
    }
}
//------END GETTERS------
