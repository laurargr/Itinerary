package service;

import model.AirportLookup;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {
   List<String> inputList;
   List<String> outputList;
   List <AirportLookup> airportLookups;

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
            System.out.println("Error reading input file");
        }
    }

    public void readLookupFile(String fileToLook) {
        try {
            FileReader l = new FileReader(fileToLook);
            BufferedReader br = new BufferedReader(l);
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String [] lookups = line.split(",");
                AirportLookup a = new AirportLookup(lookups[0], lookups[1], lookups[2], lookups[3],lookups[4],lookups[5],lookups[6]);
                airportLookups.add(a);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading lookup file");
        }
    }

    public void parseData () {
       for (int i = 0; i < inputList.size(); i++) {
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
               if (words[a].startsWith("D") && words[a].endsWith(")")) {
                   String date = words[a].substring(2, (words[a].length()-1));
                   words[a] = parseDate(date);
               }
               if (words[a].startsWith("T12") && words[a].endsWith(")")) {
                   String timeT12 = words[a].substring(4, (words[a].length()-1));
                   words[a] = parseTime12(timeT12);
               }
               if (words[a].startsWith("T24") && words[a].endsWith(")")) {
                   String timeT24 = words[a].substring(4, (words[a].length()-1));
                   words[a] = parseTime24(timeT24);
               }
           }
           String joined = String.join(" ", words);
           outputList.add(joined);
       }
    }

    public String parseIataCode(String code){
        String subCode = code.substring(1, 4);
        for (int i = 0; i < airportLookups.size(); i++) {
            AirportLookup lookup = airportLookups.get(i);
            if (lookup.getIataCode().equals(subCode)) {
                return lookup.getName();
            }
        }
        return code;
    }

    public String parseIcaoCode(String code){
       String subCode = code.substring(2, 6);
       for (int i = 0; i < airportLookups.size(); i++) {
           AirportLookup lookup = airportLookups.get(i);
           if(lookup.getIcaoCode().equals(subCode)){
               return lookup.getName();
           }
       }
       return code;
    }

    public String parseDate (String date) {
        LocalDate ld;
        date = date.replace("−", "-");
        OffsetDateTime odt = OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        ld = odt.toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return ld.format(dateFormatter);
    }

    public String parseTime12 (String time) {
        time = time.replace("−", "-");
        OffsetDateTime odt = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma (XXX)");
        return  odt.format(timeFormatter);
    }

    public String parseTime24 (String time) {
        time = time.replace("−", "-");
        OffsetDateTime odt = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm (XXX)");
        return  odt.format(timeFormatter);
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
}

