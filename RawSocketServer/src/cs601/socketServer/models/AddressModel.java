package cs601.socketServer.models;

/**
 * Created by dean on 9/1/2016.
 * Address infor about a hotel
 */
public class AddressModel {
    private String streetAddress;
    private String state;
    private String city;
    private String country;
    private double longitude;
    private double latitude;

    public AddressModel(){}

    /**
     * Initialize Address Object
     * @param streetAddress
     * @param state
     * @param city
     * @param longitude
     * @param latitude
     */
    public AddressModel(String streetAddress, String state, String city, double longitude, double latitude,String country) {
        this.streetAddress =  streetAddress;
        this.state = state;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country=country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return streetAddress+", "+state+", "+city+", "+country;
    }
}
