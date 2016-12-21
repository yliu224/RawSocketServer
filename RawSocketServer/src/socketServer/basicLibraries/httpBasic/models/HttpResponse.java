package  socketServer.basicLibraries.httpBasic.models;

import  socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import  socketServer.basicLibraries.helperBasic.SystemConstant;
import  socketServer.basicLibraries.httpBasic.HttpConstant;
import  socketServer.basicLibraries.httpBasic.httpHandlerBasic.HandlerSet;
import  socketServer.basicLibraries.httpBasic.sessionBasic.HttpSession;
import  socketServer.basicLibraries.httpBasic.sessionBasic.SessionManager;
import  socketServer.models.UserModel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dean on 10/31/2016.
 */
public class HttpResponse {
    private String statusCode;
    private String content;
    private String protocolType;
    private OutputStream out;
    private Map<String,String> responseHeader;


    /**
     * In the constructor,it will set statusCode=405,protocolType="HTTP/1.1",contentType="text/html",Server="DeanDing" by default
     * @param out
     */
    public HttpResponse(OutputStream out){
        this.responseHeader=new HashMap<>();
        this.out=out;
        //set response header
        this.statusCode= HttpConstant.SC_METHOD_NOT_ALLOWED;
        this.content="";
        this.protocolType="HTTP/1.1";

        setHeader(HttpConstant.H_CONTENT_TYPE,HttpConstant.CT_HTML);
        setHeader("Server","DeanDing Alpha");
        setHeader("Contact-Info","Looking for 2017 summer intern :);ydliu1993@gmail.com");
    }

    /**
     * Return the value of element in the head
     * @param headerName
     * @return if the name exists return the specific value,otherwise return null
     */
    public String getHeader(String headerName) {
        return responseHeader.get(headerName);
    }

    /**
     * Ret name and value of header.If the name has already existed,the value of the name will be updated,otherwise it will insert a new value in the header
     * @param headerName
     * @param headerValue
     */
    public void setHeader(String headerName, String headerValue) {
        responseHeader.put(headerName,headerValue);
    }
    public void setContentType(String contentType){
        setHeader(HttpConstant.H_CONTENT_TYPE,contentType);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    /**
     * It will set Content-Length in the header automatically
     * @param content
     */
    public void setContent(String content) {
        this.content = content;//在这个位置this.content=content+"  "
        responseHeader.put("Content-Length",String.valueOf(content.length()));
    }

    /**
     * Redirect request to another page!
     * @param hostname
     * @param path
     */
    public void urlRedirectSetting(String hostname,String path){
        //responseHeader.clear();
        setStatusCode(HttpConstant.SC_REDIRECTION);
        setHeader(HttpConstant.H_LOCATION,"http://"+hostname+path);
    }



    /**
     * default response msg will be:
     * statusCode="405 SC_METHOD_NOT_ALLOWED";
     * content="";
     * protocolType="HTTP/1.1";
     * contentType="text/html;charset=ISO-8859-1";
     * @return formatted response http string
     */
    public String CreateResponse(){
        StringBuilder sb=new StringBuilder();
        sb.append(this.protocolType+" "+this.statusCode+System.lineSeparator()); // Response Header
        for(Map.Entry<String,String> e: responseHeader.entrySet()){
            sb.append(e.getKey()+": "+e.getValue()+System.lineSeparator());
        }
        //set content
        sb.append(System.lineSeparator());
        sb.append(this.content);
        return sb.toString();
    }

    /**
     * send the response back to the client
     */
    public void send(){
        PrintWriter writer=new PrintWriter(out);
        writer.println(CreateResponse());
        writer.flush();
    }

    /**
     * send bytes to the client
     * @param bytes
     */
    public void sendByte(byte[] bytes) throws IOException {
        //setContent("");
        PrintWriter writer=new PrintWriter(out);
        writer.print(CreateResponse());
        writer.flush();
        out.write(bytes);
        out.flush();
    }
    /************************************private method begin************************************/
    /************************************private method end************************************/
}
