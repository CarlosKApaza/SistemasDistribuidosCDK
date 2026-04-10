/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *  ServidorBienestar RMI implementa IBienestar
 * @author cdk04
 */
public class ServidorBienestar extends UnicastRemoteObject implements IBienestar{
    public ServidorBienestar() throws RemoteException { super(); }
    
    
    @Override 
    public ArrayList<Nota> ObtenerHistorial(String ci) throws RemoteException{
        
        ArrayList<Nota> notas = new ArrayList<>();
        
        // probamos promedio 85: 69 y 90
        if(ci.equals("1234567")) {
            notas.add(new Nota("Matematicas", 99)); // podemos ir probando el valor de promedio 
            notas.add(new Nota("Fisica", 90));
        }
        return notas;
    }
    
    public static void main(String[] args) {
        try{ 
            // levantamos el servidorBienestar RMI
            LocateRegistry.createRegistry(1100);
            Naming.bind("rmi://localhost:1100/ServidorBienestar", new ServidorBienestar());
            System.out.println("[BIENESTAR RMI] Listo en puerto 1100");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
