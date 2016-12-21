package cs601.socketServer.controllers;

import cs601.socketServer.basicLibraries.helperBasic.JsonHelper;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Authentication;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Get;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Post;
import cs601.socketServer.basicLibraries.httpBasic.contollerBasic.Controller;
import cs601.socketServer.basicLibraries.httpBasic.models.ActionResult;
import cs601.socketServer.basicLibraries.fileReaderBasic.FileReader;
import cs601.socketServer.basicLibraries.httpBasic.sessionBasic.HttpSession;
import cs601.socketServer.basicLibraries.httpBasic.sessionBasic.SessionManager;
import cs601.socketServer.databaseHandlers.UserHandler;
import cs601.socketServer.models.UserModel;
import org.json.simple.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yliu224 on 11/27/16.
 */
public class HomeController extends Controller {
    @Override
    @Get
    public ActionResult index() {
        return new ActionResult(ActionResult.HTML,FileReader.getHtml("/"));
    }
    @Get
    public ActionResult login(String session_id){
        return new ActionResult(ActionResult.HTML,FileReader.getHtml("/profile"));
    }
    @Post
    public ActionResult login(JSONObject json){
        String username=(String) json.get("username");
        String password= (String) json.get("password");
        UserHandler userHandler=new UserHandler();
        UserModel user=userHandler.loginUser(username,password);
        //UserModel user=new UserModel("1","test");
        if(user!=null){
            //log in success
            userHandler.updateLoginTime(user.getUserId());
            return new ActionResult(ActionResult.LOG_IN_SUCCESS, user);
        }
        else{
            //log in failed
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Invalid user name or password"));
        }
    }
    @Get
    public ActionResult register(String session_id){
        if(SessionManager.getInstance().isExist(session_id)){
            return new ActionResult(ActionResult.REDIRECT,"/Hotel");
        }
        else{
            return new ActionResult(ActionResult.HTML,FileReader.getHtml("/register"));
        }
    }

    @Post
    public ActionResult register(String username,String password){
        UserHandler userHandler=new UserHandler();
        if(password.length()<6){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Password too short!"));
        }
        String regex="[~!@#$%^&*()_+{}<>?]+";
        Pattern p= Pattern.compile(regex);
        Matcher m=p.matcher(password);
        if(!m.find()){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Password should contain at least one special character"));
        }

        //Register into database
        UserModel user=userHandler.registerUser(username,password);
        if(user!=null){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"Register success!"));
        }
        else{
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"Duplicate username"));
        }
    }
    @Post
    public ActionResult logout(String session_id){
        SessionManager.getInstance().clearSession(session_id);
        return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,"Session Deleted"));
    }
    @Get
    public ActionResult getUserInfo(String session_id){
        HttpSession session=SessionManager.getInstance().getSession(session_id);

        if(session==null){
            return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(false,"User not exist in the session"));
        }
        else {
            UserHandler userHandler=new UserHandler();
            UserModel user=userHandler.getUser(session.getUserId());
            return new ActionResult(ActionResult.JSON,JsonHelper.userModelToJson(user));
        }
    }
}
