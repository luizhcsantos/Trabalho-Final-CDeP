package sockets;

import client.Encript;
import client.IMC;
import client.QRCodeGenerator;
import client.Senha;
import compute.Compute;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TCPServer {


    static final String CAMINHO_PADRAO_QR = "services/qrcodes/";

    static {
        File diretorio = new File(CAMINHO_PADRAO_QR);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
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
            catch(NotBoundException e) {
                System.err.println("\nErro ao executar a conexao SERVIDOR-EXECUTOR:");
            }
            
        }

        
        @Override
        public void run() {
            try {
                System.out.println("\nIniciando leitura dos dados enviados pelo Cliente\n");

                String data = (String) in.readObject(); 
                System.out.println("\nData: " + data + "\n");
                String[] dadosSeparados = data.split(" "); 
                System.out.println(dadosSeparados[0]);

                switch (dadosSeparados[0].toLowerCase()) {
                    case "encript" ->                         {
                            String stringOriginal = dadosSeparados[1];
                            int deslocamento = Integer.parseInt(dadosSeparados[2]);
                            Encript task = new Encript(stringOriginal, deslocamento);
                            String stringEncriptada = task.execute();
                            System.out.println("\nResposta do servico Encript recebida, enviando ao cliente...");
                            // Sends the encrypted string back to the client
                            out.writeObject(stringEncriptada);
                            out.flush();
                        }
                    case "senha" ->                         {
                            System.out.println("teste");
                            int comprimento = Integer.parseInt(dadosSeparados[1]);
                            Senha task = new Senha(comprimento);
                            String senhaGerada = task.execute();
                            System.out.println("\nResposta do servico Encript recebida, enviando ao cliente...");
                            out.writeObject(senhaGerada);
                            out.flush();
                        }
                    case "imc" ->                         {
                            float altura = Float.parseFloat(dadosSeparados[1]);
                            float peso = Float.parseFloat(dadosSeparados[2]);
                            IMC task = new IMC(altura, peso);
                            float imc = task.execute();
                            System.out.println("\nResposta do servico IMC recebida, enviando ao cliente...");
                            System.out.println("\nimc: "+imc);
                            out.writeObject(imc);
                            out.flush();
                        }
                    case "qrcode" ->                         {
                            String texto = dadosSeparados[1];
                            int largura = Integer.parseInt(dadosSeparados[2]);
                            int altura = Integer.parseInt(dadosSeparados[3]);
                            String caminho =  TCPServer.CAMINHO_PADRAO_QR + "qrcode_" + System.currentTimeMillis() + ".png";
                            QRCodeGenerator task = new QRCodeGenerator(texto, largura, altura, caminho);
                            String caminhoGerado = task.execute();
                            System.out.println("\nQR Code gerado, caminho: " + caminhoGerado);
                            out.writeObject(caminhoGerado);
                            out.flush();
                        }
                    default -> {
                    }
                }

            }catch (IOException | ClassNotFoundException | NumberFormatException e) {
                System.err.println(
                    "\nErro ao executar servicos do servidor - verifique se as entradas do cliente foram corretas.: \n");
        }

    }
}