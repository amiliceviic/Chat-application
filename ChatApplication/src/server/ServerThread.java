/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package server;

import java.io.*;
import java.net.*;

/**
 *
 * @author Aleksandar Milicevic
 */
public class ServerThread extends Thread {
    
    BufferedReader ulazniTokOdKlijenta = null;
    PrintStream izlazniTokKaKlijentu = null;
    
    Socket soketZaKomunikaciju = null;
    ServerThread[] clients;

    public ServerThread(Socket soket, ServerThread[] clients) {
        this.soketZaKomunikaciju = soket;
        this.clients = clients;
    }

    public void run() {
        String line;
        String name;
        
        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());
            
            izlazniTokKaKlijentu.println("Unesite ime.");
            name = ulazniTokOdKlijenta.readLine();
            
            izlazniTokKaKlijentu.println("Dobrodosao/la " + name + ".\nZa izlaz unesite    /quit");
            
            for(int i = 0; i <= 9; i++) {
                if(clients[i] != null && clients[i] != this) {
                    clients[i].izlazniTokKaKlijentu.println("*** Novi korisnik: " + name + " je usao u chat sobu!!! ***");
                }
            }
            
            while(true) {
                line =ulazniTokOdKlijenta.readLine();
                
                if(line.startsWith("/quit")) {
                    break;
                }
                
                for(int i = 0; i <= 9; i++) {
                    if(clients[i] != null) {
                        clients[i].izlazniTokKaKlijentu.println("<" + name + "> " + line);
                    }
                }
            }
            
            for(int i = 0; i <= 9; i++) {
                if(clients[i] != null && clients[i] != this) {
                    clients[i].izlazniTokKaKlijentu.println("*** Korisnik " + name + " izlazi iz chat sobe!!! ***");
                }
            }
            
            izlazniTokKaKlijentu.println("*** Dovidjenja " + name + " ***");
            
            soketZaKomunikaciju.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        for(int i = 0; i <= 9; i++) {
            if(clients[i] == this) {
                clients[i] = null;
            }
        }
    }

}
