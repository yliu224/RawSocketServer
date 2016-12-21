package  socketServer.models;

/**
 * Created by dean on 9/1/2016.
 * Basic Hotel info
 */
public class HotelModel implements Comparable {
    private String hotelId;
    private String hotelName;
    private double rating;
    private AddressModel addressInfo;
    private String imageUrl;

    public HotelModel(){}

    /**
     * Initialize Hotel object
     * @param hotelId the id of the hotel
     * @param hotelName the name of the hotel
     * @param address the address info of the hotel
     */
    public HotelModel(String hotelId,String hotelName,AddressModel address){
        this.hotelId=hotelId;
        this.hotelName=hotelName;
        this.addressInfo=address;
    }

    /**
     * Initialize Hotel object
     * @param hotelId the id of the hotel
     * @param hotelName the name of the hotel
     * @param address the address info of the hotel
     */
    public HotelModel(String hotelId,String hotelName,double rating,AddressModel address){
        this.rating=rating;
        this.hotelId=hotelId;
        this.hotelName=hotelName;
        this.addressInfo=address;
    }

    public HotelModel(String hotelId,String hotelName,String imageUrl,double rating,AddressModel addressInfo){
        this.rating=rating;
        this.hotelId=hotelId;
        this.hotelName=hotelName;
        this.imageUrl=imageUrl;
        this.addressInfo=addressInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public AddressModel getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressModel addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public int compareTo(Object o) {
        try{
            HotelModel h=(HotelModel)o;
            return this.hotelName.compareTo(h.getHotelName());
        }
        catch (Exception e){
            throw e;
        }

    }
}
