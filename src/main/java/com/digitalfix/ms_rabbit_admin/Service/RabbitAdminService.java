package com.digitalfix.msrabbitadmin.service;

import com.digitalfix.msrabbitadmin.dto.BindingRequest;
import com.digitalfix.msrabbitadmin.dto.ExchangeRequest;
import com.digitalfix.msrabbitadmin.dto.QueueRequest;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private final AmqpAdmin amqpAdmin;

    public RabbitAdminService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void crearCola(QueueRequest request) {
        Queue queue = new Queue(
            request.name(),
            true,
            false,
            false
        );

        amqpAdmin.declareQueue(queue);
    }

    public void eliminarCola(String name) {
        amqpAdmin.deleteQueue(name);
    }

    public void crearExchange(ExchangeRequest request) {
        String name = request.name();
        String type = request.type().toLowerCase();

        switch (type) {
            case "direct" ->
                amqpAdmin.declareExchange(
                    new DirectExchange(
                        name, request.durable(), request.autoDelete()
                    )
                );

            case "topic" ->
                amqpAdmin.declareExchange(
                    new TopicExchange(
                        name, request.durable(), request.autoDelete()
                    )
                );

            case "fanout" ->
                amqpAdmin.declareExchange(
                    new FanoutExchange(
                        name, request.durable(), request.autoDelete()
                    )
                );

            case "headers" ->
                amqpAdmin.declareExchange(
                    new HeadersExchange(
                        name, request.durable(), request.autoDelete()
                    )
                );

            default ->
                throw new IllegalArgumentException(
                    "Tipo de exchange no permitido"
                );
        }
    }

    public void eliminarExchange(String name) {
        amqpAdmin.deleteExchange(name);
    }

    public void crearBinding(BindingRequest request) {
        Binding binding = new Binding(
            request.queue(),
            Binding.DestinationType.QUEUE,
            request.exchange(),
            request.routingKey(),
            null
        );

        amqpAdmin.declareBinding(binding);
    }

    public void eliminarBinding(BindingRequest request) {
        Binding binding = new Binding(
            request.queue(),
            Binding.DestinationType.QUEUE,
            request.exchange(),
            request.routingKey(),
            null
        );

        amqpAdmin.removeBinding(binding);
    }
}