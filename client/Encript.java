package client;

import java.io.Serializable;

import compute.Task;

public class Encript implements Task<String>, Serializable {

    private static final long serialVersionUID = 227L;

    private final String stringOriginal;
    private final int deslocamento;

    public Encript(String stringOriginal, int deslocamento) {
        this.stringOriginal = stringOriginal;
        this.deslocamento = deslocamento;
    }

    @Override
    public String execute() {
        return encriptString(stringOriginal, deslocamento);
    }

    public String encriptString(String stringOriginal, int deslocamento) {
        StringBuilder stringEncriptada = new StringBuilder(); 

        for (int i=0; i< stringOriginal.length(); i++) {
            char caracter = stringOriginal.charAt(i); 

            if (Character.isLetter(caracter)) {
                boolean isUppercase = Character.isUpperCase(caracter); 
                caracter = Character.toUpperCase(caracter); 

                char charEncriptado = (char) ((caracter - 'A' + deslocamento) % 26 + 'A');

                if (!isUppercase) {
                    charEncriptado = Character.toLowerCase(charEncriptado); 
                }

                stringEncriptada.append(charEncriptado); 


            } else {
                stringEncriptada.append(caracter); 
            }
        }
        System.out.println("\nEnviando resposta da solicitacao ao servidor... ");
        return stringEncriptada.toString();

    }
    
}
