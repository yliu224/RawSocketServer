package cs601.socketServer.basicLibraries.httpBasic.sessionBasic;

import java.util.Map;

/**
 * Created by Yiding Liu on 11/14/2016.
 */
public abstract class SessionManagerBasic {
    protected Map<String,HttpSession> sessions;
    /**
     * get session by session id
     * @param sessionId
     * @return
     */
    public abstract HttpSession getSession(String sessionId);

    /**
     * create one session
     * @param sessionId
     * @param sessionBasic
     */
    public abstract void  createSession(String sessionId,HttpSession sessionBasic);

    /**
     * Check if this session exists
     * If it exists but has expired,then delete it automatically and return false!
     * @param sessionId
     * @return return true if and only if the session is in the map and it's not expired,otherwise return false
     */
    public abstract boolean isExist(String sessionId);


    /**
     * clear session based on session id
     * @param sessionId
     */
    public abstract void clearSession(String sessionId);

    /**
     * clear all expired session
     */
    public abstract void clearAll();
}
