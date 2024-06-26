package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.Encript;
import compute.Compute;

public class TCPServer {

    public static void main(String args[]) {
        

    class Connection extends Thread {
        
        // Variáveis da conexão cliente - servidor
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket clientSocket;

        // Variáveis da conexão executor - servidor
        String name;
        Registry registry;
        Compute comp;
        

        public Connection(Socket aClientSocket) {
            try {
                // Preparando a conexão servidor-executor
                name = "Compute";
                registry = LocateRegistry.getRegistry("localhost", 1099);
                comp = (Compute) registry.lookup(name);

                System.out.println("\nConexão SERVIDOR-EXECUTOR estabelecida com sucesso...\n");

                clientSocket = aClientSocket; 
                in = new ObjectInputStream(clientSocket.getInputStream()); 
                out = new ObjectOutputStream(clientSocket.getOutputStream()); 
                out.flush();
                
                System.out.println("\nConexão CLIENTE-SERVIDOR estabelecida com sucesso...\n");
        
            } catch (IOException e) {
                System.out.println("Connection:" + e.getMessage());
            }
            catch(Exception e) {
                System.err.println("\nErro ao executar a conexão servidor-executor:");
                e.printStackTrace();
            }
            
        }

        @Override
        public void run() {
            try {
                System.out.println("\nIniciando leitura dos dados enviados pelo Cliente\n");

                String data = (String) in.readObject(); 
                System.out.println("\nData: " + data + "\n");
                String[] dadosSeparados = data.split(" "); 

                if (dadosSeparados[0].toLowerCase().equals("encript")) {
                    String stringOriginal = dadosSeparados[1]; 
                    int deslocamento = Integer.parseInt(dadosSeparados[2]); 
                    Encript task = new Encript(stringOriginal, deslocamento); 
                    String stringEncriptada = task.execute(); 

                    System.out.println("\nResposta do serviço Encrypt recebida, enviando ao cliente...");
                    // Sends the encrypted string back to the client
                    out.writeObject(stringEncriptada);
                    out.flush();
                }

            }catch (Exception e) {
                System.err.println(
                    "\nErro ao executar serviços do servidor - verifique se as entradas do cliente foram corretas. Para ajuda, utilize 'help': \n");
            e.printStackTrace();
        }

    }

    }
    }
}