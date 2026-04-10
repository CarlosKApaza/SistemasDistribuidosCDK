/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkg1erparcial_becas;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CONTRATOS RMI
 *  Lo que el estudiante ve
 * @author cdk04
 */
public interface IUniversitario extends Remote {
    RespuestaBeca SolicitarBeca(String ci, String nombres, String apellidos) throws RemoteException;
}
