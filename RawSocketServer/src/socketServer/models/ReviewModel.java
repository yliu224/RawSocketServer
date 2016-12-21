package  socketServer.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dean on 9/1/2016.
 * Basic Review info
 */
public class ReviewModel implements Comparable{
    private String reviewId;
    private String hotelId;
    private String reviewTitle;
    private String reviewText;
    private String userName;
    private String date;
    private int overallRating;
    private boolean isRecom;
    private int like;
    private boolean isLiked;

    public final static String ANONYMOUS="anonymous";

    /**
     * Initialize the Review Object
     * @param reviewId review id
     * @param hotelId hotel id
     * @param reviewTitle review title
     * @param reviewText review text
     * @param userName username or nick name
     * @param date post date
     * @param overallRating rating
     * @param isRecom whether it's recommended or not
     */
    public ReviewModel(String reviewId, String hotelId, String reviewTitle, String reviewText, String userName, String date, int overallRating, boolean isRecom) {
        this.reviewId = reviewId;
        this.hotelId = hotelId;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.userName = userName;
        this.date = date;
        this.overallRating = overallRating;
        this.isRecom = isRecom;
    }

    public ReviewModel(String reviewId, String hotelId, String reviewTitle, String reviewText, String userName, String date, int overallRating, boolean isRecom,int like) {
        this.reviewId = reviewId;
        this.hotelId = hotelId;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.userName = userName;
        this.date = date;
        this.overallRating = overallRating;
        this.isRecom = isRecom;
        this.like=like;
    }

    public ReviewModel(String reviewId, String hotelId, String reviewTitle, String reviewText, String userName, String date, int overallRating, boolean isRecom,int like,boolean isLiked) {
        this.reviewId = reviewId;
        this.hotelId = hotelId;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.userName = userName;
        this.date = date;
        this.overallRating = overallRating;
        this.isRecom = isRecom;
        this.like=like;
        this.isLiked=isLiked;
    }
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public boolean getIsRecom() {
        return isRecom;
    }

    public void setIsRecom(boolean recom) {
        isRecom = recom;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public int compareTo(Object o) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            ReviewModel r=(ReviewModel)o;
            Date thisDate=df.parse((this.date));
            Date objDate=df.parse(r.getDate());

            if(thisDate.after(objDate)) return 1;
            else if(thisDate.before(objDate)) return -1;
            else if(userName.compareTo(r.getUserName())!=0){
                return -this.userName.compareTo(r.getUserName());
            }
            else{
                  return this.userName.compareTo(r.getReviewId());
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

}
