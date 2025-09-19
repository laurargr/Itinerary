package FileMenagment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMenagment {
   List<String> inputArr;

    public FileMenagment() {
        this.inputArr = new ArrayList<String>();
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

