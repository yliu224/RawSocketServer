package  hotelapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * String format class
 */
public class FormatString {
    public static final String HOTEL_SEPERATOR ="********************";
    public static final String REVIEW_SEPERATOR ="--------------------";

    /**
     * Formate Hotel String
     * @param h hotel object
     * @return
     */
    public static String PrintHotel(Hotel h){
        Address a=h.getAddressInfo();
        StringBuilder output=new StringBuilder();
        output.append(h.getHotelName()+": "+h.getHotelId()+"\n");
        output.append(a.getStreetAddress()+"\n");
        output.append(a.getCity()+", "+a.getState()+"\n");

        return output.toString();
    }

    /**
     * Format Review String
     * @param reviews Review TreeMap
     * @return
     */
    public static String PrintReviews(List<Review> reviews){
        if(reviews.size()==0) return "";
        StringBuilder output=new StringBuilder();

        Collections.sort(reviews, new Comparator<Review>() {
            @Override
            public int compare(Review r1, Review r2) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    Date o1Date=df.parse(r1.getDate());
                    Date o2Date=df.parse(r2.getDate());

                    if(o1Date.after(o2Date)) return 1;
                    else if(o1Date.before(o2Date)) return -1;
                    else if(r1.getUserName().compareTo(r2.getUserName())!=0){
                        return r1.getUserName().compareTo(r2.getUserName());
                    }
                    else{
                        return r1.getReviewId().compareTo(r2.getReviewId());
                    }
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                return 0;
            }
        });

        for(Review r:reviews){
            output.append(REVIEW_SEPERATOR +"\n");
            output.append("Review by "+r.getUserName()+": "+r.getOverallRating()+"\n");
            output.append(r.getReviewTitle()+"\n");
            output.append(r.getReviewText()+"\n");
            //output+=formateDate(r.getDate())+"\n";
        }
        return output.toString();
    }

    /**
     * Convert datetime to date
     * @param date
     * @return
     */
    public static String formateDate(String date){
        return date.substring(0,10);
    }

    /**
     * Checks to see if a String is null or empty.
     *
     * @param text
     *            - String to check
     * @return true if non-null and non-empty
     */
    public static boolean isBlank(String text) {
        return (text == null) || text.trim().isEmpty();
    }

    /**
     * convert long time to EEE, dd-MMM-yyy ss:mm:hh z
     * @param date
     * @return
     */
    public static String dateToString(long date){
        SimpleDateFormat spf=new SimpleDateFormat("EEE, dd-MMM-yyy HH:mm:ss zzz");
        return spf.format(date);
    }

    /**
     * convert EEE, dd-MMM-yyy ss:mm:hh z to long time
     * @param date
     * @return
     */
    public static long stringToDate(String date){
        SimpleDateFormat spf=new SimpleDateFormat("EEE, dd-MMM-yyy HH:mm:ss zzz");
        Date d=new Date(0);
        try {
            d=spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }
}
