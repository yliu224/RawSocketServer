package cs601.socketServer.basicLibraries.httpBasic.filterBasic;

import cs601.socketServer.basicLibraries.httpBasic.models.HttpRequest;
import cs601.socketServer.basicLibraries.httpBasic.models.HttpResponse;

/**
 * Created by yliu224 on 11/26/16.
 */
public interface IFilterDispatcher {
    /**
     * Register a filter
     * @param filter
     */
    public void registerFilter(IFilter filter);

    /**
     * Dispatch the filter job to specific IFilter class
     * @param req
     * @param resp
     */
    public boolean dispatchFilter(String filterName,HttpRequest req, HttpResponse resp);

    /**
     * Check if the filter has been registered
     * @param filterName
     * @return
     */
    public boolean isContainFilter(String filterName);
}
