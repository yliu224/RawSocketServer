import hotelapp.*;
import socketServer.databaseHandlers.HotelHandler;
import socketServer.databaseHandlers.ReviewHandler;
import socketServer.databaseHandlers.UserHandler;
import socketServer.models.AddressModel;
import socketServer.models.HotelModel;
import socketServer.models.ReviewModel;
import socketServer.models.UserModel;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataImporter {
    public static void main(String[] args) {
        ThreadSafeHotelData data=new ThreadSafeHotelData();
        HotelDataBuilder builder=new HotelDataBuilder(data);

        //load hotel info and reviews
        builder.loadHotelInfo("input/hotels200.json");
        //builder.loadReviews(Paths.get("input" + File.separator + "reviews8000"));
        builder.loadReviews(Paths.get("input" + File.separator + "reviews"));

        System.out.println("Loading data...");
        builder.waitUntilFinished();
        builder.shutdown();
        System.out.println("Loading complete! :)");
        //importUsers(data);
        //addReviews(data);
    }
    public static void addHotel(ThreadSafeHotelData data){
        List<HotelModel> lh=new ArrayList<>();
        HotelHandler hh=new HotelHandler();
        for(Hotel hotel:data.gethotelList().values()){
            Address addOld=hotel.getAddressInfo();
            AddressModel add=new AddressModel(addOld.getStreetAddress(),
                    addOld.getState(),
                    addOld.getCity(),
                    addOld.getLongitude(),
                    addOld.getLatitude(),
                    addOld.getCountry());
            HotelModel h=new HotelModel(hotel.getHotelId(),hotel.getHotelName(),add);
            lh.add(h);
        }
        System.out.println(hh.addMultipleHotel(lh));
    }

    public static void addReviews(ThreadSafeHotelData data){
        List<ReviewModel> lr=new ArrayList<>();

        for(Map<String,Review> mr:data.getreviewList().values()){
            for(Review r:mr.values()){
                ReviewModel reviewModel=new ReviewModel(r.getReviewId(),
                        r.getHotelId(),
                        r.getReviewTitle(),
                        r.getReviewText(),
                        r.getUserName(),
                        r.getDate(),
                        r.getOverallRating(),
                        r.getIsRecom());
                lr.add(reviewModel);
            }
        }
        ReviewHandler rh=new ReviewHandler();
        rh.addMultipleReview(lr);
    }

    public static void importUsers(ThreadSafeHotelData data){
        List<String> lu=new ArrayList<>();

        for(Map<String,Review> mr:data.getreviewList().values()){
            for(Review r:mr.values()){
                UserModel user=new UserModel(-1,r.getUserName());
                if(!lu.contains(user.getName())&&user.getName()!="anonymous")
                    lu.add(user.getName());
            }
        }

        UserHandler uh=new UserHandler();
        System.out.println(lu.size());
        int i=0;
        for(String username:lu){
            uh.registerUser(username,"123456!");
            System.out.println(i++);
        }
    }
}

