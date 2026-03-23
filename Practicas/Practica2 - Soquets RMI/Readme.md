# Micro Sistema Judicial - Consultas Financieras Distribuidas

**Materia:** Sistemas Distribuidos (SIS258)  
**Carrera:** Ciencias de la Computación  
**Universidad:** Universidad Mayor, Real y Pontificia de San Francisco Xavier de Chuquisaca (USFX)  
**Autor:** Carlos Daniel Kevin Apaza Villca  

---

## 📖 Descripción General
Este proyecto implementa un sistema distribuido bajo la plataforma Java para la emisión de consultas y retención de fondos (congelamiento) en entidades financieras, solicitado por un Juez. 
El sistema destaca por integrar tres tecnologías de comunicación de red distintas (RMI, TCP y UDP) operando de manera simultánea y concurrente.

## 🏗️ Arquitectura del Sistema
El sistema consta de tres niveles de interacción claramente definidos:

1. **Cliente Juez (RMI Client):** Interfaz de consola que solicita información de cuentas bancarias y emite órdenes al servidor intermediario.
2. **Servidor Justicia (Gateway / RMI Server):** Actúa como nodo central. Recibe las peticiones RMI del Juez y se ramifica comportándose como cliente Socket (TCP y UDP) para consultar a las bases de datos de los bancos.
3. **Entidades Financieras (Socket Servers):**
   * **Banco Mercantil:** Implementado como Servidor TCP. Utiliza una arquitectura multihilo (aislando peticiones concurrentes mediante Handlers) y encapsulamiento estricto para la seguridad de la información.
   * **Banco BCP:** Implementado como Servidor UDP. Procesa peticiones y envía datagramas de respuesta sin conexión persistente.

## 🚀 Tecnologías Utilizadas
* **Java RMI (Remote Method Invocation):** Para la comunicación de alto nivel entre el Juez y el Servidor Justicia. Implementación de la interfaz `Serializable` para el paso de objetos en red.
* **Sockets TCP (`java.net.Socket`, `ServerSocket`):** Para flujos de comunicación confiables y orientados a conexión. Uso de `DataInputStream` y `DataOutputStream`.
* **Sockets UDP (`java.net.DatagramSocket`, `DatagramPacket`):** Para mensajería rápida basada en datagramas.
* **Concurrencia (`synchronized`, `Thread`):** Prevención de condiciones de carrera (Race Conditions) en el acceso a los datos bancarios.

## ⚙️ Instrucciones de Ejecución
Para evitar errores de rechazo de conexión (Connection Refused), los nodos deben inicializarse en cascada, levantando primero los servidores de menor nivel hasta llegar a la capa del cliente.

## 🙏🙏 ---- Ejecutar los archivos en el siguiente orden estricto ---- 🙏🙏
1. run: `ServerMercantil.java` (Levanta el puerto TCP 5001)
2. run: `ServerBCP.java` (Levanta el puerto UDP 5002)
3. run: `ServerJusticia.java` (Levanta el registro RMI en el puerto 1099)
4. run: `Juez.java` (Inicia el cliente y la consola interactiva)

## 🧪 Caso de Prueba Obligatorio
El sistema cuenta con datos precargados para verificar su funcionamiento. Introducir los siguientes datos en el cliente Juez:
* **CI:** 11021654
* **Nombres:** Juan Perez
* **Apellidos:** Segovia

**Resultado esperado:**
* Banco Mercantil: Cuenta 1515, Saldo 5200.0
* Banco BCP: Cuenta 657654, Saldo 6000.0
