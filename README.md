# Itinerary Prettifier
A command-line tool that converts raw flight itineraries into a customer-friendly format.
Built for Anywhere Holidays, this tool helps back-office administrators save time by automating the process of cleaning up itineraries.
## Project Structure

The project is organized into the following main folders:

- **Main**  
  Contains the entry point of the application.

- **model**  
  Holds the core data models:
    - `AirportLookup` – handles airport-related information.
    - `Coordinates` – manages geographic coordinates.

- **service**  
  Includes the service layer, such as:
    - `FileManagement` – responsible for reading, writing, and managing files.
## Features
•   Reads raw itineraries from a text file.<br/>
•	Converts airport codes (#IATA and ##ICAO) into full airport names using a lookup CSV.<br/>
•	Formats dates into DD-Mmm-YYYY (e.g., 05 Apr 2007).<br/>
### • Formats times:
•	T12 → hh:mmAM/PM (offset)<br/>
•	T24 → HH:mm (offset)<br/>
•	Converts Zulu time (Z) into (+00:00).<br/>
## Usage
**Compile:**
```
javac Main.java
```
**Run:**
```
java Main.java ./input.txt ./output.txt ./airport-lookup.csv
```
**Help flag:**
```
java Main.java -h
```
**Output:**
```
itinerary usage:
$ java Main.java ./input.txt ./output.txt ./airport-lookup.csv
```
## Input Files

•	**Input Itinerary (input.txt):** Text file with raw itinerary (admin-facing). <br/>
•	**Airport Lookup (airport-lookup.csv):** CSV with the following required columns:

```
name,iso_country,municipality,icao_code,iata_code,coordinates
```
•	If any column is missing or blank, the lookup is **malformed**. 
## Output File 
•	**Output Itinerary (output.txt):** Cleaned and customer-friendly version of the input itinerary.<br/>
•	Created only if no errors occur.
## Error Handling
•	Wrong number of arguments → show usage.<br/>
•	Missing input file → Input not found.<br/>
•	Missing airport lookup → Airport lookup not found.<br/>
•	Malformed airport lookup → Airport lookup malformed.<br/>
•	Invalid date/time format → leave unchanged in output.<br/>
•	Unknown airport codes → leave unchanged in output.<br/>
•	In case of error → no output file is created or overwritten.
## Example
**Input (input.txt):**
```
Customer: John Doe

Flight from #LAX to ##EGLL
Departure: D(2025-09-24T12:30-02:00)
Boarding: T24(2025-09-24T11:45-02:00)
Arrival: T12(2025-09-25T08:55Z)
```
**Airport Lookup (airport-lookup.csv):**
```
name,                                  iso_country,       municipality,       icao_code,       iata_code,      coordinates
Los Angeles International Airport,     US,                Los Angeles,        KLAX,            LAX,            33.9425,-118.4081
London Heathrow Airport,               GB,                London,             EGLL,            LHR,            51.4706,-0.4619
```
**Output (output.txt):**
```
Customer: John Doe

Flight from Los Angeles International Airport to London Heathrow Airport
Departure: 24-Sep-2025
Boarding: 11:45 (-02:00)
Arrival: 08:55AM (+00:00)
```
