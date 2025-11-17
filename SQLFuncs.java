import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * SQLFuncs class is the host to a variety of functions that interact with the SQLite database
 * through SQL commands.
 */
public class SQLFuncs {
    public Connection con;
    public Statement st;
    public ResultSet rs;
    Match tempMatch;

    /**
     * SQLFuncs constructor: Takes instantiates this class as an Object with a Connection attribute,
     * so that we can later perform the SQL functions provided in this class.
     * @param con A database Connection is established in another class, but passed here as a param.
     * This way we only have one database connection going on at once.
     */
    public SQLFuncs(Connection con){ //Only want to do this once
        this.con = con;
    }

    /**
     * GetData method: Performs a SELECT all query to our database.
     * @return returns a ResultSet with the data returned from the query.
     * This ResultSet is used to update the table in the GUI.
     * @throws java.sql.SQLException if a database access error occurs.
     */
    public ResultSet GetData() {
        String getQuery = "SELECT * FROM Matches"; //Returns a result set, so execute the query
        try {
            st = con.createStatement();
            rs = st.executeQuery(getQuery);
            return rs;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * DeleteData method: Performs an SQL query that deletes a row of data from the Matches table.
     * @param id The ID provided is the MatchID in the database that will be removed.
     * @return returns an int. 1 if the deletion is successful, 0 otherwise.
     * @throws java.sql.SQLException if a database access error occurs.
     */
    public int DeleteData(int id) {
        String delQuery = ("DELETE FROM Matches WHERE MatchID = " + id); //Simple delete based on ID
        String idFix = ("UPDATE Matches SET MatchID = MatchID - 1 WHERE MatchID > " + id); //Any matches with an ID greater than what was
                                                                                           //removed will have their ID updated (minus one'd)
        try {
            st = con.createStatement();
            st.executeUpdate(delQuery);
            st.executeUpdate(idFix); //Functions that do not return result sets must use executeUpdate
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * UpdateMatch method: Sends an update query to modify data of an existing row of data.
     * @param temp This param is a Match object created by the MainWindow table and passed here. This
     *             temp object's data is used to fill in the update query.
     * @param id This id int param is the MatchID of the row to be updated.
     * @return returns an int. 1 if the deletion is successful, 0 otherwise.
     * @throws java.sql.SQLException if a database access error occurs.
     */
    public int UpdateData(Match temp, int id) {
        String updateQuery = "UPDATE Matches SET Killer = ?, WasKiller = ?, Map = ?, Survivor1 = ?, Survivor2 = ?, " +
                "Survivor3 = ?, Survivor4 = ?, Disconnects = ?, Escapes = ? WHERE MatchID = ?";

        try (PreparedStatement ps = con.prepareStatement(updateQuery)) { //Prepared statements make life easier
            ps.setString(1, temp.getKiller());//Question marks are placeholders
            ps.setInt(2, Boolean.compare(temp.getKillerBool(), false));//First argument is the number of the corresponding ? in the string
            ps.setString(3, temp.getMap());
            ps.setString(4, temp.getSurvivor1());
            ps.setString(5, temp.getSurvivor2());
            ps.setString(6, temp.getSurvivor3());
            ps.setString(7, temp.getSurvivor4());
            ps.setInt(8, temp.getDisconnects());
            ps.setInt(9, temp.getEscapes());
            ps.setInt(10, id);

            ps.executeUpdate();
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * CreateData method: Creates and runs a SQL query that creates a new row of data with data passed into the method.
     * @param temp Temp Match object param is used to hold the data that fills in the query.
     * @return returns an int. 1 if the deletion is successful, 0 otherwise.
     * @throws java.sql.SQLException if a database access error occurs.
     */
    public int CreateData(Match temp) {
        String createQuery = "INSERT INTO Matches (Killer, WasKiller, Map, Survivor1, Survivor2, Survivor3, Survivor4, " +
                "Disconnects, Escapes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(createQuery)) { //Another prepared statement, takes in a Match object
            ps.setString(1, temp.getKiller());//Extracts the data, and then runs the create query
            ps.setInt(2, Boolean.compare(temp.getKillerBool(), false));
            ps.setString(3, temp.getMap());
            ps.setString(4, temp.getSurvivor1());
            ps.setString(5, temp.getSurvivor2());
            ps.setString(6, temp.getSurvivor3());
            ps.setString(7, temp.getSurvivor4());
            ps.setInt(8, temp.getDisconnects());
            ps.setInt(9, temp.getEscapes());

            ps.executeUpdate();
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * AddFromFile method: This method accesses a preemptively formatted file containing Match object data,
     * throws that data into a temp Match objects, and uses them to fill in INSERT SQL queries to add rows.
     * @param file a File object is selected from the GUI and passed here for accessing.
     * @return returns the number of rows added if successful. Returns a -1 if an exception occurs due to incorrect formatting.
     * Returns a 0 no matches are added and no exception occurred. Returns a -2 if a separate error occurs.
     * @throws java.sql.SQLException if a database access error occurs.
     * @throws Exception if the scanner is closed or if the file is formatted incorrectly.
     */
    public int AddFromFile(File file) { //A hybrid of CreateData and my old AddFromFile function
        String uploadQuery = "INSERT INTO Matches (Killer, WasKiller, Map, Survivor1, Survivor2, Survivor3, Survivor4, " +
                "Disconnects, Escapes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Scanner fileScanner = new Scanner(file);
            int counter = 0; //To let the user know how many were added in the end

            while (fileScanner.hasNextLine()) {
                try {
                    String line = fileScanner.nextLine().trim(); //Remove leading and trailing whitespace
                    String[] lineData = line.split("%"); //Our files will be formatted with the % as a delimiter, split each line of data with it

                    String killer = lineData[0];
                    String survivor1 = lineData[1];
                    String survivor2 = lineData[2];
                    String survivor3 = lineData[3];
                    String survivor4 = lineData[4];
                    String map = lineData[5];
                    boolean wasKiller = Boolean.parseBoolean(lineData[6]);
                    int escapes = Integer.parseInt(lineData[7]);
                    int disconnects = Integer.parseInt(lineData[8]);

                    tempMatch = new Match(killer, survivor1, survivor2, survivor3, survivor4, map, wasKiller, escapes, disconnects);

                    try (PreparedStatement ps = con.prepareStatement(uploadQuery)) { //Like CreateData, use a prepared statement for each line read from the file
                        ps.setString(1, tempMatch.getKiller());
                        ps.setInt(2, Boolean.compare(tempMatch.getKillerBool(), false));
                        ps.setString(3, tempMatch.getMap());
                        ps.setString(4, tempMatch.getSurvivor1());
                        ps.setString(5, tempMatch.getSurvivor2());
                        ps.setString(6, tempMatch.getSurvivor3());
                        ps.setString(7, tempMatch.getSurvivor4());
                        ps.setInt(8, tempMatch.getDisconnects());
                        ps.setInt(9, tempMatch.getEscapes());

                        ps.executeUpdate();

                        counter++;
                    }
                } catch (Exception e) {
                    System.out.println("\nFile format error. Please check formatting and try again.");
                    return -1;
                }
            }

            if (counter > 0) {
                System.out.println("\nSuccessfully added " + counter + " match(es) from file!\n");
                return counter;
            } else {
                System.out.println("\nNo matches added!\n");
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }
}
