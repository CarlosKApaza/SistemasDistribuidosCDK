/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.operacion;

/**
 *
 * @author cdk04
 */
public class Operaciones {
    public static String procesarSolicitud(String cadena) {

        try {
            // Formato esperado: num1,num2,operacion
            // Ejemplo: 5,3,1

            String[] partes = cadena.split(",");

            if (partes.length != 3) {
                return "Formato incorrecto. Use: num1,num2,operacion";
            }

            int numero1 = Integer.parseInt(partes[0]);
            int numero2 = Integer.parseInt(partes[1]);
            int operacion = Integer.parseInt(partes[2]);

            int resultado = 0;

            switch (operacion) {
                case 1:
                    resultado = numero1 + numero2;
                    break;

                case 2:
                    resultado = numero1 - numero2;
                    break;

                case 3:
                    resultado = numero1 * numero2;
                    break;

                case 4:
                    if (numero2 == 0)
                        return "Error: división por cero";
                    resultado = numero1 / numero2;
                    break;

                default:
                    return "Operación no valida";
            }

            return "Resultado: " + resultado;

        } catch (NumberFormatException e) {
            return "Los valores deben ser numeros";
        } catch (Exception e) {
            return "Error en la solicitud";
        }
    }
}
