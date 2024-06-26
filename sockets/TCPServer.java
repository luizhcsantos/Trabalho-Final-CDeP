package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.*;
import compute.Compute;

public class TCPServer {

    public static void main(String args[]) {

        try {
            // Conectar com o cliente
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }

    } 
}
        

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

                System.out.println("\nConexao SERVIDOR-EXECUTOR estabelecida com sucesso...\n");

                clientSocket = aClientSocket; 
                in = new ObjectInputStream(clientSocket.getInputStream()); 
                out = new ObjectOutputStream(clientSocket.getOutputStream()); 
                out.flush();
                
                System.out.println("\nConexao CLIENTE-SERVIDOR estabelecida com sucesso...\n");
                this.start();

            } catch (IOException e) {
                System.out.println("Connection:" + e.getMessage());
            }
            catch(Exception e) {
                System.err.println("\nErro ao executar a conexao SERVIDOR-EXECUTOR:");
                e.printStackTrace();
            }
            
        }

        
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

                    System.out.println("\nResposta do servico Encript recebida, enviando ao cliente...");
                    // Sends the encrypted string back to the client
                    out.writeObject(stringEncriptada);
                    out.flush();
                } 
                else if (dadosSeparados[0].toLowerCase().equals("senha")) {
                    System.out.println("teste");
                    int comprimento = Integer.parseInt(dadosSeparados[1]);
                    Senha task = new Senha(comprimento); 
                    String senhaGerada = task.execute(); 

                    System.out.println("\nResposta do servico Encript recebida, enviando ao cliente...");
                    out.writeObject(senhaGerada);
                    out.flush();
                }

            }catch (Exception e) {
                System.err.println(
                    "\nErro ao executar servicos do servidor - verifique se as entradas do cliente foram corretas.: \n");
            e.printStackTrace();
        }

    }
}