package  socketServer.basicLibraries.httpBasic.filterBasic;

import  socketServer.basicLibraries.httpBasic.models.HttpRequest;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;

/**
 * Created by yliu224 on 11/26/16.
 */
public interface IFilter {
    /**
     * Do filtering.If match the requirement return true,otherwise return false
     * @param req
     * @param resp
     * @return
     */
    public boolean filtering(HttpRequest req, HttpResponse resp);

    /**
     * return the filter name
     * @return
     */
    public String getFilterName();

}
