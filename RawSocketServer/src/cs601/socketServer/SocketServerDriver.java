package cs601.socketServer;

import cs601.concurrent.WorkQueue;
import cs601.socketServer.basicLibraries.httpBasic.annotations.Authentication;
import cs601.socketServer.basicLibraries.httpBasic.filterBasic.FilterDispatcher;
import cs601.socketServer.basicLibraries.httpBasic.httpHandlerBasic.HandlerSet;
import cs601.socketServer.basicLibraries.httpBasic.routeDispatcher.RouterDispatcher;
import cs601.socketServer.controllers.HomeController;
import cs601.socketServer.controllers.HotelController;
import cs601.socketServer.controllers.ReviewController;
import cs601.socketServer.filters.AuthenticationFilter;
import cs601.socketServer.httpHandlers.*;

/**
 * main driver of the raw socket
 */
public class SocketServerDriver {
    public static void main(String[] args){
        SocketServer server=new SocketServer(new WorkQueue(10));

        System.out.println("System Running...");

        RouterDispatcher.getInstance().registerController(HomeController.class);
        RouterDispatcher.getInstance().registerController(HotelController.class);
        RouterDispatcher.getInstance().registerController(ReviewController.class);

        FilterDispatcher.getInstance().registerFilter(new AuthenticationFilter());

        server.createWelcomeSocket();
    }
}
