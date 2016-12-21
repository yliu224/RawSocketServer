package  socketServer;

import  concurrent.WorkQueue;
import  socketServer.basicLibraries.httpBasic.annotations.Authentication;
import  socketServer.basicLibraries.httpBasic.filterBasic.FilterDispatcher;
import  socketServer.basicLibraries.httpBasic.httpHandlerBasic.HandlerSet;
import  socketServer.basicLibraries.httpBasic.routeDispatcher.RouterDispatcher;
import  socketServer.controllers.HomeController;
import  socketServer.controllers.HotelController;
import  socketServer.controllers.ReviewController;
import  socketServer.filters.AuthenticationFilter;
import  socketServer.httpHandlers.*;

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
