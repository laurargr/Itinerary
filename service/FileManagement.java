package service;
import model.AirportLookup;
import java.io.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManagement {
   List<String> inputList;
   List<String> outputList;
   List <AirportLookup> airportLookups;
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_RED = "\u001B[31m";



    public FileManagement() {
        this.inputList = new ArrayList<String>();
        this.outputList = new ArrayList<String>();
        this.airportLookups = new ArrayList<AirportLookup>();
    }

    public boolean fileExist(String fileToCheck) {
        File e = new File(fileToCheck);
        return e.exists();
    }

    public void readInputFile(String fileToRead) {
        try {
            FileReader r = new FileReader(fileToRead);
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                inputList.add(line.trim());
            }
            br.close();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading input file" + ANSI_RESET);
            System.exit(1);
        }
    }

    public void readLookupFile(String fileToLook) {
        try {
            FileReader l = new FileReader(fileToLook);
            BufferedReader br = new BufferedReader(l);
            String line;
            String [] headers =br.readLine().split(",");
            if (headers.length != 6) {
                System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                System.exit(1);
            }
            else {
                if (!headers[0].trim().toLowerCase().equals("name")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                if (!headers[1].trim().toLowerCase().equals("iso_country")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                if (!headers[2].trim().toLowerCase().equals("municipality")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                if (!headers[3].trim().toLowerCase().equals("icao_code")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                if (!headers[4].trim().toLowerCase().equals("iata_code")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                if (!headers[5].trim().toLowerCase().equals("coordinates")) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }

            }

            while ((line = br.readLine()) != null) {
                String [] lookups = line.split(",");
                if (lookups.length < 7) {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
                    AirportLookup a = new AirportLookup(lookups[0], lookups[1], lookups[2], lookups[3],lookups[4],lookups[5],lookups[6]);
                if (a.isValid()) {
                    airportLookups.add(a);
                }
                else {
                    System.out.println(ANSI_RED + "Lookup file data is malformed" + ANSI_RESET);
                    System.exit(1);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading lookup file" + ANSI_RESET);
        }
    }

    public void parseData () {
       for (int i = 0; i < inputList.size(); i++) {
           String line = inputList.get(i);
           line.replace('\u000B', '\n').replace('\f', '\n').replace('\r', '\n');

           if (i != inputList.size()-1) {
               if (line.trim().isEmpty() && inputList.get(i + 1).isEmpty()) continue;
           }

           String [] words = inputList.get(i).split(" ");
           for (int a = 0; a < words.length; a++) {
               if (words[a].startsWith("#")){
                   String resultName = parseIataCode(words[a]);
                   words[a] = resultName;
               }
               if (words[a].startsWith("##")){
                   String resultName = parseIcaoCode(words[a]);
                   words[a] = resultName;
               }
               if (words[a].startsWith("*#")){
                   String resultName = words[a];
                   String subName = resultName.substring(2, resultName.length());
                   if (subName.length() == 3) {
                       resultName = parseIataCode(resultName);
                       words[a] = resultName;
                   }
                   else {
                       resultName = parseIcaoCode(resultName);
                       words[a] = resultName;
                   }
               }
               if (words[a].startsWith("D") && words[a].endsWith(")")) {
                   String resultDate = parseDate(words[a]);
                   words[a] = resultDate;
               }
               if (words[a].startsWith("T12") && words[a].endsWith(")")) {
                   String timeT12 = parseTime12(words[a]);
                   words[a] = timeT12;
               }
               if (words[a].startsWith("T24") && words[a].endsWith(")")) {
                   String timeT24 = parseTime24(words[a]);
                   words[a] = timeT24;
               }
           }
           String joined = String.join(" ", words);
           outputList.add(joined);
       }
    }

    public String parseIataCode(String code){
        if (code.startsWith("*")) {
            String subCode = code.substring(2, 5);
            for (int i = 0; i < airportLookups.size(); i++) {
                AirportLookup lookup = airportLookups.get(i);
                if (lookup.getIataCode().equals(subCode)) {
                    return lookup.getMunicipality();
                }
            }
        }
        else {
            String subCode = code.substring(1, 4);
            for (int i = 0; i < airportLookups.size(); i++) {
                AirportLookup lookup = airportLookups.get(i);
                if (lookup.getIataCode().equals(subCode)) {
                    return lookup.getName();
                }
            }
        }

        return code;
    }

    public String parseIcaoCode(String code){
        if (code.length() < 6) {
            return code;
        }
        if (code.startsWith("*")) {
            String subCode = code.substring(3, 7);
            for (int i = 0; i < airportLookups.size(); i++) {
                AirportLookup lookup = airportLookups.get(i);
                if (lookup.getIcaoCode().equals(subCode)) {
                    return lookup.getMunicipality();
                }
            }
        }
        else {
            String subCode = code.substring(2, 6);
            for (int i = 0; i < airportLookups.size(); i++) {
                AirportLookup lookup = airportLookups.get(i);
                if(lookup.getIcaoCode().equals(subCode)){
                    return lookup.getName();
                }
            }
        }
       return code;
    }

    public String parseDate (String date) {
        int n = date.length();
        String subDate = date.substring(2, (n -1));
        try {
            LocalDate ld;
            subDate = subDate.replace("−", "-");
            OffsetDateTime odt = OffsetDateTime.parse(subDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            ld = odt.toLocalDate();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            return ld.format(dateFormatter);
        } catch (Exception e) {
            return date;
        }

    }

    public String parseTime12 (String time) {
        int n = time.length();
        String subTime = time.substring(4, (n -1));
        try {
            subTime = subTime.replace("−", "-");
            OffsetDateTime odt = OffsetDateTime.parse(subTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma (XXX)");
            String formatted = odt.format(timeFormatter);
            formatted = formatted.replace("(Z)", "(+00:00)");
            return formatted;
        } catch (Exception e) {
            return time;
        }

    }

    public String parseTime24 (String time) {
        int n = time.length();
        String subTime = time.substring(4, (n-1));
        try {
            subTime = subTime.replace("−", "-");
            OffsetDateTime odt = OffsetDateTime.parse(subTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm (XXX)");
            String formatted = odt.format(timeFormatter);
            formatted = formatted.replace("(Z)", "(+00:00)");
            return  formatted;
        } catch (Exception e) {
            return time;
        }

    }

    public void createFile(String fileToCreate) {
        try {
            FileWriter fw = new FileWriter(fileToCreate);
            for (int i = 0; i < outputList.size(); i++) {
                    fw.write(outputList.get(i) + "\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Error creating output file");
        }
    }

    public void print() {

        for (int i = 0; i < outputList.size(); i++) {
            System.out.println(ANSI_GREEN + outputList.get(i) + ANSI_RESET);
        }
    }

}

