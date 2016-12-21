package  socketServer.basicLibraries.httpBasic.sessionBasic;

import  socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import  socketServer.basicLibraries.helperBasic.SystemConstant;
import  socketServer.basicLibraries.httpBasic.models.Cookie;
import  socketServer.basicLibraries.httpBasic.HttpConstant;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;
import  socketServer.models.UserModel;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Yiding Liu on 11/14/2016.
 */
public class SessionManager extends SessionManagerBasic {
    public static SessionManager sessionManager=new SessionManager();

    private SessionManager(){
        sessions=new HashMap<>();
    }
    @Override
    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * give a session id and create a session in the sessionManager
     * @param sessionId
     * @param sessionBasic
     */
    @Override
    public void createSession(String sessionId, HttpSession sessionBasic) {
        sessions.put(sessionId,sessionBasic);
    }

    @Override
    public boolean isExist(String sessionId) {
        if(sessions.containsKey(sessionId)){
            if(!sessions.get(sessionId).isExpired()) return true;
            else clearSession(sessionId);
        }
        return false;
    }

    @Override
    public void clearSession(String sessionId) {
        if(sessions.containsKey(sessionId)){
            sessions.remove(sessionId);
        }
    }

    @Override
    public void clearAll() {
        for(String key:sessions.keySet()){
            isExist(key);
        }
    }
    public static SessionManager getInstance(){
        return sessionManager;
    }
    /**
     * extend session one hour automatically
     * @param s
     */
    public void extendSession(HttpSession s, HttpResponse resp){
        Cookie c=new Cookie(SystemConstant.SESSION_ID,s.getSessionId());
        c.setDefault();
        s.extendExpireDate(60*60);
        resp.setHeader(HttpConstant.H_COOKIE,c.toString());
    }

    /**
     * create a session in the sessionManager and create cookie in the response header
     * @return
     */
    public HttpSession createSession(UserModel user,HttpResponse resp){
        String sessionId = UUID.randomUUID().toString();
        String sessionTime= ConfigInfo.getConfig("sessionTime");

        //set cookie
        Cookie cookie = new Cookie(SystemConstant.SESSION_ID, sessionId);
        cookie.setMaxAge(sessionTime==null?3600:Integer.parseInt(sessionTime));
        cookie.setPath("/");
        resp.setHeader(HttpConstant.H_SET_COOKIE, cookie.toString());

        //set session
        HttpSession session = new HttpSession(sessionId, String.valueOf(user.getUserId()), cookie.getExpire());
        SessionManager sessionManager=SessionManager.getInstance();
        sessionManager.createSession(sessionId, session);

        return session;
    }

    /**
     * delete session from sessionManager and set cookie Max-Age=0;
     * @param sessionId
     * @param resp
     * @return
     */
    public HttpSession deleteSession(String sessionId,HttpResponse resp){
        HttpSession session=SessionManager.getInstance().getSession(sessionId);
        //set cookie
        Cookie cookie = new Cookie(SystemConstant.SESSION_ID, sessionId);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.setHeader(HttpConstant.H_SET_COOKIE, cookie.toString());

        //delete session
        SessionManager.getInstance().clearSession(sessionId);

        return session;
    }
}
