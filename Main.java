import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //Thread for running our MainWindow GUI
            public void run() {
                MainWindow mainWindow = new MainWindow();
            }
        });
    }

}
