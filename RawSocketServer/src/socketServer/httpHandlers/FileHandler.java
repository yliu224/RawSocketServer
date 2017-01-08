package  socketServer.httpHandlers;

import  socketServer.basicLibraries.fileReaderBasic.FileReader;
import  socketServer.basicLibraries.helperBasic.FileType;
import  socketServer.basicLibraries.httpBasic.HttpConstant;
import  socketServer.basicLibraries.httpBasic.models.HttpRequest;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;
import  socketServer.basicLibraries.httpBasic.httpHandlerBasic.HttpBaseHandler;

import java.io.IOException;

/**
 * Created by yliu224 on 11/16/16.
 */
public class FileHandler extends HttpBaseHandler {
    //TODO:Optimize file handler
    private static final String textFile="js|css|html|doc|xml|eot|ttf|woff|woff2";
    private static final String imageFile="png|jpg|ico";
    @Override
    public void doGet(HttpRequest req, HttpResponse resp) {
        FileReader reader=new FileReader();
        String fileType=req.getPath().substring(req.getPath().lastIndexOf(".")+1);

        FileType file=FileType.UNDEFINED;

        switch (fileType){
            case "js":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_JS);
                file = FileType.COMMON_FILE;
                break;
            case "css":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_CSS);
                file = FileType.COMMON_FILE;
                break;
            case "html":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_HTML);
                file = FileType.COMMON_FILE;
                break;
            /***************font*****************/
            case "ttf":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_FONT);
                file = FileType.COMMON_FILE;
                break;
            case "woff":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_FONT);
                file = FileType.COMMON_FILE;
                break;
            case "woff2":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_FONT);
                file = FileType.COMMON_FILE;
                break;
            /***************iamge*****************/
            case "ico":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_ICON);
                file = FileType.IMAGE;
                break;
            case "png":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_PNG);
                file = FileType.IMAGE;
                break;
            case "jpg":
                resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_JPG);
                file = FileType.IMAGE;
                break;
        }




        switch (file) {
            case IMAGE:
                resp.setStatusCode(HttpConstant.SC_OK);
                try {
                    byte[] data = reader.getImage(req.getPath());
                    resp.sendByte(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case COMMON_FILE:
                resp.setStatusCode(HttpConstant.SC_OK);
                String content=reader.getFile(req.getPath())+"         ";
                resp.setContent(content);
                resp.send();
                break;
            default:
                resp.setStatusCode(HttpConstant.SC_NOT_FOUND);
                resp.setContent(reader.getHtml("/404"));
                resp.send();
        }
    }
}
