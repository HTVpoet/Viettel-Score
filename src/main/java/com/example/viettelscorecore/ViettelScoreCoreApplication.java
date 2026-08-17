package com.example.viettelscorecore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class ViettelScoreCoreApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");
		SpringApplication.run(ViettelScoreCoreApplication.class, args);
	}

}
