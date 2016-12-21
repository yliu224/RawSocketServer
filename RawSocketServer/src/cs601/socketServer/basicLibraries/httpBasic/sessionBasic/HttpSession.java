package cs601.socketServer.basicLibraries.httpBasic.sessionBasic;

import cs601.hotelapp.FormatString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 * Created by Yiding Liu on 11/14/2016.
 */
public class HttpSession {
    private String sessionId;
    private String userId;
    private long expireDate;

    public HttpSession(String sessionId, String userId, String expireDate){

        this.sessionId=sessionId;
        this.userId=userId;
        this.expireDate=FormatString.stringToDate(expireDate);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getExpireDate() {
        return FormatString.dateToString(expireDate);
    }

    public void setExpireDate(long time) {
        expireDate=time;
    }

    /**
     * extend session time
     * @param time
     */
    public void extendExpireDate(long time){
        expireDate=System.currentTimeMillis()+time*1000;
    }

    /**
     *
     * @return return true if this session has expired ,otherwise return false
     */
    public boolean isExpired() {
        long sysTime=System.currentTimeMillis();
        if (sysTime<expireDate) return false;
        else return true;
    }
}
