package cs601.socketServer.basicLibraries.httpBasic.httpHandlerBasic;

import cs601.socketServer.basicLibraries.httpBasic.models.HttpRequest;
import cs601.socketServer.basicLibraries.httpBasic.models.HttpResponse;

import java.util.TreeMap;

/**
 * Created by yliu224 on 11/6/16.
 */
public class HttpBaseHandler {
    private TreeMap<String,Object> attributes;

    public HttpBaseHandler(){

    }

    public void doGet(HttpRequest req, HttpResponse resp){
        resp.setContent("Method not allowed");
        resp.send();
    }

    public void doPost(HttpRequest req,HttpResponse resp){
        resp.setContent("Method not allowed");
        resp.send();
    }

}
