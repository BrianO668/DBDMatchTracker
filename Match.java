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

    /**
     * Match constructor with params: Used to instantiate new Match objects with relevant data. This constructor will
     * auto-increment matchID, a need that is outdated, as SQLite handles IDs now and ignores the object's matchID.
     * @param killer String name of the killer of that match.
     * @param survivor1 String name of one survivor from the match.
     * @param survivor2 String name of one survivor from the match.
     * @param survivor3 String name of one survivor from the match.
     * @param survivor4 String name of one survivor from the match.
     * @param map String name of the map the match occurred in.
     * @param wasKiller Boolean to let us know if you played as the killer or not.
     * @param escapes Int for the number of escapes that occurred that match.
     * @param disconnects Int for the number of disconnects that may have occurred that match. Survivor disconnects are counted only.
     */
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

    /**
     * Match constructor with no params: Used to instantiate a temporary Match object that will have its data overwritten, saved separately,
     * and overwritten again at various points in the program's usage.
     */
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

    /**
     * setKiller method: Sets the killer String to a String provided as a param.
     * @param killer This is the String that will become the object's killer value.
     */
    public void setKiller(String killer) {
        this.killer = killer;
    }

    /**
     * setSurvivor1 method: Sets the survivor1 String to a String provided as a param.
     * @param survivor1 This is the String that will become the object's survivor1 value.
     */
    public void setSurvivor1(String survivor1) {
        this.survivor1 = survivor1;
    }

    /**
     * setSurvivor2 method: Sets the survivor2 String to a String provided as a param.
     * @param survivor2 This is the String that will become the object's survivor2 value.
     */
    public void setSurvivor2(String survivor2) {
        this.survivor2 = survivor2;
    }

    /**
     * setSurvivor3 method: Sets the survivor3 String to a String provided as a param.
     * @param survivor3 This is the String that will become the object's survivor3 value.
     */
    public void setSurvivor3(String survivor3) {
        this.survivor3 = survivor3;
    }

    /**
     * setSurvivor4 method: Sets the survivor4 String to a String provided as a param.
     * @param survivor4 This is the String that will become the object's survivor4 value.
     */
    public void setSurvivor4(String survivor4) {
        this.survivor4 = survivor4;
    }

    /**
     * setMap method: Sets map String to a String provided as a param.
     * @param map This is the String that will become the object's map value.
     */
    public void setMap(String map) {
        this.map = map;
    }

    /**
     * setKillerBool method: Sets the wasKiller boolean to true or false depending on the bool value
     * provided by the user.
     * @param wasKiller This is the true or false value that is provided by the user.
     */
    public void setKillerBool(boolean wasKiller) {
        this.wasKiller = wasKiller;
    }

    /**
     * setEscapes method: Sets the number of escapes that occurred in the match to an int provided as a param.
     * @param escapes This is the int that will become the escapes value.
     */
    public void setEscapes(int escapes) {
        this.escapes = escapes;
    }

    /**
     * setDisconnects method: Sets the number of disconnects that occurred in the match to an int provided as a param.
     * @param disconnects This is the int that will become the disconnects value.
     */
    public void setDisconnects(int disconnects) {
        this.disconnects = disconnects;
    }
    //------END SETTERS------

    //------GETTERS------

    /**
     * getKiller method: Used to access the value of the String killer.
     * @return returns killer String.
     */
    public String getKiller() {
        return killer;
    }

    /**
     * getSurvivor1 method: Used to access the value of the String survivor1.
     * @return returns survivor1 String.
     */
    public String getSurvivor1() {
        return survivor1;
    }

    /**
     * getSurvivor2 method: Used to access the value of the String survivor2.
     * @return returns survivor2 String.
     */
    public String getSurvivor2() {
        return survivor2;
    }

    /**
     * getSurvivor3 method: Used to access the value of the String survivor3.
     * @return returns survivor3 String.
     */
    public String getSurvivor3() {
        return survivor3;
    }

    /**
     * getSurvivor4 method: Used to access the value of the String survivor4.
     * @return returns survivor4 String.
     */
    public String getSurvivor4() {
        return survivor4;
    }

    /**
     * getMap method: Used to access the value of the String map.
     * @return returns map String
     */
    public String getMap() {
        return map;
    }

    /**
     * getKillerBool method: Used to access the value of the wasKiller boolean
     * @return returns the wasKiller boolean
     */
    public boolean getKillerBool() {
        return wasKiller;
    }

    /**
     * getEscapes method: Used to access the value of the escapes int.
     * @return returns the int escapes.
     */
    public int getEscapes() {
        return escapes;
    }

    /**
     * getDisconnects method: Used to access the value of the disconnects int.
     * @return returns the int disconnects.
     */
    public int getDisconnects() {
        return disconnects;
    }
}
//------END GETTERS------
