/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Recibe la solicitud de ClienteUniversitario - ORQUESTADOR RMI + LOGICA - CONECTA TODO
 * CONECTA TODO
 *
 * @author cdk04
 */
public class ServidorUniversitario extends UnicastRemoteObject implements IUniversitario {

    public ServidorUniversitario() throws RemoteException {
        super();
    }

    @Override
    public RespuestaBeca SolicitarBeca(String ci, String nombres, String apellidos) throws RemoteException {
        try {
            // 1 - VALIDAR SEGIP (TCP)   
            String resSegip = "";
            try (Socket socketTCP = new Socket("localhost", 5000);
                    DataOutputStream out = new DataOutputStream(socketTCP.getOutputStream()); 
                    DataInputStream in = new DataInputStream(socketTCP.getInputStream())) {
                   
                    out.writeUTF("verificar:" + ci + "-" + nombres + "-" + apellidos);
                    resSegip  = in.readUTF();
            }
            if(!resSegip.equals("resultado:encontrado")) {
                return new RespuestaBeca(false, "Rechazado: Identidad no valida en SEGIP", 0.0);
            }
            
            
            // 2 - VALIDAR BIENESTAR ( RMI )   ----- Nota.java
            IBienestar bienestar = (IBienestar) Naming.lookup("rmi://localhost:1100/ServidorBienestar");
            ArrayList<Nota> historial = bienestar.ObtenerHistorial(ci);
            
            if (historial.isEmpty()) {
                return new RespuestaBeca(false, "Rechazado: No tiene historial academico", 0.0);
            }
            
            double suma = 0;
            for (Nota n : historial){
                suma += n.getCalificacion(); 
            }
            
            double promedio = suma / historial.size();
            
            // si su promedio es menor a 80 en su historial
            if (promedio <= 80) {
                return new RespuestaBeca(false, "Rechazado: Promeido insuficiente (" + promedio + ")", promedio);
            }
            
            
            // 3.  VALIDACION FINANCIERO  UPD
            String resFinanciero = "";
            try (DatagramSocket socketUDP = new DatagramSocket()) {
                String msj = "deuda:" + ci;
                byte[] dataEnv = msj.getBytes();
                DatagramPacket packetEnv = new DatagramPacket(dataEnv, dataEnv.length, InetAddress.getByName("localhost"), 6000);
                socketUDP.send(packetEnv);

                byte[] dataRec = new byte[1024];
                DatagramPacket packetRec = new DatagramPacket(dataRec, dataRec.length);
                socketUDP.setSoTimeout(2000);
                socketUDP.receive(packetRec);
                resFinanciero = new String(packetRec.getData(), 0, packetRec.getLength()).trim();
            }
            
            if (!resFinanciero.isEmpty() && Double.parseDouble(resFinanciero) > 0) {
                return new RespuestaBeca(false, "Rechazado: Registra deudas en el sistema financiero", promedio);
            }

            
            
            // SI LLEGA AQUÍ, CUMPLE LAS 3 REGLAS
            return new RespuestaBeca(true, "Elegible para beca", promedio); // go

        } catch (Exception e) {
            return new RespuestaBeca(false, "Error interno del sistema: " + e.getMessage(), 0.0);
        }
    }
    
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost:1099/ServidorUniversitario", new  ServidorUniversitario());
            System.out.println("[ORQUESTADOR UNIVERSITARIO RMI] Listo en el puerto 1099...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
