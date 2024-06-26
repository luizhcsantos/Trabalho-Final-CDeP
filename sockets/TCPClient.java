package sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    public static void main(String args[]) {
        Socket s = null; 

        try {
            int serverPort = 7896; 

            s = new Socket("localhost", serverPort); 

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream()); 
            out.flush();

            ObjectInputStream in = new ObjectInputStream(s.getInputStream()); 

           StringBuilder outString = new StringBuilder(); 
           for (int i=0; i < args.length; i++) {
            if (i != 0) 
                outString.append(" "); 

            outString.append(args[i]); 
           }

           out.writeObject(outString.toString()); 

           if (args[0].toLowerCase().equals("encript")) {
                StringBuilder inputString = new StringBuilder();
                for (int i=1; i < args.length; i++) {
                    inputString.append(args[i]).append(" "); 
                } 
                String input = inputString.toString().trim(); 
                
                    // Send the input string to the server
                    out.writeObject(input);

                    // Receive the encrypted string from the server
                    String encryptedString = (String) in.readObject();

                    // Print the encrypted string
                    System.out.println("\nResposta recebida do servidor do servco Encript:");
                    System.out.println("A string encriptada: " + encryptedString + "\n");
           }
            
        }catch (UnknownHostException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("Close: "+ e.getMessage());
                }
            }
        }
    }
    
}
