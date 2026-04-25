package com.batchly.batchly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BatchlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchlyApplication.class, args);
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Admin@123   -> " + encoder.encode("Admin@123"));
        System.out.println("Coach@123   -> " + encoder.encode("Coach@123"));
        System.out.println("Teach@123   -> " + encoder.encode("Teach@123"));
        System.out.println("Student@123 -> " + encoder.encode("Student@123"));
	}

}
