package fr.artisandeveloppeur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        String fileName = "./resources/employees.txt";
        try {
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            System.out.println("Reading file...");
            String line;
            Boolean first_line = true;
            // Parcours du fichier pour traiter ligne par ligne
            while((line = bufferedReader.readLine()) != null) {
              try {
                    // Traitement particulier dans le cas de la première ligne
                    if (first_line) {
                        first_line = false;
                    } else {
                        processLine(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bufferedReader.close();
            System.out.println("Batch job done.");
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    private static void processLine(String line) throws Exception {
        String[] tokens = line.split(",");
        for (int i = 0; i < tokens.length; i++)
            tokens[i] = tokens[i].trim();
        // Vérifier que le tableau est valide
        if (isValidToken(tokens)) {
            String[] date = tokens[2].split("/");
            // Vérifier que la date est valide
            if (isValidDate(date)) {
                Calendar cal = Calendar.getInstance();
                // Est-ce une date anniversaire?
                if (isAnniversaryDate(date, cal)) {
                    MailBroker.sendEmail(tokens[3], "Joyeux Anniversaire !", "Bonjour "
                            + tokens[0] + ",\nJoyeux Anniversaire !\nA bientôt,");
                }
            } else {
                throw new Exception("Cannot read birthdate for " + tokens[0] + " " + tokens[1]);
            }
        } else {
            throw new Exception("Invalid file format");
        }
    }

    private static boolean isValidToken(String[] tokens) {
        return tokens.length == 4;
    }

    private static boolean isValidDate(String[] date) {
        return date.length == 3;
    }

    private static boolean isAnniversaryDate(String[] date, Calendar cal) {
        return cal.get(Calendar.DATE) == Integer.parseInt(date[0]) && cal.get(Calendar.MONTH) == (Integer.parseInt(date[1]) - 1);
    }

}
