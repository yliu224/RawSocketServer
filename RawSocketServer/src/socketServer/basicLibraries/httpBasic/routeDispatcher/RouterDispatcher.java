package  socketServer.basicLibraries.httpBasic.routeDispatcher;

import  socketServer.basicLibraries.helperBasic.JsonHelper;
import  socketServer.basicLibraries.httpBasic.models.ActionResult;
import  socketServer.basicLibraries.httpBasic.HttpConstant;
import  socketServer.basicLibraries.httpBasic.models.HttpRequest;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;
import  socketServer.basicLibraries.httpBasic.contollerBasic.Controller;
import  socketServer.basicLibraries.httpBasic.filterBasic.FilterDispatcher;
import  socketServer.basicLibraries.httpBasic.filterBasic.IFilterDispatcher;
import  socketServer.basicLibraries.httpBasic.sessionBasic.HttpSession;
import  socketServer.basicLibraries.httpBasic.sessionBasic.SessionManager;
import  socketServer.httpHandlers.FileHandler;
import  socketServer.models.UserModel;
import org.json.simple.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will dispatch specific url to the specific controller and action
 * The default model should be http://hostname:port/controller/action?parameters
 * It can be customized in the later version.In the alpha version,it only support the default model
 * The default url(http://hostname:port/) will map to HomeController.Index()
 * Any requests which didn't provide the action name will map to Index() action
 * The url is case sensitive!!!
 */
public class RouterDispatcher implements IRouterDispatcher {
    private static RouterDispatcher ourInstance = new RouterDispatcher();
    private static Map<String,Class> controllers;
    private static IFilterDispatcher filterDispatcher;

    public static RouterDispatcher getInstance() {
        return ourInstance;
    }

    private RouterDispatcher() {
        controllers=new HashMap<>();
        filterDispatcher= FilterDispatcher.getInstance();
    }

    /**
     * It will map className to Class.If the name is the duplicated,the new Class will replace the old Class
     * @param controller
     */
    @Override
    public void registerController(Class<? extends Controller> controller) {
        controllers.put(getNameWithoutPackage(controller),controller);
    }

    /**
     * This method will dispatch request to controllers.The process is showing below:
     * 1.Get controller based on controllerName in the request path.If exists,then get the controller ,else go to the resource controller
     * 2.Check the filter annotation.If there have filters,then invoke filters based on annotationName,which should be the same name as filter class.
     * If and only if passed all the filters,it will go into the next step
     * 3.Get the method based on actionName in the request path,request method(GET,POST...) and parameter numbers.If exists,then get the method,else return 404
     * 4.Do step 2 again
     * 5.Invoke the method.If the execution success,then return(the response message will send back in the invokeMethod()),else return 500;
     * @param req
     * @param resp
     */
    @Override
    public void dispatchRequest(HttpRequest req, HttpResponse resp) {
        String controllerName= getControllerName(req.getPath())+"Controller";
        String actionName= getActionName(req.getPath());
        //Class errorClass=controllers.get("ErrorController");
        try{
            if(controllers.containsKey(controllerName)){
                Class controllerClass=controllers.get(controllerName);
                if(doFilters(controllerClass.getAnnotations(),req,resp)){
                    Method m=getMethod(controllerClass,actionName,req);
                    if(m!=null){
                        if(doFilters(m.getAnnotations(),req,resp)){//If doFilter failed,the filter will send response to client
                            //Processing the request and get result
                            if(invokeMethod(m,controllerClass,req,resp)) return;
                            else invokeErrorMethod(HttpConstant.SC_INTERNAL_ERROR,resp);
                        }
                    }
                    else invokeErrorMethod(HttpConstant.SC_NOT_FOUND,resp);
                }
            }
            else {
                if(isFilePath(req.getPath())) invokeResourceMethod(req,resp);
                else invokeErrorMethod(HttpConstant.SC_NOT_FOUND,resp);
            }
        }
//        catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            invokeErrorMethod(HttpConstant.SC_NOT_FOUND,resp);
//        } //catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isContainController(Class<? extends Controller> controller) {
        return controllers.containsKey(getNameWithoutPackage(controller));
    }

    /*****************************private method begin*****************************/
    /**
     * Get controller name
     * Default will return "home"
     * @param path
     * @return
     */
    private String getControllerName(String path){
        int i=path.indexOf("/",1);
        if(i==-1){
            if(path.substring(1).equals(""))return "Home";
            else return path.substring(1);
        }
        else return path.substring(1,i);
    }

    /**
     * Get action name
     * Default will return "index"
     * @param path
     * @return
     */
    private String getActionName(String path){
        String actionName;
        int i=path.indexOf("/",1);
        int j=path.indexOf("?");
        if(i==-1) actionName= "index";
        else if(j==-1){
            if(path.substring(i+1).equals("")) actionName= "index";
            else actionName= path.substring(i+1);
        }
        else actionName= path.substring(i+1,j);
        try{
            Integer.parseInt(actionName);
            actionName="index";
        }
        catch (Exception e){}
        return actionName;
    }

    /**
     * Return the name of the class, without the package name
     * @param c
     * @return
     */
    private String getNameWithoutPackage(Class c){
        return getNameWithoutPackage(c.getName());
    }

    /**
     * Return the name without package name
     * @param name
     * @return
     */
    private String getNameWithoutPackage(String name){
        return name.substring(name.lastIndexOf(".")+1);
    }
    /**
     * Before forwording the request,we can do some filter job
     * If and only if the request pass all the filter,the function will return true
     * @param names
     * @param req
     * @param resp
     * @return
     */
    private boolean doFilters(Annotation[] names, HttpRequest req, HttpResponse resp){
        for(Annotation name:names){
            String filterName=getNameWithoutPackage(name.annotationType().getName());
            if(filterDispatcher.isContainFilter(filterName)){
                if(!filterDispatcher.dispatchFilter(filterName,req,resp)) return false;
            }
        }
        return true;
    }

    /**
     * Send error message to the browser
     * @param statusCode
     */
    private void invokeErrorMethod(String statusCode,HttpResponse resp){
        resp.setStatusCode(statusCode);
        resp.setContentType(HttpConstant.CT_HTML);
        resp.setContent(statusCode);
        resp.send();
    }

    /**
     * Handle the file on the server
     * @param req
     * @param resp
     */
    private void invokeResourceMethod(HttpRequest req,HttpResponse resp){
        //TODO:this need redesign and optimize
        new FileHandler().doGet(req,resp);
    }

    /**
     * Get the method from Class.If there a method meet all requirements of the request,like number of parameters,action name or action verb(GET,POST...)
     * If find the method,return the Method,otherwise return null
     * @param controller
     * @param methodName
     * @param req
     * @return
     */
    private Method getMethod(Class controller,String methodName,HttpRequest req){
        //Get all the methods from the controller
        Method[] ms=controller.getMethods();
        for(Method m:ms){
            //Check method name
            if(m.getName().equals(methodName)){
                //Check annotation
                Annotation[] as=m.getAnnotations();
                for(Annotation a:as){
                    String reqMethod=req.getMethod().toLowerCase();
                    String annotationMethod=getNameWithoutPackage(a.annotationType().getName()).toLowerCase();
                    if(reqMethod.equals(annotationMethod)){
                        //Check parameter numbers
                        int mCount=m.getParameterCount();
                        int reqCount=req.getParameterCount()+req.getCookieCount()+(req.hasJson()?1:0);
                        if(mCount<=reqCount){
                            return m;
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * Invoke the method and send response to browser
     * !!!For Alpha version,it only support String and JSON parameter!!!
     * @param m
     * @param controllerClass
     * @param req
     * @param resp
     * @return
     */
    private boolean invokeMethod(Method m, Class controllerClass, HttpRequest req, HttpResponse resp) {
        Parameter[] parameters=m.getParameters();
        Object[] objects=new Object[m.getParameterCount()];
        for(int i=0;i<parameters.length;i++){
            String pType=parameters[i].getParameterizedType().getTypeName();
            if(pType.equals(String.class.getTypeName())){
                if(req.getParameter(parameters[i].getName())!=null){
                    objects[i]=req.getParameter(parameters[i].getName());
                }
                else{
                    objects[i]=req.getCookie(parameters[i].getName());
                }

            }
            else if(pType.equals(JSONObject.class.getTypeName())){
                objects[i]=req.getJson();
            }
            else{
                objects[i]=null;
            }
        }
        try {
            //The return type should be String,JSONObject or byte[]
            ActionResult actionResult=(ActionResult)m.invoke(controllerClass.newInstance(),objects);

            //Send response back
            resp.setStatusCode(HttpConstant.SC_OK);
            switch (actionResult.getOperationType()){
                case ActionResult.BYTES:
                    resp.setContentType(actionResult.getFile().getFileType());
                    resp.sendByte(actionResult.getFile().getBytes());
                    return true;
                case ActionResult.HTML:
                    resp.setContentType(HttpConstant.CT_HTML);
                    resp.setContent(actionResult.getStringContent());
                    resp.send();
                    return true;
                case ActionResult.JSON:
                    resp.setContentType(HttpConstant.CT_JSON);
                    resp.setContent(((JSONObject)actionResult.getObject()).toJSONString()+"     ");
                    resp.send();
                    return true;
                case ActionResult.REDIRECT:
                    resp.urlRedirectSetting(req.getHostName(),actionResult.getStringContent());
                    resp.send();
                    return true;
                case ActionResult.LOG_IN_SUCCESS:
                    SessionManager.getInstance().createSession((UserModel)actionResult.getObject(),resp);
                    resp.setContentType(HttpConstant.CT_JSON);
                    resp.setContent(JsonHelper.userModelToJson((UserModel)actionResult.getObject()).toJSONString());
                    resp.send();
                    return true;
                default:
                    return false;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * If the path endwith .jpg,.doc,.html...,then return true,else return false;
     * @param path
     * @return
     */
    private boolean isFilePath(String path){
        if(
                path.endsWith(".jpg")||
                path.endsWith(".png")||
                path.endsWith(".js")||
                path.endsWith(".html")||
                path.endsWith(".doc")||
                path.endsWith(".xml")||
                path.endsWith(".css")) return true;
        else return false;
    }
    /*****************************private method end*****************************/
}
