package cs601.socketServer.controllers;

import cs601.socketServer.basicLibraries.fileReaderBasic.FileReader;
import cs601.socketServer.basicLibraries.helperBasic.JsonHelper;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Authentication;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Get;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Post;
import cs601.socketServer.basicLibraries.httpBasic.contollerBasic.Controller;
import cs601.socketServer.basicLibraries.httpBasic.models.ActionResult;
import cs601.socketServer.databaseHandlers.ReviewHandler;
import cs601.socketServer.models.ReviewModel;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by Yiding Liu on 12/7/2016.
 */
@Authentication
public class ReviewController extends Controller {
    @Override
    @Get
    public ActionResult index() {
        return new ActionResult(ActionResult.HTML, FileReader.getHtml("/profile"));
    }
    @Get
    public ActionResult getReviewList(String hotelId,String orderBy,String index,String count,String userName){
        int i=index==null?0:Integer.parseInt(index);
        int j=count==null?0:Integer.parseInt(count);

        ReviewHandler reviewHandler = new ReviewHandler();
        List<ReviewModel> reviewModelList= reviewHandler.loadReviews(hotelId,orderBy,(i-1)*j,j,userName);

        return new ActionResult(ActionResult.JSON, JsonHelper.reviewModelListToJson(reviewModelList));
    }
    @Get
    public ActionResult getReviewNumbers(String hotelId){
        ReviewHandler reviewHandler =new ReviewHandler();
        int reviews= reviewHandler.getNumberOfReviews(hotelId);

        return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,String.valueOf(reviews)));
    }
    @Post
    public ActionResult addReview(JSONObject json){
        ReviewModel reviewModel=JsonHelper.jsonToReviewMode(json);

        ReviewHandler reviewHandler =new ReviewHandler();
        ReviewModel addedReview= reviewHandler.addReview(reviewModel);

        if(addedReview!=null){
            return new ActionResult(ActionResult.JSON,JsonHelper.reviewModelToJson(addedReview));
        }
        else{
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Failed"));
        }
    }

    /**
     * Check this user if he has already added review or not
     */
    @Get
    public ActionResult isAddedReview(String userName,String hotelId){
        ReviewHandler reviewHandler =new ReviewHandler();
        boolean isAdded= reviewHandler.isAddedReview(userName,hotelId);

        if(isAdded){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"Is Added"));
        }
        else {
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Is not Added"));
        }
    }
    @Get
    public ActionResult getReview(String userName,String hotelId){
        ReviewHandler reviewHandler =new ReviewHandler();
        ReviewModel reviewModel= reviewHandler.getReview(userName,hotelId);

        if(reviewModel!=null){
            return new ActionResult(ActionResult.JSON,JsonHelper.reviewModelToJson(reviewModel));
        }
        else{
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"No review found"));
        }
    }
    @Post
    public ActionResult editReview(JSONObject json){
        ReviewHandler reviewHandler =new ReviewHandler();
        ReviewModel reviewModel=JsonHelper.jsonToReviewMode(json);

        ReviewModel editedReview= reviewHandler.editReview(reviewModel);

        if(editedReview!=null){
            return new ActionResult(ActionResult.JSON,JsonHelper.reviewModelToJson(editedReview));
        }
        else {
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Failed"));
        }
    }

    /**
     * Get rating of specific hotel
     * @param hotelId
     * @return
     */
    @Get
    public ActionResult getRating(String hotelId){
        ReviewHandler reviewHandler =new ReviewHandler();
        double rating= reviewHandler.getOverallRating(hotelId);

        return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,String.valueOf(rating)));
    }
    @Get
    public ActionResult likeReview(String reviewId,String userName){
        ReviewHandler reviewHandler =new ReviewHandler();
        boolean success= reviewHandler.likeReview(reviewId,userName);

        if(success){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"OK"));
        }
        else{
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Failed"));
        }
    }
    @Get
    public ActionResult disLikeReview(String reviewId,String userName){
        ReviewHandler reviewHandler=new ReviewHandler();
        boolean success=reviewHandler.disLikeReview(reviewId,userName);

        if(success){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"Dislike success"));
        }
        else {
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Dislike failed"));
        }
    }
    @Get
    public ActionResult deleteReview(String reviewId){
        ReviewHandler reviewHandler =new ReviewHandler();
        boolean deleted= reviewHandler.deleteReview(reviewId);

        if(deleted){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"Deleted"));
        }
        else{
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Not Deleted"));
        }
    }
}
