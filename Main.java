import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Match> database =  new ArrayList<>(); //Our database
        Scanner scanner = new Scanner(System.in);
        DataFuncs data = new DataFuncs();

        while (true) { //Keep looping the menu selection until exit is chosen
            System.out.println("Welcome to the Dead by Daylight Match Tracking System\n");
            System.out.println("1. Add Match Manually");
            System.out.println("2. Add Match From File");
            System.out.println("3. Display All Matches");
            System.out.println("4. Edit Match Data");
            System.out.println("5. Delete Match Data");
            System.out.println("6. Display Win Rate");
            System.out.println("7. Exit");
            System.out.print("Please Select An Option: ");

            try { //try catch to validate input. Switch for easy menu function
                int menuOption = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                switch (menuOption) {
                    case 1:
                        data.ManualAdd(database);
                        break;
                    case 2:
                        data.AddFromFile(database);
                        break;
                    case 3:
                        data.DisplayData(database);
                        break;
                    case 4:
                        data.EditData(database);
                        break;
                    case 5:
                        data.DeleteData(database);
                        break;
                    case 6:
                        data.DisplayWinRate(database);
                        break;
                    case 7:
                        System.out.println("Closing Dead by Daylight Match Tracking System\n");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid Selection! Please Try Again\n");
                }
            }
            catch (Exception e) {
                System.out.println("\nInvalid Selection! Please Try Again\n");
                scanner.nextLine();
            }
        }
    }
}

