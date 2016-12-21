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
    @Override
    public void doGet(HttpRequest req, HttpResponse resp) {
        FileReader reader=new FileReader();

        FileType file=null;

        if(req.getPath().endsWith(".js")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_JS);
            file = FileType.COMMON_FILE;
        }
        else if(req.getPath().endsWith(".css")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_CSS);
            file = FileType.COMMON_FILE;
        }
        else if(req.getPath().endsWith(".svg")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_SVG);
            file = FileType.COMMON_FILE;
        }
        else if(req.getPath().endsWith(".ico")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_ICON);
            file=FileType.IMAGE;
        }
        else if(req.getPath().toLowerCase().endsWith(".jpg")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_JPG);
            file=FileType.IMAGE;
        }
        else if(req.getPath().toLowerCase().endsWith(".png")){
            resp.setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_PNG);
            file=FileType.IMAGE;
        }


        resp.setStatusCode(HttpConstant.SC_OK);

        switch (file) {
            case IMAGE:
                try {
                    byte[] data = reader.getImage(req.getPath());
                    resp.sendByte(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case COMMON_FILE:
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
