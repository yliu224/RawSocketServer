package  socketServer.basicLibraries.httpBasic;

/**
 * Created by dean on 10/31/2016.
 */
public class HttpConstant {
    //status code
    public static final String SC_OK="200 OK";
    public static final String SC_METHOD_NOT_ALLOWED="405 Method not allowed";
    public static final String SC_NOT_FOUND="404 Not found";
    public static final String SC_REDIRECTION="303 See Other";
    public static final String SC_INTERNAL_ERROR="500 Internal Server Error";
    //content type
    public static final String CT_HTML="text/html";//charset=ISO-8859-1";
    public static final String CT_JSON="application/json";
    public static final String CT_FORM_DATA="application/x-www-form-urlencoded";
    public static final String CT_CSS="text/css";
    public static final String CT_JS="text/javascript";
    public static final String CT_ICON="image/x-icon";
    public static final String CT_JPG="image/jpeg";
    public static final String CT_PNG=" image/png";
    public static final String CT_SVG="text/xml";
    //headers name
    public static final String H_CONTENT_TYPE="Content-Type";
    public static final String H_SERVER="Server";
    public static final String H_CONTENT_LENGTH="Content-Length";
    public static final String H_SET_COOKIE="Set-Cookie";
    public static final String H_COOKIE="Cookie";
    public static final String H_LOCATION="Location";
}
