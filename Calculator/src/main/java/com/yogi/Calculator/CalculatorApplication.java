package com.yogi.Calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

    public int integerDivision(int i, int i1) {
		int result = i/i1;
		return  result;
    }

	public  int integerSubstraction(int i, int i2) {
		return i - i2;
	}
}
