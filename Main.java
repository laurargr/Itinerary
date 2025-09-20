import service.FileMenagment;
public class Main {
    public static void main(String[] args) {

        String input = "";
        String output;
        String airportLookup;

        if (args.length != 3) {
            System.out.println("Itinerary usage: \n java Main.java ./input.txt ./output.txt ./airport-lookup.csv");
            System.exit(1);
        }
        input = args[0];
        output = args[1];
        airportLookup = args[2];

        FileMenagment fileManagment= new FileMenagment();
        if (fileManagment.fileExist(input) != true) {
            System.out.println("Input file does not exist");
            System.exit(1);
        }
        if (fileManagment.fileExist(airportLookup) != true) {
            System.out.println("Lookup file does not exist");
            System.exit(1);
        }
        fileManagment.readInputFile(input);
        fileManagment.createFile(output);
        fileManagment.readLookupFile(airportLookup);
    }
}