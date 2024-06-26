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
                    System.out.println("\nResposta recebida do servidor do servico Encript:");
                    System.out.println("A string encriptada: " + encryptedString + "\n");
           } else if (args[0].toLowerCase().equals("senha")) {

                int comprimento = Integer.parseInt(args[1]);

                if (comprimento <= 0) {
                    System.out.println("Comprimento deve ser de, no minimo, 8 caracteres");
                    return;
                }

                // Envia a solicitação de geração de senha para o servidor
                out.writeObject("senha " + comprimento);

                // Recebe a senha gerada do servidor
                String senhaGerada = (String) in.readObject();

                // Imprime a senha gerada
                System.out.println("\nResposta recebida do servidor do servico Senha:");
                System.out.println("A senha gerada: " + senhaGerada + "\n");

           }
           else if (args[0].toLowerCase().equals("imc")) {
            float altura = Float.parseFloat(args[1]); 
            float peso = Float.parseFloat(args[2]); 

            // Envia a solicitação ao servidor
            out.writeObject("imc " + altura + " " + peso);

            // Recebe o resultado do IMC do servidor
            float imc = (Float) in.readObject();

            // Exibe o resultado do IMC
            System.out.println("\nResposta recebida do servidor para o servico IMC:");
            System.out.println("Seu IMC eh: " + imc + "\n");
           }
        //    else if (args[0].toLowerCase().equals("qrcode")) {
        //     String texto = args[1];
        //     int largura = Integer.parseInt(args[2]);
        //     int altura = Integer.parseInt(args[3]);
        
        //     // Envia a solicitação ao servidor
        //     out.writeObject("qrcode " + texto + " " + largura + " " + altura);
        
        //     // Recebe o caminho do arquivo gerado do servidor
        //     String caminhoArquivo = (String) in.readObject();
        
        //     // Exibe o resultado
        //     System.out.println("\nResposta recebida do servidor para o servico de Geracaoo de QR Code:");
        //     System.out.println("QR Code gerado em: " + caminhoArquivo + "\n");
        // }
            
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
