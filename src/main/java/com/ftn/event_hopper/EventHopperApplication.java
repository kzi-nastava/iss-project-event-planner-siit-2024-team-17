package com.ftn.event_hopper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class EventHopperApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventHopperApplication.class, args);
	}

}
