package cs601.socketServer.controllers;

import cs601.socketServer.basicLibraries.fileReaderBasic.FileReader;
import cs601.socketServer.basicLibraries.helperBasic.JsonHelper;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Authentication;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Get;
import cs601.socketServer.basicLibraries.httpBasic.contollerBasic.Controller;
import cs601.socketServer.basicLibraries.httpBasic.models.ActionResult;
import cs601.socketServer.databaseHandlers.HotelHandler;
import cs601.socketServer.models.HotelModel;

import java.awt.*;
import java.util.List;

/**
 * Created by Yiding Liu on 12/1/2016.
 */
@Authentication
public class HotelController extends Controller{
    @Override
    @Get
    public ActionResult index() {
        return new ActionResult(ActionResult.HTML, FileReader.getHtml("/profile"));
    }
    @Get
    public ActionResult getHotelList(String index,String count){
        int i=index==null?0:Integer.parseInt(index);
        int j=count==null?0:Integer.parseInt(count);

        HotelHandler hotelHandler= new HotelHandler();
        List<HotelModel> hotelModelList=hotelHandler.loadHotelList((i-1)*j,j);

        return new ActionResult(ActionResult.JSON, JsonHelper.hotelModelListToJson(hotelModelList));
    }
    @Get
    public ActionResult getNumberOfHotels(){
        HotelHandler hotelHandler=new HotelHandler();
        int hotels=hotelHandler.getNumberOfHotel();

        return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,String.valueOf(hotels)));
    }
    @Get
    public ActionResult getHotel(String hotelId){
        HotelHandler hotelHandler=new HotelHandler();
        HotelModel hotelModel=hotelHandler.loadHotel(hotelId);
        return new ActionResult(ActionResult.JSON,JsonHelper.hotelModelToJson(hotelModel));
    }
    @Get
    public ActionResult getHotelListByName(String name,String index,String count){
        int i=index==null?0:Integer.parseInt(index);
        int j=count==null?0:Integer.parseInt(count);

        HotelHandler hotelHandler= new HotelHandler();
        List<HotelModel> hotelModelList=hotelHandler.loadHotelListByName(name,(i-1)*j,j);

        return new ActionResult(ActionResult.JSON, JsonHelper.hotelModelListToJson(hotelModelList));
    }
    @Get
    public ActionResult getNumberOfHotelsByName(String name){
        HotelHandler hotelHandler=new HotelHandler();
        int hotels=hotelHandler.getNumberOfHotelByName(name);

        return new ActionResult(ActionResult.JSON,JsonHelper.creatMessage(true,String.valueOf(hotels)));
    }
}
