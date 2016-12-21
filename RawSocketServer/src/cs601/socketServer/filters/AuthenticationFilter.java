package cs601.socketServer.filters;

import cs601.socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import cs601.socketServer.basicLibraries.httpBasic.filterBasic.IFilter;
import cs601.socketServer.basicLibraries.httpBasic.models.HttpRequest;
import cs601.socketServer.basicLibraries.httpBasic.models.HttpResponse;
import cs601.socketServer.basicLibraries.httpBasic.sessionBasic.HttpSession;
import cs601.socketServer.basicLibraries.httpBasic.sessionBasic.SessionManager;

/**
 * Created by yliu224 on 11/27/16.
 */
public class AuthenticationFilter implements IFilter {
    @Override
    public boolean filtering(HttpRequest req, HttpResponse resp) {

        HttpSession session=req.getSession();
        String path=req.getPath().toLowerCase();
        if(session!=null) {
            SessionManager.getInstance().extendSession(session,resp);
            return true;
        }
        else {
            resp.urlRedirectSetting(req.getHostName(), "/");
            resp.send();
            return false;
        }
    }

    @Override
    public String getFilterName() {
        return "Authentication";
    }
}
