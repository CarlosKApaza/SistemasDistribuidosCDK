/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejemplo_workqueuesrabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 *
 * @author cdk04
 */
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
             
            // durable = true (La cola no se borra si se reinicia RabbitMQ)
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            // Enviamos 5 tareas automáticamente para ver el sorteo
            for (int i = 1; i <= 5; i++) {
                // Cada tarea tiene una cantidad diferente de puntos (segundos de trabajo)
                String message = "Tarea " + i + " " + ".".repeat(i);

                // PERSISTENT_TEXT_PLAIN (El mensaje se guarda en disco)
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                        
                System.out.println(" [x] Enviado: '" + message + "'");
            }
        }
    }
}