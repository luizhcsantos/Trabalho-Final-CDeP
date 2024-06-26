package client;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import compute.Task;

public class Senha implements Task<String>, Serializable {

    private static final long serialVersionUID = 1L;
    private final int comprimento;
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITOS = "0123456789";
    private static final String CARACTERES_ESPECIAIS = "!@#$%^&*()-_=+<>?";


    public Senha(int comprimento) {
        if (comprimento <= 0) {
            throw new IllegalArgumentException("Comprimento deve ser maior ou igual a 8");
        }
        this.comprimento = comprimento; 
    }

    @Override
    public String execute() {
        return gerarSenha(comprimento); 
    }

    public String gerarSenha(int comprimento) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(comprimento);

        List<Character> charCategories = new ArrayList<>();
        charCategories.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        charCategories.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        charCategories.add(DIGITOS.charAt(random.nextInt(DIGITOS.length())));
        charCategories.add(CARACTERES_ESPECIAIS.charAt(random.nextInt(CARACTERES_ESPECIAIS.length())));

        for (int i = 4; i < comprimento; i++) {
            String allCharacters = UPPERCASE + LOWERCASE + DIGITOS + CARACTERES_ESPECIAIS;
            charCategories.add(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        Collections.shuffle(charCategories);

        for (Character ch : charCategories) {
            password.append(ch);
        }

        System.out.println("\nEnviando resposta da solicitacao ao servidor...");
        return password.toString();
    }
    
}
