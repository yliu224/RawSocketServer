package  socketServer.basicLibraries.httpBasic.routeDispatcher;

import  socketServer.basicLibraries.httpBasic.models.HttpRequest;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;
import  socketServer.basicLibraries.httpBasic.contollerBasic.Controller;

/**
 * Created by yliu224 on 11/26/16.
 */
public interface IRouterDispatcher {
    /**
     * Register a controller
     * @param controller
     */
    public void registerController(Class<? extends Controller> controller);

    /**
     * Dispatch request to specific controller,and send it back to browser
     * @param req
     * @param resp
     */
    public void dispatchRequest(HttpRequest req, HttpResponse resp);

    /**
     * Check if the controller has been registered
     * @param controller
     * @return
     */
    public boolean isContainController(Class<? extends Controller> controller);
}
