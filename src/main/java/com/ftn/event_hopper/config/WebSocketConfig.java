package com.ftn.event_hopper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration

@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/socket") // Endpoint for initializing communication http://localhost:8080/api/socket/
                .setAllowedOriginPatterns("**");// Allow connections from any origin
    }

    /*
     * Metoda konfigurise opcije message brokera. U ovom slucaju klijenti koji hoce da koriste web socket broker
     * moraju da se konektuju na /socket-publisher.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/socket-subscriber") // Prefix for clients to send data to server
                .enableSimpleBroker("/notifications", "/messages"); // Topics that server will send data to clients
    }
}
