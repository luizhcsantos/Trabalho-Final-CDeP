package client;

import java.io.Serializable;

import compute.Task;

public class IMC implements Task<Float>, Serializable {

    private static final long serialVersionUID = 1L;
    private float altura; 
    private float peso; 

    public IMC(float altura, float peso) {
        this.altura = altura; 
        this.peso = peso; 
    }

    @Override
    public Float execute() {
        return calcularIMC(altura, peso); 
    }

    private Float calcularIMC(float altura, float peso) {
        float imc; 

        imc = peso/(altura*altura); 
        System.out.println("\naltura: " + altura+ "\npeso: " + peso);

        return imc; 
    }
    
}
