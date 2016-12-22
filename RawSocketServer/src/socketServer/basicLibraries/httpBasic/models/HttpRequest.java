package  socketServer.basicLibraries.httpBasic.models;

import socketServer.SocketServerDriver;
import  socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import  socketServer.basicLibraries.httpBasic.HttpConstant;
import  socketServer.basicLibraries.httpBasic.sessionBasic.HttpSession;
import  socketServer.basicLibraries.httpBasic.sessionBasic.SessionManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dean on 10/31/2016.
 */
public class HttpRequest {
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private Map<String,String> cookies;
    private String method;
    private String protocol;
    private String path;
    private final String regexParseHeader="(.*?): (.*)";
    private final String regexParseFirstLine="(.*?) ";
    private JSONObject json;
    private BufferedReader reader;

    public HttpRequest(BufferedReader reader){
        this.headers=new HashMap<>();
        this.parameters=new HashMap<>();
        this.reader=reader;
    }

    /**
     * Input a list of string which contains the http request header info
     * @param list
     */
    public void initializeHeaders(List<String> list){
        //parse first line
        try{
            String[] fl=list.get(0).split(" ");
            this.method =fl[0];
            this.protocol = fl.length==2?fl[1]:fl[2];
            this.path = fl.length==3?fl[1]:"";
        }
        catch (Exception e){
            StringBuilder sb=new StringBuilder();
            for(String s:list){
                sb.append(s+"///");
            }
            SocketServerDriver.log.error("HEADER ERROR+\t"+sb.length()+"\t"+sb.toString());
        }

        //Get header content
        Pattern p=Pattern.compile(regexParseHeader);
        for(int i=1;i<list.size();i++){
            Matcher m=p.matcher(list.get(i));
            if(m.find()){
                headers.put(m.group(1),m.group(2));
            }
        }
        //if it contains cookie,than parse the cookie
        parseCookie();

        //get parameters
        switch (method){
            case "GET":
                parseGetQueries();
                break;
            case "POST":
                parseFormData();
                break;
            default:
                break;
        }
    }

    public String getHeader(String name){
        return headers.get(name);
    }
    /**
     * Return specific parameter value,if not exist return null
     * @return
     */
    public String getParameter(String key){
        return parameters==null?null:this.parameters.get(key);
    }

    /**
     * Return number of elements in the parameters
     * @return
     */
    public int getParameterCount(){
        return parameters.size();
    }
    /**
     * Get cookie by name
     * @param key
     * @return
     */
    public String getCookie(String key){
        if(this.cookies!=null){
            return cookies.get(key);
        }
        return null;
    }

    /**
     * Check if there has parameters
     * @return
     */
    public boolean isEmptyParameters(){
        return parameters.isEmpty();
    }
    /**
     * Get all the cookies in the request header
     * @return
     */
    public Cookie[] getCookies(){
        if(cookies==null) return null;
        int num=cookies.size();
        Cookie[] c=new Cookie[num];
        int i=0;
        for(Map.Entry<String,String> e:cookies.entrySet()){
            c[i]=new Cookie(e.getKey(),e.getValue());
        }
        return c;
    }

    /**
     * Check if the request contains Json data
     * @return
     */
    public boolean hasJson(){
        if(this.json!=null){
            return true;
        }
        return false;
    }
    public String getHostName(){
        return getHeader("Host")==null?
                ConfigInfo.getConfig("domainName")+":"+ConfigInfo.getConfig("port"):
                getHeader("Host");
    }
    public String getMethod() {
        return method;
    }
    public String getProtocol() {
        return protocol;
    }
    public String getPath() {
        return path;
    }
    public JSONObject getJson() {
        return json;
    }

    /**
     * Get number of cookies
     * @return
     */
    public int getCookieCount(){
        return cookies==null?0:cookies.size();
    }
    /**
     * Set bufferedReader and get parameters from POST method
     */
    public void parseFormData(){
        String ct=this.headers.get(HttpConstant.H_CONTENT_TYPE);
        if(ct!=null){
            ct=ct.substring(0,ct.indexOf(";"));
        }
        switch (ct){
            case HttpConstant.CT_FORM_DATA:parsPostFormData();break;
            case HttpConstant.CT_JSON:parsPostJson();break;
            default:return;
        }
    }

    /**
     * Get a session
     * @return return a HttpSession if user is in the session
     */
    public HttpSession getSession(){
        if(cookies==null) return null;
        if(cookies.containsKey("session_id")){
            SessionManager sessionManager=SessionManager.getInstance();
            if(SessionManager.getInstance().isExist(cookies.get("session_id"))){
                HttpSession s=SessionManager.getInstance().getSession(cookies.get("session_id"));
                String sessionTime=ConfigInfo.getConfig("sessionTime");
                s.extendExpireDate(sessionTime==null?3600:Integer.parseInt(sessionTime));
                return s;
            }
        }
        return null;
    }

    /**
     * Return a string of all the info in the request.
     * This method only used for debug
     * @return
     */
    public String toString(){
        return null;
    }
    /******************************private methods begin******************************/
    /**
     * Parse path info
     */
    private void parseGetQueries(){
        int indexQ= path.indexOf('?');
        if(indexQ!=-1){
            String queryString= path.substring(indexQ+1, path.length());
            path = path.substring(0,indexQ);
            try {
                setParameters(queryString);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read form-data
     */
    private void parsPostFormData(){
        if(reader==null) return;
        StringBuilder sb=new StringBuilder();
        try {
            while(reader.ready()){
                sb.append((char)reader.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            setParameters(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse cookie if cookie is in the header
     */
    private void parseCookie(){
        if(headers.containsKey(HttpConstant.H_COOKIE)){
            cookies=new HashMap<>();
            String[] parameters=headers.get(HttpConstant.H_COOKIE).split(";");
            for(String s:parameters){
                int i=s.indexOf("=");
                cookies.put(s.substring(0,i).trim(),s.substring(i+1,s.length()));
            }
        }
        else{
            cookies=new HashMap<>();
            cookies.put("session_id","");
        }
    }

    /**
     * Parse the parameter string, and add them to the map.This method will decode url automatically
     * @param queryString the queryString should be like name=value&name=value&name=value...
     */
    private void setParameters(String queryString) throws UnsupportedEncodingException {
        if(queryString.equals("")||queryString==null) return;
        String[] queries=queryString.split("&");
        for(String s:queries){
            String value= URLDecoder.decode(s.substring(s.indexOf('=')+1,s.length()),"UTF-8");
            String name= URLDecoder.decode(s.substring(0,s.indexOf('=')),"UTF-8");
            parameters.put(name,value);
        }
    }

    /**
     * Parse json data in post request
     */
    private void parsPostJson(){
        if(reader==null) return;
        StringBuilder sb=new StringBuilder();
        try {
            while(reader.ready()){
                sb.append((char)reader.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.json=(JSONObject)new JSONParser().parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /******************************private methods end******************************/
}
