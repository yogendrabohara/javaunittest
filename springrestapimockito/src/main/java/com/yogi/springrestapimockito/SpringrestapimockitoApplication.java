package com.yogi.springrestapimockito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.yogi.springrestapimockito.io")
public class SpringrestapimockitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringrestapimockitoApplication.class, args);
	}

}
