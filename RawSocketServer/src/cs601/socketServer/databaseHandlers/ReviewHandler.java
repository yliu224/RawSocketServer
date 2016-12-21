package cs601.socketServer.databaseHandlers;

import cs601.hotelapp.FormatString;
import cs601.socketServer.basicLibraries.databaseBasic.SQLBaseHandler;
import cs601.socketServer.basicLibraries.helperBasic.SystemConstant;
import cs601.socketServer.models.ReviewModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yiding Liu on 11/15/2016.
 */
public class ReviewHandler extends SQLBaseHandler {
    private static final String ADD_REVIEW = "INSERT INTO reviews (reviewid, hotelid, reviewtitle, reviewtext, username, date, overallrating, isrecom ,like_review) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String EDIT_REVIEW = "UPDATE reviews SET reviewtitle=?,reviewtext=?,overallrating=?,isrecom=? WHERE reviewid=?";
    private static final String GET_LIKES="SELECT like_review FROM reviews WHERE reviewid=?";
    private static final String IS_ADDED_REVIEW="SELECT * FROM reviews WHERE username=? AND hotelid=?";
    private static final String RATING_SQL = "SELECT AVG(overallrating) FROM reviews WHERE hotelid = ?";

    private static final String SEARCH_REVIEW_BY_DATE = "SELECT * FROM reviews LEFT OUTER JOIN like_review ON reviewid=review_id AND user_name=?  WHERE hotelid = ? ORDER BY date DESC LIMIT ?,?";
    private static final String SEARCH_REVIEW_BY_RATING = "SELECT * FROM reviews LEFT OUTER JOIN like_review ON reviewid=review_id AND user_name=? WHERE hotelid = ? ORDER BY overallrating DESC LIMIT ?,?";

    private static final String GET_NUMBER_OF_REVIEWS="SELECT COUNT(*) as number FROM reviews WHERE hotelid= ? ";
    private static final String GET_REVIEW="SELECT * FROM reviews WHERE username=? AND hotelid=?";
    private static final String DELETE_REVIEW="DELETE FROM reviews WHERE reviewId=?";

    private static final String IS_LIKED_REVIEW="SELECT * FROM like_review WHERE review_id=? AND user_name=?";
    private static final String ADD_LIKE_REVIEW="INSERT INTO like_review (review_id,user_name) VALUES (?,?)";
    private static final String DELETE_LIKE_REVIEW="DELETE FROM like_review WHERE review_id=?";
    private static final String DELETE_LIKE_REVIEW_BY_USER="DELETE FROM like_review WHERE review_id=? AND user_name=?";
    private static final String LIKE_REVIEW = "UPDATE reviews SET like_review=? WHERE reviewid=?";
    private static final String DISLIKE_REVIEW = "UPDATE reviews SET like_review=? WHERE reviewid=?";

