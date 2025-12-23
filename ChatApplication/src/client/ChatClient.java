/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package client;

import java.io.*;
import java.net.*;

/**
 *
 * @author Aleksandar Milicevic
 */
public class ChatClient implements Runnable {
    
    static Socket soketZaKomunikaciju = null;
    static PrintStream izlazniTokKaServeru = null;
    static BufferedReader ulazniTokOdServera = null;
    static BufferedReader ulazKonzola = null;
    static boolean kraj = false;
    
    public static void main(String[] args) {
        try {
            int port = 2222;
            
            if(args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            
            soketZaKomunikaciju = new Socket("localhost", port);
            
            ulazKonzola = new BufferedReader(new InputStreamReader(System.in));
            
            izlazniTokKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());
            ulazniTokOdServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            
            new Thread(new ChatClient()).start();
            
            while(!kraj) {
                izlazniTokKaServeru.println(ulazKonzola.readLine());
            }
            
            soketZaKomunikaciju.close();
        } catch (UnknownHostException uhe) {
            System.out.println("Don't know about host ");
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }

    @Override
    public void run() {
        String linijaOdServera;
        
        try {
            while((linijaOdServera = ulazniTokOdServera.readLine()) != null) {
                System.out.println(linijaOdServera);
                
                if(linijaOdServera.indexOf("*** Dovidjenja") == 0) {
                    kraj = true;
                    return;
                }
            }
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }

}
