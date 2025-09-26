import service.FileManagement;
public class Main {
    public static void main(String[] args) {

        String input = "";
        String output;
        String airportLookup;
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        if (args.length != 3) {
            System.out.println( ANSI_YELLOW + "Itinerary usage: \n java Main.java ./input.txt ./output.txt ./airport-lookup.csv" + ANSI_RESET);
            System.exit(1);
        }
        input = args[0];
        output = args[1];
        airportLookup = args[2];

        FileManagement fileManagment= new FileManagement();
        if (fileManagment.fileExist(input) != true) {
            System.out.println(ANSI_RED + "Input file does not exist" + ANSI_RESET);
            System.exit(1);
        }
        if (fileManagment.fileExist(airportLookup) != true) {
            System.out.println(ANSI_RED + "Lookup file does not exist" + ANSI_RESET);
            System.exit(1);
        }
        fileManagment.readInputFile(input);
        fileManagment.readLookupFile(airportLookup);
        fileManagment.parseData();
        fileManagment.createFile(output);
        fileManagment.print();
    }
}