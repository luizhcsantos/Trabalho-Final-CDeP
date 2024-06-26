package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import compute.Compute;

public class ComputeEncript {

    public static void main(String args[]) {
        try {
            String name = "Compute"; 
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])); 
            Compute comp = (Compute) registry.lookup(name);

            Encript task = new Encript(name, 0)

        }catch(Exception e) {

        }


    }
    
}
