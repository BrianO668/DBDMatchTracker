/*
Brian Oldham
CEN3024C-14877
26OCT2025
Class for accessing and manipulating Match data stored in an arraylist
 */
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFuncs {
    Match tempMatch;
    Scanner scanner = new Scanner(System.in);

    public int ManualAdd(ArrayList<Match> arry, Match tempy) {
        if ((tempy.getDisconnects() < 0 || tempy.getDisconnects() > 4) || (tempy.getEscapes() < 0 || tempy.getEscapes() > 4)) {
            return -1; //Invalid data, do not attempt to instantiate
        }
        else {
            Match temp = new Match(tempy.getKiller(), tempy.getSurvivor1(), tempy.getSurvivor2(), tempy.getSurvivor3(), tempy.getSurvivor4(),
                    tempy.getMap(), tempy.getKillerBool(), tempy.getEscapes(), tempy.getDisconnects());
            arry.add(temp); //Instantiate a Match and add it to the arraylist provide
        }

        return 1;
    }

    public int AddFromFile(ArrayList<Match> arry, File file) {
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
                            arry.add(tempMatch); //Instantiate and shove it in the arraylist that was passed as a parameter
                            counter++;
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

    public int EditData(ArrayList<Match> arry, int indexIntake, Match newInfo) {
        if ((newInfo.getDisconnects() < 0 || newInfo.getDisconnects() > 4) || (newInfo.getEscapes() < 0 || newInfo.getEscapes() > 4)) {
            return -1; //Invalid data, make no changes
        }

        else {
            arry.get(indexIntake).setKiller(newInfo.getKiller()); //Set old Match data to new Match data provided
            arry.get(indexIntake).setKillerBool(newInfo.getKillerBool());
            arry.get(indexIntake).setMap(newInfo.getMap());
            arry.get(indexIntake).setSurvivor1(newInfo.getSurvivor1());
            arry.get(indexIntake).setSurvivor2(newInfo.getSurvivor2());
            arry.get(indexIntake).setSurvivor3(newInfo.getSurvivor3());
            arry.get(indexIntake).setSurvivor4(newInfo.getSurvivor4());
            arry.get(indexIntake).setEscapes(newInfo.getEscapes());
            arry.get(indexIntake).setDisconnects(newInfo.getDisconnects());
        }

        return 1;
    }

    public int DeleteData(ArrayList<Match> arry, int idIntake) {
        int searchFlag = 0;
        int tempID;

        for (int i = 0; i < arry.size(); i++) { //Search provide arraylist for provided matchID and remove it
            if (arry.get(i).getMatchNumber() == idIntake) {
                tempID = arry.get(i).getMatchNumber();
                arry.get(i).setMatchAdder(); //Adjust the matchAdder, as it auto increments on instantiation
                arry.remove(i);
                while (i < arry.size()) {
                    arry.get(i).setMatchID(tempID);
                    i++;
                    tempID++;
                }
                searchFlag = 1;
            }
        }

        if (searchFlag == 0) {
            System.out.println("No match found");
            return -1;
        }

        return arry.size();
    }

    public double DisplayWinRate(ArrayList<Match> arry) {
        double wins = 0;
        double winRate = -1;
        int matches = 0;

        if (arry.isEmpty()) {
            System.out.println("No data available to calculate!\n");
            return -1;
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
                String winRateFormat = String.format("%.2f", winRate);
                System.out.println("Out of " + matches + " match(es), you won " + (int) wins + " times!\nWin Rate: "
                        + winRateFormat + "%\n");
            }

            else {
                System.out.println("No killer matches found!\n");
                return -1;
            }
        }
        return winRate;
    }
}
