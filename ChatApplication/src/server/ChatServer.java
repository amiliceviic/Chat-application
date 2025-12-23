/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package server;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author Aleksandar Milicevic
 */
public class ChatServer {
    
    static ServerThread clients[] = new ServerThread[10];
    
    public static void main(String[] args) {
        
        int port = 2222;
        
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        
        Socket clientSocket = null;
        
        try {
            ServerSocket serverScoket = new ServerSocket(port);
            
            while(true) {
                clientSocket = serverScoket.accept();
                
                for(int i = 0; i <= 9; i++) {
                    if(clients[i] == null) {
                        clients[i] = new ServerThread(clientSocket, clients);
                        clients[i].start();
                        break;
                    }
                }
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }

}