    /**
     * Add multiple reviews
     */
    public int addMultipleReview(List<ReviewModel> reviews){
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(ADD_REVIEW);) {
                for(ReviewModel review:reviews){
                    statement.setString(1, review.getReviewId());
                    statement.setString(2, review.getHotelId());
                    statement.setString(3, review.getReviewTitle());
                    statement.setString(4, review.getReviewText());
                    statement.setString(5, review.getUserName().toString());
                    statement.setInt(7, review.getOverallRating());
                    statement.setBoolean(8, review.getIsRecom());

                    SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-DD'T'hh:mm:ss'Z'");
                    Date d=new Date();
                    try {
                        d = sdf.parse(review.getDate());
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                    ;
                    statement.setDate(6, new java.sql.Date(d.getTime()));
                    statement.addBatch();
                }
                //System.out.println("Finish Batch!");
                int[] ruslts=statement.executeBatch();
                //System.out.println("Finish Execution!");

                int cnt=0;
                for(int i:ruslts){
                    if(i>0) cnt++;
                }

                if (cnt>0) {
                    return cnt;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**
     * Add on review
     * @param review
     * @return
     */
    public ReviewModel addReview(ReviewModel review){
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(ADD_REVIEW);) {
                long id=System.currentTimeMillis();
                statement.setString(1, String.valueOf(id));
                statement.setString(2, review.getHotelId());
                statement.setString(3, review.getReviewTitle());
                statement.setString(4, review.getReviewText());
                statement.setString(5, review.getUserName().toString());
                statement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                statement.setInt(7, review.getOverallRating());
                statement.setBoolean(8, review.getIsRecom());
                statement.setInt(9,0);

                //System.out.println("Finish Batch!");
                int results = statement.executeUpdate();
                //System.out.println("Finish Execution!");

                if (results > 0) {
                    review.setReviewId(String.valueOf(id));
                    return review;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }

    /**
     * Aet over all rating of a specific hotel
     * @param hotelId
     * @return
     */
    public double getOverallRating(String hotelId){
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(RATING_SQL);) {
                statement.setString(1, hotelId);

                ResultSet resultSet=statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getDouble(1);
                }
                else{
                    return 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**
     * Return a review list of a specific hotel
     * @param hotelId
     * @param index start index
     * @param count the number of reviews you need
     * @return
     */
    public List<ReviewModel> loadReviews(String hotelId,String orderBy,int index,int count,String userName){
        List<ReviewModel> list=new ArrayList<>();
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {
            String SQL="";
            switch (orderBy){
                case "rating":
                    SQL=SEARCH_REVIEW_BY_RATING;
                    break;
                case "date":
                    SQL=SEARCH_REVIEW_BY_DATE;
                    break;
                default:
                    SQL=SEARCH_REVIEW_BY_DATE;
            }
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(SQL);) {
                statement.setString(1, userName);
                statement.setString(2, hotelId);
                statement.setInt(3, index);
                statement.setInt(4, count);


                ResultSet resultSet=statement.executeQuery();
                while(resultSet.next()){
                    list.add(reviewModelFactory(resultSet));
                }
                return list;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return list;
    }

    /**
     * Get the total number of reviews of this hotel
     * @return
     */
    public int getNumberOfReviews(String hotelId){
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_REVIEWS);) {
                statement.setString(1,hotelId);

                ResultSet resultSet=statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("number");
                }
                return 0;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**
     * Modify the review
     * @param review
     * @return
     */
    public ReviewModel editReview(ReviewModel review){
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(EDIT_REVIEW);) {
                statement.setString(1, review.getReviewTitle());
                statement.setString(2, review.getReviewText());
                statement.setInt(3, review.getOverallRating());
                statement.setBoolean(4, review.getIsRecom());
                statement.setString(5, review.getReviewId());

                //System.out.println("Finish Batch!");
                int results = statement.executeUpdate();
                //System.out.println("Finish Execution!");

                if (results > 0) {
                    return review;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }

    /**
     * Check if the the user has already added a review int the hotel
     * @param userName
     * @param hotelId
     * @return If the user has already added a review ,return true,otherwise return false
     */
    public boolean isAddedReview(String userName,String hotelId){
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(IS_ADDED_REVIEW);) {
                statement.setString(1,userName);
                statement.setString(2,hotelId);

                ResultSet resultSet=statement.executeQuery();
                if(resultSet.next()){
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return true;
    }

    /**
     * Add one like on this review
     * @param reviewId
     * @return
     */
    public boolean likeReview(String reviewId,String userName){
        try (Connection connection = db.getConnection();) {
            if(isLikedReview(reviewId,userName,connection)) return false;
            int likes=getLikes(reviewId,connection);
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(LIKE_REVIEW);) {

                statement.setInt(1,likes+1);
                statement.setString(2,reviewId);

                int result=statement.executeUpdate();
                if(result!=0){
                    addLikeReview(reviewId,userName,connection);
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return true;
    }

    /**
     * Get a review based on the username and hotelId
     * @param userName
     * @param hotelId
     * @return
     */
    public ReviewModel getReview(String userName,String hotelId){
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(GET_REVIEW);) {
                statement.setString(1,userName);
                statement.setString(2,hotelId);

                ResultSet resultSet=statement.executeQuery();
                if(resultSet.next()){
                    return reviewModelFactory(resultSet);
                }
                else{
                    return null;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }

    /**
     * Delete one review
     * @param reviewId
     * @return
     */
    public boolean deleteReview(String reviewId){
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW);) {
                statement.setString(1,reviewId);

                int result=statement.executeUpdate();
                if(result!=0){
                    deleteLikeReview(reviewId,connection);
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return false;
    }

    /**
     * Dislike one review
     * @param reviewId
     * @param userName
     * @return
     */
    public boolean disLikeReview(String reviewId,String userName){
        try (Connection connection = db.getConnection();) {
            int likes=getLikes(reviewId,connection);
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(DISLIKE_REVIEW);) {

                statement.setInt(1,likes-1);
                statement.setString(2,reviewId);

                int result=statement.executeUpdate();
                if(result!=0){
                    deleteLikeReviewByUser(reviewId,userName,connection);
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return true;
    }
    /**************************private functions begin**************************/
    /**
     * Get number of the like of one reivew
     * @param reviewId
     * @param connection
     * @return
     */
    private int getLikes(String reviewId,Connection connection){
        try (PreparedStatement statement = connection.prepareStatement(GET_LIKES);) {
            statement.setString(1,reviewId);

            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("like_review");
            }
            else{
                return 0;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Check if this liked this review
     * @param reviewId
     * @param userName
     * @param connection
     * @return
     */
    private boolean isLikedReview(String reviewId,String userName,Connection connection){
        try (PreparedStatement statement = connection.prepareStatement(IS_LIKED_REVIEW);) {
            statement.setString(1,reviewId);
            statement.setString(2,userName);

            ResultSet result=statement.executeQuery();
            if(result.next()){
                return true;
            }
            else{
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Add user into the like_review to build a relationship with the like and user
     * @param reviewId
     * @param userName
     * @param connection
     * @return
     */
    private boolean addLikeReview(String reviewId,String userName,Connection connection){
        try (PreparedStatement statement = connection.prepareStatement(ADD_LIKE_REVIEW);) {
            statement.setString(1,reviewId);
            statement.setString(2,userName);

            int result=statement.executeUpdate();
            if(result!=0){
                return true;
            }
            else{
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete a review in the like_review table
     * @param reviewId
     * @return
     */
    private boolean deleteLikeReview(String reviewId,Connection connection){
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LIKE_REVIEW);) {
            statement.setString(1,reviewId);

            int result=statement.executeUpdate();
            if(result!=0){
                return true;
            }
            else{
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Delete a review in the like_review table by userName
     * @param reviewId
     * @return
     */
    private boolean deleteLikeReviewByUser(String reviewId,String userName,Connection connection){
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LIKE_REVIEW_BY_USER);) {
            statement.setString(1,reviewId);
            statement.setString(2,userName);

            int result=statement.executeUpdate();
            if(result!=0){
                return true;
            }
            else{
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    private ReviewModel reviewModelFactory(ResultSet resultSet) throws SQLException{
        String reviewId=resultSet.getString("reviewid");
        String hotelId=resultSet.getString("hotelid");
        String revieTitle=resultSet.getString("reviewtitle");
        String reviewText=resultSet.getString("reviewtext");
        String username=resultSet.getString("username");
        Date date=resultSet.getDate("date");
        int overallRating=resultSet.getInt("overallrating");
        boolean isRecom=resultSet.getBoolean("isrecom");
        int like=resultSet.getInt("like_review");
        String user_name=null;
        try{
            user_name=resultSet.getString("user_name");
        }
        catch (SQLException e){}
        boolean isLiked=user_name==null?false:true;
        return new ReviewModel(reviewId,hotelId,revieTitle,reviewText,username,date.toString(),overallRating,isRecom,like,isLiked);
    }
    /**************************private functions end**************************/
}
