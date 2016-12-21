package  socketServer.basicLibraries.httpBasic.httpHandlerBasic;

import java.util.TreeMap;

/**
 * Created by yliu224 on 11/6/16.
 */
public class HandlerSet {
    private TreeMap<String,Object> handlerMap;
    public HandlerSet(){
        handlerMap=new TreeMap<>();
    }

    public void addHandler(String path,Object handler){
        handlerMap.put(path,handler);
    }

    public Object getHandler(String path){
        return handlerMap.get(path);
    }
}
