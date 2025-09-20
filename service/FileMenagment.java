package service;

import model.AirportLookup;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileMenagment {
   List<String> inputArr;
   List <AirportLookup> airportLookups;

    public FileMenagment() {
        this.inputArr = new ArrayList<String>();
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
                inputArr.add(line.trim());
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }

    }

    public void createFile(String fileToCreate) {
        try {
            FileWriter fw = new FileWriter(fileToCreate);
            for (int i = 0; i < inputArr.size(); i++) {
                fw.write(inputArr.get(i) + "\n");
            }
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

