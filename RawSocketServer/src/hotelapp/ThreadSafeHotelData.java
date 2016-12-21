package  hotelapp;

import  concurrent.ReentrantReadWriteLock;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by dean on 9/28/2016.
 */
public class ThreadSafeHotelData extends HotelData {
    private ReentrantReadWriteLock reentrantLock;

    public ThreadSafeHotelData(){
        super();
        reentrantLock =new ReentrantReadWriteLock();
    }
    public ThreadSafeHotelData(TreeMap<String,Hotel> hotelList, TreeMap<String,HashMap<String,Review>> reviewList){
        super(hotelList,reviewList);
        reentrantLock=new ReentrantReadWriteLock();
    }
    /**
     * Thread safe add hotel
     * @param hotelId
     *            - the id of the hotel
     * @param hotelName
     *            - the name of the hotel
     * @param city
     *            - the city where the hotel is located
     * @param state
     *            - the state where the hotel is located.
     * @param streetAddress
     *            - the building number and the street
     * @param lat
     * @param lon
     */
    public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,
                    double lon,String country){
        try{
            reentrantLock.lockWrite();
            super.addHotel(hotelId,hotelName,city,state,streetAddress,lat,lon,country);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reentrantLock.unlockWrite();
        }
    }

    /**
     * Thread safe add review
     * @param hotelId
     *            - the id of the hotel reviewed
     * @param reviewId
     *            - the id of the review
     * @param rating
     *            - integer rating 1-5.
     * @param reviewTitle
     *            - the title of the review
     * @param review
     *            - text of the review
     * @param isRecom
     *            - whether the user recommends it or not
     * @param date
     *            - date of the review in the format yyyy-MM-dd, e.g.
     *            2016-08-29.
     * @param username
     *            - the nickname of the user writing the review.
     */
    public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
                          boolean isRecom, String date, String username){
        try{
            reentrantLock.lockWrite();
            return super.addReview(hotelId,reviewId,rating,reviewTitle,review,isRecom,date,username);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            reentrantLock.unlockWrite();
        }
    }

    /**
     * Thread safe add review batch
     * @param reviews
     */
    public void addReviewBatch(TreeMap<String,HashMap<String,Review>> reviews){
        try{
            reentrantLock.lockWrite();
            super.addReviewBatch(reviews);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            reentrantLock.unlockWrite();
        }
    }
    /**
     * Thread safe get hotel id list
     * @return
     */
    public List<String> getHotels(){
        try{
            reentrantLock.lockRead();
            return super.getHotels();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            reentrantLock.unlockRead();

        }
    }

    /**
     * Thread safe to load hotels info
     * @param jsonFilename
     *            the name of the json file that contains information about the
     */
//    public void loadHotelInfo(String jsonFilename){
//        try{
//            reentrantLock.lockWrite();
//            super.loadHotelInfo(jsonFilename);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            reentrantLock.unlockWrite();
//        }
//    }

    /**
     * Thread safe to load reviews info
     * @param path
     *            the path to the directory that contains json files with
     *            reviews Note that the directory can contain json files, as
     */
    public void loadReviews(Path path){
        try{
            reentrantLock.lockWrite();
            super.loadReviews(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reentrantLock.unlockWrite();
        }
    }

    /**
     * Thread safe toString
     * @param hotelId
     *            id
     * @return
     */
    public String toString(String hotelId){
        try{
            reentrantLock.lockRead();
            return super.toString(hotelId);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            reentrantLock.unlockRead();

        }
    }

    /**
     * Thread safe to print to file
     * @param filename
     */
    public void printToFile(Path filename){
        try{
            reentrantLock.lockRead();
            super.printToFile(filename);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reentrantLock.unlockRead();
        }
    }
}
