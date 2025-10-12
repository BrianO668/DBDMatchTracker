/*
Brian Oldham
CEN3024C-14877
12OCT2025
Class for accessing and manipulating Match data stored in an arraylist
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFuncs {
    Match tempMatch;
    Scanner scanner = new Scanner(System.in);
    public String killer;
    public String survivor1;
    public String survivor2;
    public String survivor3;
    public String survivor4;
    public String map;
    public boolean wasKiller;
    public int escapes;
    public int disconnects;
    public char killerBool;

    public int ManualAdd(ArrayList<Match> arry) {
        while(true) {
            try {
                System.out.println("\nPlease enter the killer's name: ");
                killer = scanner.nextLine();
                System.out.println("\nPlease enter the 1st survivor's name: ");
                survivor1 = scanner.nextLine();
                System.out.println("\nPlease enter the 2nd survivor's name: ");
                survivor2 = scanner.nextLine();
                System.out.println("\nPlease enter the 3rd survivor's name: ");
                survivor3 = scanner.nextLine();
                System.out.println("\nPlease enter the 4th survivor's name: ");
                survivor4 = scanner.nextLine();
                System.out.println("\nPlease enter the map name: ");
                map = scanner.nextLine();

                while(true) { //Keep us looped so we don't exit with invalid entries
                    System.out.println("\nWere you the killer? (Y/N)");
                    try {
                        killerBool = scanner.next().charAt(0);

                        if (killerBool == 'Y' || killerBool == 'y') {
                            wasKiller = true;
                            break;
                        }
                        else if (killerBool == 'N' || killerBool == 'n') {
                            wasKiller = false;
                            break;
                        }
                        else {
                            System.out.println("Invalid input. Try again.");
                        }
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                while(true) { //Keep us looped so we don't exit with invalid entries
                    System.out.println("\nHow many survivor escapes were there?: ");
                    try {
                        escapes = scanner.nextInt();

                        if (escapes < 0 || escapes > 4) { //Game can only have 0-4 escapes
                            System.out.println("\nPlease enter a number between 0 and 4");
                        }
                        else {break;}
                    } catch (Exception e) {
                        System.out.println("\nPlease enter a number between 0 and 4");
                        scanner.nextLine();
                    }
                }

                while(true) { //Keep us looped so we don't exit with invalid entries
                    System.out.println("\nHow many survivor disconnects were there?: ");
                    try {
                        disconnects = scanner.nextInt();

                        if (disconnects < 0 || disconnects > 4) { //Game can only have 0-4 disconnects
                            System.out.println("\nPlease enter a number between 0 and 4");
                        }
                        else {break;}
                    } catch (Exception e) {
                        System.out.println("\nPlease enter a number between 0 and 4");
                        scanner.nextLine();
                    }
                }

                try { //Instantiate our new Match object, shove it into the arraylist that was passed, and print the information to screen
                    tempMatch = new Match(killer, survivor1, survivor2, survivor3, survivor4, map, wasKiller, escapes, disconnects);
                    arry.add(tempMatch);
                    scanner.nextLine();

                    System.out.println("\nMatch added!\nMatch ID: " + tempMatch.getMatchNumber() + "\nMap: " + tempMatch.getMap()
                            + "\nKiller: " + tempMatch.getKiller() + "\nSurvivors:\n1. " + tempMatch.getSurvivor1()
                            + "\n2. " + tempMatch.getSurvivor2() + "\n3. "  + tempMatch.getSurvivor3() + "\n4. " +
                            tempMatch.getSurvivor4() + "\nThere was/were " +
                            tempMatch.getEscapes() + " escape(s)\nand there was/were " + tempMatch.getDisconnects() + " disconnect(s)");

                    if (tempMatch.getKillerBool()) {
                        System.out.println("You were the killer\n");
                    } else {
                        System.out.println("You were not the killer\n");
                    }
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                return 1;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public int AddFromFile(ArrayList<Match> arry) {
        System.out.print("\nPlease enter the file path: ");
        try {
            String filePath = scanner.nextLine();

            if (filePath.isEmpty()) {
                System.out.println("\nNo valid path entered!\n");
            }

            else {
                try {
                    File file = new File(filePath);
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
                            arry.add(tempMatch); //Instantiate and shove it in the arraylist that was passed as a parameter
                            counter++;
                        } catch (Exception e) {
                            System.out.println("\nFile format error. Please check formatting and try again.");
                            break;
                        }
                    }

                    if (counter > 0) {
                        System.out.println("\nSuccessfully added " + counter + " match(es) from file!\n");
                    } else {
                        System.out.println("\nNo matches added!\n");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return 2;
                }
            }
        }

        catch (Exception e) {
            System.out.println("\nInvalid file path\n");
        }

        return 2;
    }

    public int DisplayData(ArrayList<Match> arry) { //This method gets passed an arraylist, if it's not empty, it loops and prints the data
        if (!arry.isEmpty()) {
            System.out.println("---Displaying all data in database---");
            for (int i = 0; i < arry.size(); i++) {
                System.out.println("Match ID: " + arry.get(i).getMatchNumber() + "\nMap: " + arry.get(i).getMap()
                        + "\nKiller: " + arry.get(i).getKiller() + "\nSurvivors:\n1. " + arry.get(i).getSurvivor1()
                        + "\n2. " + arry.get(i).getSurvivor2() + "\n3. "  + arry.get(i).getSurvivor3() + "\n4. " +
                        arry.get(i).getSurvivor4() + "\nThere was/were " +
                        arry.get(i).getEscapes() + " escape(s)\nand there was/were " + arry.get(i).getDisconnects() + " disconnect(s)");

                if (arry.get(i).getKillerBool()) {
                    System.out.println("You were the killer\n");
                } else {
                    System.out.println("You were not the killer\n");
                }
            }
        }
        else {
            System.out.println("No data available to display!\n");
        }

        return 3;
    }

    public int EditData(ArrayList<Match> arry) { //This method looks intimidating, but it is mostly while loops for menus and error-handling
        int id;
        String info;
        int infoInt;
        char infoChar;
        boolean switchMenu = true;
        int searchFlag = 0;

        if (arry.isEmpty()) {
            System.out.println("No data available to edit!\n");
        }

        else {
            while (true) { //Initial while loop for error-handling ID input
                System.out.println("\nPlease enter the Match ID of the entry you wish to edit (Enter -1 to quit): ");
                switchMenu = true;
                try {
                    id = scanner.nextInt();

                    if (id == -1) {
                        System.out.println("\n----Returning to main menu----");
                        break;
                    }

                    else if (id < -1 || id == 0) {
                        System.out.println("\nPlease enter a valid match ID\n");
                        searchFlag = 1;
                    }

                    else {
                        searchFlag = 0; //Sentinel for only stating "No match found" once rather than every iteration of the loop
                        scanner.nextLine();
                        for (int i = 0; i < arry.size(); i++) { //Loop through arraylist, if a match is found, print the information
                            if (arry.get(i).getMatchNumber() == id) {
                                searchFlag = 1;
                                System.out.println("The following match information was found: ");
                                System.out.println("Match ID: " + arry.get(i).getMatchNumber() + "\nMap: " + arry.get(i).getMap()
                                        + "\nKiller: " + arry.get(i).getKiller() + "\nSurvivors:\n1. " + arry.get(i).getSurvivor1()
                                        + "\n2. " + arry.get(i).getSurvivor2() + "\n3. "  + arry.get(i).getSurvivor3() + "\n4. " +
                                        arry.get(i).getSurvivor4() + "\nThere was/were " +
                                        arry.get(i).getEscapes() + " escape(s)\nand there was/were " + arry.get(i).getDisconnects() + " disconnect(s)");
                                if (arry.get(i).getKillerBool()) {
                                    System.out.println("You were the killer\n");
                                } else {
                                    System.out.println("You were not the killer\n");
                                }

                                while (switchMenu){ //Edit menu while-loop
                                    System.out.println("Please review the edit menu options\n");

                                    System.out.println("1. Edit Map");
                                    System.out.println("2. Edit Killer");
                                    System.out.println("3. Edit Survivor 1");
                                    System.out.println("4. Edit Survivor 2");
                                    System.out.println("5. Edit Survivor 3");
                                    System.out.println("6. Edit Survivor 4");
                                    System.out.println("7. Edit Disconnects");
                                    System.out.println("8. Edit Escapes");
                                    System.out.println("9. Go back");
                                    System.out.print("Please Select An Option: ");

                                    try {
                                        int choice = scanner.nextInt();
                                        System.out.println();

                                        switch(choice) {
                                            case 1:
                                                while (true) { //While loops in the switch cases will all keep us in for error-handling
                                                    System.out.println("Previous map :" + arry.get(i).getMap() +
                                                            "\nPlease enter a new map: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setMap(info);

                                                        System.out.println("New map set to " + arry.get(i).getMap());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }
                                                }
                                                break;
                                            case 2:
                                                while (true) {
                                                    System.out.println("Previous killer :" + arry.get(i).getKiller() +
                                                            "\nPlease enter a new killer: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setKiller(info);

                                                        System.out.println("New killer set to " + arry.get(i).getKiller());

                                                        while(true) { //Loop for input/error-handling
                                                            System.out.println("\nWere you the killer? (Y/N)");
                                                            try {
                                                                infoChar = scanner.next().charAt(0);

                                                                if (infoChar == 'Y' || infoChar == 'y') {
                                                                    arry.get(i).setKillerBool(true);
                                                                    break;
                                                                } else if (infoChar == 'N' || infoChar == 'n') {
                                                                    arry.get(i).setKillerBool(false);
                                                                    break;
                                                                }
                                                                else {
                                                                    System.out.println("Invalid input. Please try again");
                                                                }
                                                            }

                                                            catch (Exception e) {
                                                                System.out.println(e.getMessage());
                                                                scanner.nextLine();
                                                            }
                                                        }
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 3:
                                                while (true) { //Print previous survivor for confirmation, take input for new survivor
                                                    System.out.println("Previous survivor: " + arry.get(i).getSurvivor1() +
                                                            "\nPlease enter a new survivor: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setSurvivor1(info);

                                                        System.out.println("New survivor set to " + arry.get(i).getSurvivor1());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 4: //Same logic as case 3. 5 and 6 will also be the same
                                                while (true) {
                                                    System.out.println("Previous survivor: " + arry.get(i).getSurvivor2() +
                                                            "\nPlease enter a new survivor: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setSurvivor2(info);

                                                        System.out.println("New survivor set to " + arry.get(i).getSurvivor2());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 5:
                                                while (true) {
                                                    System.out.println("Previous survivor: " + arry.get(i).getSurvivor3() +
                                                            "\nPlease enter a new survivor: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setSurvivor3(info);

                                                        System.out.println("New survivor set to " + arry.get(i).getSurvivor3());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 6:
                                                while (true) {
                                                    System.out.println("Previous survivor: " + arry.get(i).getSurvivor4() +
                                                            "\nPlease enter a new survivor: ");
                                                    scanner.nextLine();

                                                    try {
                                                        info = scanner.nextLine();

                                                        arry.get(i).setSurvivor4(info);

                                                        System.out.println("New survivor set to " + arry.get(i).getSurvivor4());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 7: //Loop to keep us in until a proper input is given
                                                while (true) {
                                                    System.out.println("Previous disconnects: " + arry.get(i).getDisconnects() +
                                                            "\nPlease enter new amount of disconnects: ");

                                                    try {
                                                        while (true) {
                                                            infoInt = scanner.nextInt();

                                                            if (infoInt < 0 || infoInt > 4) {
                                                                System.out.println("\nPlease enter a number between 0 and 4\nPlease enter new amount of disconnects: ");
                                                            } else {
                                                                arry.get(i).setDisconnects(infoInt);
                                                                break;
                                                            }
                                                        }

                                                        System.out.println("New disconnects set to " + arry.get(i).getDisconnects());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println("\nPlease enter a valid amount of disconnects!\n");
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 8: //Same logic as case 7
                                                while (true) {
                                                    System.out.println("Previous escapes: " + arry.get(i).getEscapes() +
                                                            "\nPlease enter new amount of escapes: ");

                                                    try {
                                                        while (true) {
                                                            infoInt = scanner.nextInt();

                                                            if (infoInt < 0 || infoInt > 4) {
                                                                System.out.println("\nPlease enter a number between 0 and 4\nPlease enter new amount of escapes: ");
                                                            } else {
                                                                arry.get(i).setEscapes(infoInt);
                                                                break;
                                                            }
                                                        }

                                                        System.out.println("New escapes set to " + arry.get(i).getEscapes());
                                                        break;
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println("\nPlease enter a valid amount of escapes!\n");
                                                        scanner.nextLine();
                                                    }
                                                }
                                                break;
                                            case 9:
                                                switchMenu = false; //Lets us exit the edit menu
                                                break;
                                            default:
                                                System.out.println("Invalid Selection! Please Try Again\n");
                                        }
                                    }

                                    catch (Exception e) {
                                        System.out.println("\nPlease enter a valid selection!\n");
                                        scanner.nextLine();
                                    }
                                }
                                break;
                            }
                        }
                    }

                    if (searchFlag == 0) {
                        System.out.println("\nNo match ID " + id + " found!");
                    }
                }

                catch (Exception e) {
                    System.out.println("\nPlease enter a valid Match ID!");
                    scanner.nextLine();
                }
            }
        }

        return 4;
    }

    public int DeleteData(ArrayList<Match> arry) {
        int id;
        int searchFlag = 0;

        if (arry.isEmpty()) {
            System.out.println("No data available to delete!\n");
        }

        else {
            while (true){
                searchFlag = 0; //Sentinel so we can wait until the loops are done to say no ID was found
                if (arry.isEmpty()) { //Otherwise it would say no ID found after each iteration
                    System.out.println("No data left in database! Exiting...\n");
                    break;
                }

                System.out.println("\nPlease enter the Match ID of the entry you wish to delete (Enter -1 to quit): ");
                try {
                    id = scanner.nextInt();

                    if (id == -1) {
                        System.out.println("\n----Returning to main menu----");
                        break;
                    }

                    else if (id < -1 || id ==0) { //No Match ID will start with 0 or be negative
                        System.out.println("\nPlease enter a valid match ID\n");
                        searchFlag = 1;
                    }

                    else {
                        scanner.nextLine();
                        for (int i = 0; i < arry.size(); i++) { //Loop through the arraylist passed and remove the instance with a matching ID
                            if (arry.get(i).getMatchNumber() == id) {
                                System.out.println("Removing Match " + (i + 1));
                                arry.remove(i);
                                searchFlag = 1;
                                break;
                            }
                        }
                    }

                    if (searchFlag == 0) {
                        System.out.println("\nNo match ID " + id + " found!");
                    }
                }

                catch (Exception e) {
                    System.out.println("Please enter a valid Match ID!\n");
                    scanner.nextLine();
                }
            }
        }

        return 5;
    }

    public int DisplayWinRate(ArrayList<Match> arry) {
        double wins = 0;
        double winRate;
        int matches = 0;

        if (arry.isEmpty()) {
            System.out.println("No data available to calculate!\n");
        }

        else { //A win is only counted as a match played as killer with less than 2 escapes
            for (Match m : arry) { //2 escapes is a draw, more than 2 is a loss
                if (m.getKillerBool()) { //Matches not played as killer are not calculated in this function
                    if (m.getEscapes() < 2) {
                        wins++;
                    }
                    matches++;
                }
            }

            if (matches > 0) { //Only calculate if killer matches were found
                winRate = (wins / matches) * 100;
                System.out.println("Out of " + matches + " match(es), you won " + (int) wins + " times!\nWin Rate: "
                        + winRate + "%\n");
            }

            else {
                System.out.println("No killer matches found!\n");
            }
        }
        return 6;
    }
}
