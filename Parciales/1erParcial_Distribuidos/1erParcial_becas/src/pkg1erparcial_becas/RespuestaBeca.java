/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1erparcial_becas;

import java.io.Serializable;

/**
 * Serializable porque este sera un objetio que viaje por la red entonces lo empaquetamos
 * @author cdk04
 */
public class RespuestaBeca implements Serializable {
    private Boolean aprobado;
    private String motivo;
    private Double promedio;

    public RespuestaBeca(Boolean aprobado, String motivo, Double promedio) {
        this.aprobado = aprobado;
        this.motivo = motivo;
        this.promedio = promedio;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public String getMotivo() {
        return motivo;
    }

    public Double getPromedio() {
        return promedio;
    }
    
    @Override
    public String toString(){
        return "aprobado: " + aprobado + ", motivo: '" + motivo + "', promedio: " + promedio;
    }
}
