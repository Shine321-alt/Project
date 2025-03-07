package com.project;

import java.sql.SQLException;
import org.h2.tools.Server;

public class H2ServerManager {

    private Server tcpServer;
    private Server webServer;

    public boolean isServerRunning(){
        return (tcpServer != null && tcpServer.isRunning(false))||
                (webServer != null && webServer.isRunning(false));
    }

    public void startServer(){
        try{
            if(!isServerRunning()){
                tcpServer = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093").start();
                System.out.println("Web console running at: http://localhost:9093");
                webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8083").start();
                System.out.println("Web console running at: http://localhost:8083");
            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void stopServer(){
        if(isServerRunning()){
            if(tcpServer != null)
                tcpServer.stop();

            if(webServer != null)
                webServer.stop();

            System.out.println("Server stopped success");
        }else{
            System.out.println("Server is already stopped");
        }
    }
}
