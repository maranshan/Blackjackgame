package Blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class fileHelper {
    

    public void writeStateToFile(ArrayList<List<String>> SaveToFile, String file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            for (List<String> user : SaveToFile) {
                String username = user.get(0);
                String password = user.get(1);
                String balance = user.get(2);
                writer.println(username + "," + password + "," + balance);
            }
        } catch (IOException e) {
            System.err.println("Feil ved skriving til filen: " + e.getMessage());
        }
    }

    public String readLastLine(String filename) {
        File file = new File(filename);
        String lastLine = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lastLine = scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lastLine;
    }

}
