package cs601.socketServer.basicLibraries.httpBasic.models;

import cs601.hotelapp.FormatString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yliu224 on 11/13/16.
 */
public class Cookie {
    private String name;
    private String value;
    private String domain;
    private int maxAge = -1;
    private String expire;
    private String path;

    public Cookie(String name, String value) {
        if (name != null && name.length() != 0) {
            if (!name.equalsIgnoreCase("Comment") && !name.equalsIgnoreCase("Discard") && !name.equalsIgnoreCase("Domain") && !name.equalsIgnoreCase("Expires") && !name.equalsIgnoreCase("Max-Age") && !name.equalsIgnoreCase("Path") && !name.equalsIgnoreCase("Secure") && !name.equalsIgnoreCase("Version") && !name.startsWith("$")) {
                this.name = name;
                this.value = value;
            } else {
                throw new IllegalArgumentException("cookie_name_is_token");
            }
        } else {
            throw new IllegalArgumentException("err.cookie_name_blank");
        }
    }
    public void setDomain(String domain) {
        this.domain = domain.toLowerCase(Locale.ENGLISH);
    }

    public String getDomain() {
        return this.domain;
    }

    public void setMaxAge(int expire) {
        this.maxAge = expire;
        Date d=new Date();
        d.setTime(System.currentTimeMillis()+(maxAge*1000));
        this.expire= FormatString.dateToString(d.getTime());
    }

    public String getExpire(){
        return expire;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setPath(String uri) {
        this.path = uri;
    }

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * Default setting
     * Max-Age=60*60
     * Path="/"
     * Expire=one hour later
     */
    public void setDefault(){
        setMaxAge(60*60);
        setPath("/");
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(name+"="+value);

        if(domain!=null&&!domain.equals("")) sb.append(";Domain="+domain);
        if(expire!=null&&!expire.equals("")) sb.append(";Expires="+expire);
        if(path!=null&&!path.equals("")) sb.append(";Path="+path);
        if(maxAge!=-1) sb.append(";Max-Age="+maxAge);

        return sb.toString();
    }
}
