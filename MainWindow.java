import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * MainWindow class: The bulk of this program. Contains all the panels, textboxes, buttons, and tables needed to
 * display our SQLite data. This MainWindow class is essentially the entire GUI instantiated in the main method
 * of the Main class. Extends JFrame for initializations.
 */
public class MainWindow extends JFrame {
    public SQLFuncs sql;
    public ResultSet results;

    public JTabbedPane tabbedPane = new JTabbedPane(); //For tabs in main window
    public JPanel connectPanel = new JPanel(); //Panel become their own tabs
    public JPanel connectNest = new JPanel(); //Go a layer deeper for layout
    public JPanel dataPanel = new JPanel();
    public JPanel dataNestL = new JPanel();//Nested layers for layout purposes again
    public JPanel dataNestR = new JPanel();
    public JPanel buttonNestL = new JPanel(); //3 Nests deep for layout
    public JPanel buttonNestR = new JPanel();
    public JTable dataTable; //Displays tables nicely
    public DefaultTableModel tableModel;
    public JLabel connectionStatus;
    public JTextArea connectionTextArea;
    public JButton connectButton;

    //Buttons
    public JButton createButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton winRateButton;
    public JButton uploadButton;

    //Labels
    public JLabel matchID;
    public JLabel killer;
    public JLabel wasKiller;
    public JLabel map;
    public JLabel surv1;
    public JLabel surv2;
    public JLabel surv3;
    public JLabel surv4;
    public JLabel disconnects;
    public JLabel escapes;

    //Text Fields
    public JTextField matchIDTF;
    public JTextField killerTF;
    public JTextField wasKillerTF;
    public JTextField mapTF;
    public JTextField surv1TF;
    public JTextField surv2TF;
    public JTextField surv3TF;
    public JTextField surv4TF;
    public JTextField disconnectsTF;
    public JTextField escapesTF;

    //Set up our temp Match object for usages that won't continue to affect our MatchAdder
    Match tempy = new Match();

    /**
     * MainWindow constructor: Allows us to call the parent class's initializer and run our Init method which sets
     * our GUI up.
     */
    public MainWindow() {
        super();
        init();
    }

