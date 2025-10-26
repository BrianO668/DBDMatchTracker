import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {
    public DataFuncs funcs = new DataFuncs();
    public List<Match> tempDatabase = new ArrayList<>(); //Our database until SQLite gets involved

    public JTabbedPane tabbedPane = new JTabbedPane(); //For tabs in main window
    public JPanel connectPanel = new JPanel(); //Panel become their own tabs
    public JPanel connectNest = new JPanel(); //Go a layer deeper for layout
    public JPanel dataPanel = new JPanel();
    public JPanel dataNestL = new JPanel();//Nested layers for layour purposes again
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

    public MainWindow() {
        super();
        init();
    }

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
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Create Button Listener Setup
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    if (FieldCheck() == -1) { //Check if data is valid before running our ManualAdd function
                        try {
                            JOptionPane.showMessageDialog(MainWindow.this, "Invalid Data Entered! Please ensure" +
                                            " all fields are filled and escapes and disconnects are between 0 and 4!",
                                    "Error!", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
                        GatherInfo();
                        funcs.ManualAdd((ArrayList<Match>) tempDatabase, tempy);
                    }
                UpdateTable(); //Refresh the table after changes are made to keep the visible status of the database updated
            }
        });

        //Update Button Listener Setup
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tempDatabase.isEmpty()) {
                    try {
                        JOptionPane.showMessageDialog(MainWindow.this, "No data in database!",
                                "Empty Database!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    if (FieldCheck() != -1) { //Check that info is entered properly before running the EditData function
                        GatherInfo();
                        int index = Integer.parseInt(matchIDTF.getText()) - 1;
                        funcs.EditData((ArrayList<Match>) tempDatabase, index, tempy);
                    }
                    else {
                        try {
                            JOptionPane.showMessageDialog(MainWindow.this, "Invalid Data Entered! Please ensure" +
                                            " all fields are filled and escapes and disconnects are between 0 and 4!",
                                    "Error!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    UpdateTable();
                }
            }
        });

        //Upload Button Listener Setup
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) { //If the JFileChooser says the selection was ok enough, we proceed
                    File selectedFile = fileChooser.getSelectedFile();

                    int counter = funcs.AddFromFile((ArrayList<Match>) tempDatabase, selectedFile); //Runs the function and stores the return for our little window that pops up
                    UpdateTable();
                    if (counter > 0) { //Cannot return anything above 0 unless it succeeded
                        try {
                            JOptionPane.showMessageDialog(MainWindow.this, counter + " row(s) have been added!",
                                    "Success!", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
                        try {
                            JOptionPane.showMessageDialog(MainWindow.this, "No rows could be added! Please try again.",
                                    "Error!", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });

        //Delete Button Listener Setup
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    if (!matchIDTF.getText().equals("")) { //Check the MatchID of the Match object currently being displayed and run the DeleteData function with that number - 1 (because indexing)
                        try {
                            int id = Integer.parseInt(matchIDTF.getText());
                            System.out.println(id);
                            funcs.DeleteData((ArrayList<Match>) tempDatabase, id);
                            ClearFields(); //Make the TextFields blank, so we're not seeing deleted data
                            UpdateTable();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

            }
        });

        //Winrate Button Setup
        winRateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double wr = funcs.DisplayWinRate((ArrayList<Match>) tempDatabase);
                int wins = 0;
                int matches = 0;

                if (wr != -1) { //Coping some of the same code from the DataFunc function because I want the values for my pop-up
                    for (Match m : tempDatabase) { //2 escapes is a draw, more than 2 is a loss
                        if (m.getKillerBool()) { //Matches not played as killer are not calculated in this function
                            if (m.getEscapes() < 2) {
                                wins++;
                            }
                            matches++;
                        }
                    }

                    try {
                        String winRateFormat = String.format("%.2f", wr);
                        JOptionPane.showMessageDialog(MainWindow.this, "Out of " + matches + " match(es), you won "
                                        + (int) wins + " time(s)!\nWin Rate: "
                                        + winRateFormat + "%",
                                "Win Rate", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                else {
                    try {
                        JOptionPane.showMessageDialog(MainWindow.this, "No killer matches to display!",
                                "Win Rate", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        int frameWidth = 900; //Set frame sizes, bounds, and make it visible
        int frameHeight = 500;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) screenSize.getWidth()/2 - (int) frameWidth/2, (int) screenSize.getHeight()/2 - (int) frameHeight/2,
                frameWidth, frameHeight);
        this.setVisible(true);
    }

    public int FieldCheck() { //Used for checking that fields are filled and without data that would cause error
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

    //Function to keep out table updated after operations
    public int UpdateTable () { //Refreshes the visible data in the table
        tableModel.setRowCount(0); //Clear the rows
        for (Match m : tempDatabase) { //Read everything, as MatchIDs change upon deletions
            Object[] rowData = {
                    m.getMatchNumber(),
                    m.getKiller(),
                    m.getKillerBool(),
                    m.getMap(),
                    m.getSurvivor1(),
                    m.getSurvivor2(),
                    m.getSurvivor3(),
                    m.getSurvivor4(),
                    m.getDisconnects(),
                    m.getEscapes()
            };
            tableModel.addRow(rowData);
        }
        return 1;
    }

    //Function for resetting all TextFields so old data doesn't remain
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

