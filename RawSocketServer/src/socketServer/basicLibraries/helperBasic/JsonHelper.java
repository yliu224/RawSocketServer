package  socketServer.basicLibraries.helperBasic;

import  hotelapp.Address;
import  hotelapp.FormatString;
import  hotelapp.Hotel;
import  hotelapp.Review;
import  socketServer.models.AddressModel;
import  socketServer.models.HotelModel;
import  socketServer.models.ReviewModel;
import  socketServer.models.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * the format of json is
 * {
 *     "success":[true | false],
 *     "message":"Reason for the success status",
 *     "data"://data could be a object or array
 * }
 */
public class JsonHelper {
    /**
     * Return a simple JSON message
     * @param status
     * @param message
     * @return
     */
    public static JSONObject creatMessage(boolean status,String message){
        JSONObject obj=new JSONObject();
        obj.put("success",status);
        obj.put("message",message);
        return obj;
    }
    /**
     * Convert hotelModel to JSON object.It contains imgurl and all other details
     * @param hotel
     * @return
     */
    public static JSONObject hotelModelToJson(HotelModel hotel) {
        JSONObject base=createBasicJson();

        JSONObject hotelDetails=hotelModelJson(hotel);
        hotelDetails.put("imgUrl",hotel.getImageUrl());

        base.put("data",hotelDetails);
        return base;
    }

    /**
     * convert reviewMode to JSON object
     * @param review
     * @return
     */
    public static JSONObject reviewModelToJson(ReviewModel review){
        JSONObject base=createBasicJson();
        JSONObject obj=reviewModeJson(review);

        base.put("data",obj);

        return base;
    }

    /**
     * convert userModel to JSON object
     * @param user
     * @return
     */
    public static JSONObject userModelToJson(UserModel user){
        JSONObject base=createBasicJson();
        JSONObject obj=new JSONObject();

        obj.put("username",user.getName());
        obj.put("id",String.valueOf(user.getUserId()));
        if(user.getLastLoginTime()==null){
            obj.put("lastLoginTime","");
        }
        else{
            obj.put("lastLoginTime", FormatString.dateToString(user.getLastLoginTime().getTime()));
        }
        base.put("data",obj);

        return base;
    }

    /**
     * Convert a list of hotels into json
     * @param hotelModelList
     * @return
     */
    public static JSONObject hotelModelListToJson(List<HotelModel> hotelModelList){
        JSONObject base=createBasicJson();
        JSONArray array=new JSONArray();
        for(HotelModel hotel:hotelModelList){
            array.add(hotelModelJson(hotel));
        }
        base.put("data",array);

        return base;
    }

    /**
     * Convert a review list into json
     * @param reviewModelList
     * @return
     */
    public static JSONObject reviewModelListToJson(List<ReviewModel> reviewModelList){
        JSONObject base=createBasicJson();
        JSONArray array=new JSONArray();
        for(ReviewModel review:reviewModelList){
            JSONObject obj=reviewModeJson(review);
            obj.put("isLiked",review.getIsLiked());
            array.add(obj);
        }
        base.put("data",array);

        return base;
    }

    /**
     * Convert Json to review model
     * @param obj
     * @return
     */
    public static ReviewModel jsonToReviewMode(JSONObject obj){
        String reviewId=(String)obj.get("reviewId");
        String hotelId=(String)obj.get("hotelId");
        String reviewTitle=(String)obj.get("reviewTitle");
        String reviewText=(String)obj.get("reviewText");
        String userName=(String)obj.get("userName");
        //String date=(String)obj.get("date");
        int overallRating=1;
        try{
            overallRating=Integer.parseInt((String)obj.get("overallRating"));
        }
        catch (Exception e){

        }
        boolean isRecom=(Boolean) obj.get("isRecom");

        return new ReviewModel(reviewId,hotelId,reviewTitle,reviewText,userName,"",overallRating,isRecom);
    }


/**********************private method**********************/
    /**
     * create basic json
     * @return
     */
    private static JSONObject createBasicJson(){
        JSONObject obj=new JSONObject();
        obj.put("success", true);
        obj.put("message","Success");
        obj.put("data",null);

        return obj;
    }

    /**
     * Pure json object,mapped with HotelModel
     * @param hotel
     * @return
     */
    private static JSONObject hotelModelJson(HotelModel hotel){
        JSONObject obj = new JSONObject();
        //create json
        AddressModel add = hotel.getAddressInfo();

        obj.put("hotelId", hotel.getHotelId());
        obj.put("name", hotel.getHotelName());
        obj.put("addr", add.getStreetAddress());
        obj.put("city", add.getCity());
        obj.put("state", add.getState());
        obj.put("lat", add.getLatitude());
        obj.put("lng", add.getLongitude());
        obj.put("country", add.getCountry());
        obj.put("rating",hotel.getRating());

        return obj;
    }

    /**
     * Pure json object,mapped with ReviewMode
     * @param review
     * @return
     */
    private static JSONObject reviewModeJson(ReviewModel review){
        JSONObject obj=new JSONObject();

        obj.put("hotelId",review.getHotelId());
        obj.put("reviewId",review.getReviewId());
        obj.put("userName",review.getUserName());
        obj.put("reviewTitle",review.getReviewTitle());
        obj.put("reviewText",review.getReviewText());
        obj.put("reviewDate",review.getDate());
        obj.put("isRecom",review.getIsRecom());
        obj.put("overallRating",review.getOverallRating());
        obj.put("like",review.getLike());

        return obj;
    }
}
