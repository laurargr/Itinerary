package model;

public class AirportLookup {
    private String name;
    private String isoCountry;
    private String municipality;
    private String icaoCode;
    private String iataCode;
    private Coordinates coordinates;

    public AirportLookup(String name, String isoCountry, String municipality, String icaoCode, String iataCode, String latitude, String longitude) {
        this.name = name;
        this.isoCountry = isoCountry;
        this.municipality = municipality;
        this.icaoCode = icaoCode;
        this.iataCode = iataCode;
        this.coordinates = new Coordinates(latitude, longitude);
    }

    public boolean isValid() {
        if (this.name.trim().isEmpty() || this.isoCountry.trim().isEmpty() || this.municipality.trim().isEmpty() || this.icaoCode.trim().isEmpty() || this.iataCode.trim().isEmpty() || coordinates.isValid() == false){
            return false;
        }
        return true;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String latitude, String longitude) {
        this.coordinates.setLatitude(latitude);
        this.coordinates.setLongitude(longitude);
    }
}
