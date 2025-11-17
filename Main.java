import javax.swing.*;

/**
 * Main Class: Includes main method used to start the application
 */
public class Main {
    /**
     * main method: Starts a new Swing thread
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //Thread for running our MainWindow GUI
            /**
             * run method: Is part of the new Swing thread. Instantiates a MainWindow. Essentially paints our window (designed in another class) to the screen.
             */
            public void run() {
                MainWindow mainWindow = new MainWindow();
            }
        });
    }

}
