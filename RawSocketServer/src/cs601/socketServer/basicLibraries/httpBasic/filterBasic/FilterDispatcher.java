package cs601.socketServer.basicLibraries.httpBasic.filterBasic;

import cs601.socketServer.basicLibraries.httpBasic.models.HttpRequest;
import cs601.socketServer.basicLibraries.httpBasic.models.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yliu224 on 11/26/16.
 */
public class FilterDispatcher implements IFilterDispatcher {
    private static FilterDispatcher ourInstance = new FilterDispatcher();
    private Map<String,IFilter> filters;

    public static FilterDispatcher getInstance() {
        return ourInstance;
    }

    private FilterDispatcher() {
        filters=new HashMap<>();
    }

    @Override
    public void registerFilter(IFilter filter) {
        filters.put(filter.getFilterName(),filter);
    }

    @Override
    public boolean dispatchFilter(String filterName, HttpRequest req, HttpResponse resp) {
        if(filters.containsKey(filterName)){
            IFilter filter=filters.get(filterName);
            return filter.filtering(req,resp);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isContainFilter(String filterName) {
        return filters.containsKey(filterName);
    }
    /*****************************private method begin*****************************/

    /*****************************private method end*****************************/
}