    /**
     * Init method: Contains everything needed for GUI initialization.
     * All object placements such as textboxes, labels, and buttons.
     * Also contains action listeners for each button.
     * Packed in a specific order to assemble the window in the way we see fit.
     */
    private void init() {
        JFrame.setDefaultLookAndFeelDecorated(true); //Generic window setup
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Dead by Daylight Match Tracker");
        this.setLayout(new BorderLayout());

        tabbedPane.addTab("Connection", connectPanel); //Add containers/layouts to our tabs
        tabbedPane.addTab("Data", dataPanel);
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        String[] columnNames = {"Match ID", "Killer", "Was Killer?", "Map", "Survivor 1",  "Survivor 2", "Survivor 3", "Survivor 4", "Disconnects", "Escapes"};
        tableModel = new DefaultTableModel(columnNames, 0);//Name our table columns

        //Label Setup
        matchID = new JLabel("Match ID");
        matchID.setForeground(Color.RED);
        killer = new JLabel("Killer");
        killer.setForeground(Color.RED);
        wasKiller = new JLabel("Played Killer? (true/false)");
        wasKiller.setForeground(Color.RED);
        map = new JLabel("Map");
        map.setForeground(Color.RED);
        surv1 = new JLabel("Survivor 1");
        surv1.setForeground(Color.RED);
        surv2 = new JLabel("Survivor 2");
        surv2.setForeground(Color.RED);
        surv3 = new JLabel("Survivor 3");
        surv3.setForeground(Color.RED);
        surv4 = new JLabel("Survivor 4");
        surv4.setForeground(Color.RED);
        disconnects = new JLabel("Disconnects");
        disconnects.setForeground(Color.RED);
        escapes = new JLabel("Escapes");
        escapes.setForeground(Color.RED);

        //Button Setup
        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        winRateButton = new JButton("Win Rate");
        uploadButton = new JButton("Upload");

        //Text Field Setup
        matchIDTF = new JTextField();
        matchIDTF.setEditable(false);
        killerTF = new JTextField();
        killerTF.setEditable(true);
        wasKillerTF = new JTextField();
        wasKillerTF.setEditable(true);
        mapTF = new JTextField();
        mapTF.setEditable(true);
        surv1TF = new JTextField();
        surv1TF.setEditable(true);
        surv2TF = new JTextField();
        surv2TF.setEditable(true);
        surv3TF = new JTextField();
        surv3TF.setEditable(true);
        surv4TF = new JTextField();
        surv4TF.setEditable(true);
        disconnectsTF = new JTextField();
        disconnectsTF.setEditable(true);
        escapesTF = new JTextField();
        escapesTF.setEditable(true);

        connectPanel.setLayout(new BorderLayout());//Add our containers and nested containers with their associated layouts
        connectNest.setLayout(new FlowLayout());
        connectionStatus = new JLabel("Connection Status:");
        connectNest.add(connectionStatus);
        connectionTextArea = new JTextArea("Disconnected");
        connectionTextArea.setEditable(false);
        connectNest.add(connectionTextArea);
        connectButton = new JButton("Connect");
        connectNest.add(connectButton);
        connectPanel.add(connectNest, BorderLayout.CENTER);

        dataPanel.setLayout(new GridLayout(1, 2)); //Same as above but for tab 2
        dataNestL.setLayout(new GridLayout(11, 2));
        dataNestL.setOpaque(true);
        buttonNestL.setLayout(new FlowLayout());
        buttonNestL.setOpaque(true);
        buttonNestR.setLayout(new FlowLayout());
        buttonNestR.setOpaque(true);
        dataNestR.setLayout(new BorderLayout());

        //Add Buttons to Nests
        buttonNestL.add(createButton);
        buttonNestL.add(updateButton);
        buttonNestL.add(uploadButton);
        buttonNestR.add(deleteButton);
        buttonNestR.add(winRateButton);

        //Insert Labels then Text Field right after
        dataNestL.add(matchID);
        dataNestL.add(matchIDTF);
        dataNestL.add(killer);
        dataNestL.add(killerTF);
        dataNestL.add(wasKiller);
        dataNestL.add(wasKillerTF);
        dataNestL.add(map);
        dataNestL.add(mapTF);
        dataNestL.add(surv1);
        dataNestL.add(surv1TF);
        dataNestL.add(surv2);
        dataNestL.add(surv2TF);
        dataNestL.add(surv3);
        dataNestL.add(surv3TF);
        dataNestL.add(surv4);
        dataNestL.add(surv4TF);
        dataNestL.add(disconnects);
        dataNestL.add(disconnectsTF);
        dataNestL.add(escapes);
        dataNestL.add(escapesTF);
        dataNestL.add(buttonNestL); //Nest the button nests in the nest
        dataNestL.add(buttonNestR);

        dataTable = new JTable(tableModel);
        dataNestR.add(new JScrollPane(dataTable), BorderLayout.CENTER); //makes things scrollable
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        dataTable.setFillsViewportHeight(true);


        dataPanel.add(new JScrollPane(dataNestL));
        dataPanel.add(dataNestR);

        /*
        *
        *
        * ----------LISTENERS----------
        *
        *
         */

        //Table Listener Setup
        dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int rowSelect = dataTable.getSelectedRow();
                if (rowSelect >= 0) { //During a deletion, getSelectedRow can return -1, this prevents that
                    try { //Update our TextFields to whatever Match object is selected
                        matchIDTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 0).toString());
                        killerTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 1).toString());
                        wasKillerTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 2).toString());
                        mapTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 3).toString());
                        surv1TF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 4).toString());
                        surv2TF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 5).toString());
                        surv3TF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 6).toString());
                        surv4TF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 7).toString());
                        disconnectsTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 8).toString());
                        escapesTF.setText(dataTable.getValueAt(dataTable.getSelectedRow(), 9).toString());
                    } catch (Exception ex) {
                        System.out.println("Table Listener Error");
                    }
                }
            }
        });

        //Connect Button Listener Setup
        connectButton.addActionListener(new ActionListener() {
            /**
             * Connect Button Action: this method gets passed an action (the button being clicked),
             * and pops up a file chooser, so that the user may select a database to connect to.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String dbPath = selectedFile.getAbsolutePath(); System.out.println(dbPath);

                    try {
                        Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbPath); //Try to connect to selected path

                        try (Statement st = con.createStatement()) {
                            st.executeQuery("select * from Matches"); //Simple query to verify connection
                            sql = new SQLFuncs(con); //Then set up our objects and data

                            results = sql.GetData();

                            UpdateTableSQL(results);

                            System.out.println("Connected to database successfully"); //Visible feedback of established connection
                            connectionTextArea.setText("Connected");
                            connectionTextArea.setForeground(Color.green);
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("Error connecting to database");
                        System.out.println("Database Connection Error");
                    }
                }
            }
        });

        //Create Button Listener Setup
        createButton.addActionListener(new ActionListener() {
            /**
             * Create Button Action: this method gets passed an action (the button being clicked),
             * and the runs the necessary SQL commands for creating data if the database is connected.
             * If not, an error window will pop up.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (!connectionTextArea.getText().equals("Connected")) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No database connected!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (FieldCheck() == -1) { //Check if data is valid before running our ManualAdd function
                    JOptionPane.showMessageDialog(MainWindow.this, "Invalid Data Entered! Please ensure" +
                                    " all fields are filled and escapes and disconnects are between 0 and 4!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    GatherInfo();//Get our tempy set up
                    try {
                        sql.CreateData(tempy);//Feed it into our SQLFuncs
                        results = sql.GetData();//Refresh results
                        UpdateTableSQL(results);//Update the visible table
                        ClearFields();
                    }
                    catch (Exception ex) {
                        System.out.println("Database Connection Error");
                    }
                }
            }
        });

        //Update Button Listener Setup
        updateButton.addActionListener(new ActionListener() {
            /**
             * Update Button Action: this method gets passed an action (the button being clicked),
             * and the runs the necessary SQL commands for updating data of the selected row.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (!connectionTextArea.getText().equals("Connected")) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No database connected!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    try {
                        results = sql.GetData();
                        if (!results.isBeforeFirst()) { //isBeforeFirst() returns false if there are no rows or cursor is not sitting before first entry
                            JOptionPane.showMessageDialog(MainWindow.this, "No data in database!",
                                    "Empty Database!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            if (FieldCheck() != -1) { //Check that info is entered properly before running the EditData function
                                GatherInfo(); //Set up tempy
                                int id = Integer.parseInt(matchIDTF.getText()); //Grab the ID to pass
                                sql.UpdateData(tempy, id); //Run the func
                                try {
                                    results = sql.GetData(); //Refresh results
                                    UpdateTableSQL(results); //Update table
                                }
                                catch (Exception ex) {
                                    System.out.println("Database Connection Error");
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(MainWindow.this, "Invalid Data Entered! Please ensure" +
                                                " all fields are filled and escapes and disconnects are between 0 and 4!",
                                        "Error!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Upload Button Listener Setup
        uploadButton.addActionListener(new ActionListener() {
            /**
             * Upload Button Action: this method gets passed an action (the button being clicked),
             * and pops up a file chooser for locating a file containing properly formatted Match data.
             * Then it the runs the necessary SQL commands for adding all the data to the connected database.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (!connectionTextArea.getText().equals("Connected")) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No database connected!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) { //If the JFileChooser says the selection was ok enough, we proceed
                        File selectedFile = fileChooser.getSelectedFile();

                        int counter = sql.AddFromFile(selectedFile); //Pass file to SQLFunc
                        try {
                            results = sql.GetData(); //Refresh
                            UpdateTableSQL(results); //Update
                        } catch (Exception ex) {
                            System.out.println("Database Connection Error");
                        }
                        if (counter > 0) { //Cannot return anything above 0 unless it succeeded
                            JOptionPane.showMessageDialog(MainWindow.this, counter + " row(s) have been added!",
                                    "Success!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainWindow.this, "No rows could be added! Please try again.",
                                    "Error!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        //Delete Button Listener Setup
        deleteButton.addActionListener(new ActionListener() {
            /**
             * Delete Button Action: this method gets passed an action (the button being clicked),
             * and the runs the necessary SQL commands for deleting the data of the selected row.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (!connectionTextArea.getText().equals("Connected")) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No database connected!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (!matchIDTF.getText().isEmpty()) {
                    try {
                        int id = Integer.parseInt(matchIDTF.getText());
                        sql.DeleteData(id); //Pass ID for  deletion
                        ClearFields(); //Make the TextFields blank, so we're not seeing deleted data
                        results = sql.GetData();//Refresh
                        UpdateTableSQL(results);//Update
                    } catch (Exception ex) {
                        System.out.println("Database Connection Error");
                    }
                }

            }
        });

        //Winrate Button Setup
        //This function refreshes the results (to start the line at the beginning) then runs through each row and counts every
        //match where wasKiller == true as a match and from those matches, any match with less than 2 escapes is a win
        //Then performs the math (if any is to be done) to display your winrate
        winRateButton.addActionListener(new ActionListener() {
            /**
             * WinRate Button Action: this method gets passed an action (the button being clicked),
             * and does the math for the win rate of all matches where the killer boolean is true.
             * A win is considered less than two escapes. Anything more is either a draw or a loss.
             * @param e the event to be processed
             * @throws SQLException if a database error occurs.
             */
            public void actionPerformed(ActionEvent e) {
                if (!connectionTextArea.getText().equals("Connected")) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No database connected!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    try {
                        results = sql.GetData();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    double wr;
                    int wins = 0;
                    int matches = 0;

                    if (results != null) {
                        try {
                            while (results.next()) {
                                if (results.getInt("WASKILLER") == 1) {
                                    matches++;

                                    if (results.getInt("ESCAPES") < 2) {
                                        wins++;
                                    }
                                }
                            }

                            if (matches > 0) {
                                wr = ((double) wins / matches) * 100;

                                String winRateFormat = String.format("%.2f", wr);
                                JOptionPane.showMessageDialog(MainWindow.this, "Out of " + matches + " match(es), you won "
                                                + wins + " time(s)!\nWin Rate: "
                                                + winRateFormat + "%",
                                        "Win Rate", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(MainWindow.this, "No killer matches to display!",
                                        "Win Rate", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception ex) {
                            System.out.println("Database Connection Error");
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainWindow.this, "No killer matches to display!",
                                "Win Rate", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        /*
         *
         *
         * ----------/LISTENERS----------
         *
         *
         */

        int frameWidth = 900; //Set frame sizes, bounds, and make it visible
        int frameHeight = 500;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) screenSize.getWidth()/2 - frameWidth /2, (int) screenSize.getHeight()/2 - frameHeight/2,
                frameWidth, frameHeight);
        this.setVisible(true);
    }

    /**
     * FieldCheck method: Checks that all fields entered are valid according to our definition.
     * @return returns a -1 if any field is invalid. Returns a 1 for valid fields.
     * @throws NumberFormatException if non-integers are entered into the escapes or disconnects fields.
     */
    public int FieldCheck() { //Used for checking that fields are filled and without data that would cause error
        try {
        if (killerTF.getText().isEmpty()) {return -1;}
        if (surv1TF.getText().isEmpty()) {return -1;}
        if (surv2TF.getText().isEmpty()) {return -1;}
        if (surv3TF.getText().isEmpty()) {return -1;}
        if (surv4TF.getText().isEmpty()) {return -1;}
        if (mapTF.getText().isEmpty()) {return -1;}
        if ((Integer.parseInt(escapesTF.getText()) < 0 || Integer.parseInt(escapesTF.getText()) > 4)) {return -1;}
        if ((Integer.parseInt(disconnectsTF.getText()) < 0 || Integer.parseInt(disconnectsTF.getText()) > 4)) {return -1;}

        return 1;
        }
        catch (Exception ex) {
            System.out.println("Field Check Failed");
            return -1;
        }

    }

    /**
     * GatherInfo method: this method gathers the displayed information into a temporary Match object.
     * This is used to pass into SQL functions that may add or update data.
     * @return returns a -1 if the FieldCheck method fails, otherwise returns a 1.
     */
    public int GatherInfo() { //Fills our temp Match object with information from the textfield, so we can pass it
        if (FieldCheck() == -1) {return -1;}
        else {
            tempy.setKiller(killerTF.getText());
            tempy.setSurvivor1(surv1TF.getText());
            tempy.setSurvivor2(surv2TF.getText());
            tempy.setSurvivor3(surv3TF.getText());
            tempy.setSurvivor4(surv4TF.getText());
            tempy.setMap(mapTF.getText());
            tempy.setKillerBool(Boolean.parseBoolean(wasKillerTF.getText()));
            tempy.setEscapes(Integer.parseInt(escapesTF.getText()));
            tempy.setDisconnects(Integer.parseInt(disconnectsTF.getText()));
        }

        return 1;
    }

    /**
     * UpdateTableSQL method: this method clears the table and re-adds data to be displayed. This ensures
     * data displayed is always up to date after functions occur.
     * @param rs Gets passed a ResultSet. Data from this ResultSet is used to fill the table.
     * @return returns a 1 if successful, otherwise returns a 0.
     * @throws SQLException if a database error occurs.
     */
    public int UpdateTableSQL(ResultSet rs) throws SQLException {
        tableModel.setRowCount(0);
        if (rs != null) {
            while (rs.next()) {
                int matchID = rs.getInt("MATCHID");
                String killer = rs.getString("KILLER");
                String wasKiller = killerBoolCheck(rs.getInt("WASKILLER"));
                String map = rs.getString("MAP");
                String survivor1 = rs.getString("SURVIVOR1");
                String survivor2 = rs.getString("SURVIVOR2");
                String survivor3 = rs.getString("SURVIVOR3");
                String survivor4 = rs.getString("SURVIVOR4");
                int disconnects = rs.getInt("DISCONNECTS");
                int escapes = rs.getInt("ESCAPES");

                tableModel.addRow(new Object[]{matchID, killer, wasKiller, map, survivor1, survivor2, survivor3, survivor4, disconnects, escapes});
            }

            return 1;
        }

        else {return 0;}
    }

    //SQLite doesn't have bools
    //This is used to check the int and return an associated string

    /**
     * KillerBoolCheck method: as SQLite does not have booleans as a data type,
     * this method bridges the gap between our Match object and the database by
     * converting the SQLite killerBool integer to an actual bool for our object.
     * @param booledInt This is the integer version of our killerBool that is stored within SQLite
     * @return returns false to assign as a bool if the int passed is 0. Otherwise, it returns true.
     */
    public String killerBoolCheck(int booledInt){
        if (booledInt == 0) {
            return "false";
        }

        else {return "true";}
    }

    //Function for resetting all TextFields so old data doesn't remain

    /**
     * ClearFields method: this method clears all the text boxes. It is used
     * after deleting data so that old, deleted data is not still displayed after
     * a deletion has actually occurred.
     * @return returns 1 after clearing occurs.
     */
    public int ClearFields() { //Sets the textfields to blanks, for use upon deletion
        matchIDTF.setText("");
        killerTF.setText("");
        wasKillerTF.setText("");
        mapTF.setText("");
        surv1TF.setText("");
        surv2TF.setText("");
        surv3TF.setText("");
        surv4TF.setText("");
        disconnectsTF.setText("");
        escapesTF.setText("");

        return 1;
    }
}
