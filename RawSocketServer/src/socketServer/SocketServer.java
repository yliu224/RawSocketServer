package  socketServer;

import  concurrent.WorkQueue;
import  socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import  socketServer.basicLibraries.httpBasic.httpHandlerBasic.HandlerSet;
import  socketServer.basicLibraries.httpBasic.models.HttpRequest;
import  socketServer.basicLibraries.httpBasic.models.HttpResponse;
import  socketServer.basicLibraries.httpBasic.httpHandlerBasic.HttpBaseHandler;
import  socketServer.basicLibraries.httpBasic.routeDispatcher.RouterDispatcher;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yliu224 on 10/30/16.
 */
public class SocketServer {
    private WorkQueue workQueue;
    private HandlerSet handlerSet;

    public SocketServer(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    public void createWelcomeSocket(){
        ServerSocket listener;
        Socket client;
        try {
            String p=ConfigInfo.getConfig("port");
            //default is 8080
            listener=new ServerSocket(Integer.parseInt(p==null?"8080":p));
            while(true){
                Socket s=listener.accept();
                workQueue.execute(new Connection(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setHandlerSet(HandlerSet handlerSet){
        this.handlerSet = handlerSet;
    }

    /**********************connection socket thread begin**********************/
    class Connection implements Runnable{
        private Socket connection;

        public Connection(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            BufferedReader reader=null;
            OutputStream out=null;
            String input="";
            HttpResponse response;
            HttpRequest request;
            List<String> httpHeaderInfo=new ArrayList<>();
            try {
                if(connection==null) return;
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                out=connection.getOutputStream();
                //new request and response
                request=new HttpRequest(reader);
                response=new HttpResponse(out);


                if(reader==null||out==null) return;
                //TODO:always popup null pointer exception.WHY???
                while(true){
                    if(reader==null) break;
                    input=reader.readLine();
                    if(input==null) break;
                    if(input.equals("")) break;
                    httpHeaderInfo.add(input);
                }
                if(httpHeaderInfo==null) return;

                request.initializeHeaders(httpHeaderInfo);

                RouterDispatcher.getInstance().dispatchRequest(request,response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    reader.close();
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /**********************connection socket thread end**********************/
}
