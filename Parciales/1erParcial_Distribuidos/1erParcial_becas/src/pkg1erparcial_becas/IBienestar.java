/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkg1erparcial_becas;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CONTRATO RMI
 *  Lo que expone BIENESTAR   -- orquestador
 * @author cdk04
 */
public interface IBienestar extends Remote{
    ArrayList<Nota> ObtenerHistorial(String ci) throws RemoteException;
}
