package  hotelapp;

/**
 * Created by dean on 9/1/2016.
 * Basic Hotel info
 */
public class Hotel implements Comparable {
    private String hotelId;
    private String hotelName;
    private Address addressInfo;

    public Hotel(){}

    /**
     * Initialize Hotel object
     * @param hotelId the id of the hotel
     * @param hotelName the name of the hotel
     * @param address the address info of the hotel
     */
    public Hotel(String hotelId,String hotelName,Address address){
        this.hotelId=hotelId;
        this.hotelName=hotelName;
        this.addressInfo=address;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Address getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(Address addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public int compareTo(Object o) {
        try{
            Hotel h=(Hotel)o;
            return this.hotelName.compareTo(h.getHotelName());
        }
        catch (Exception e){
            throw e;
        }

    }
}
