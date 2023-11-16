package com.example.springbootjwtauthentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@SpringBootTest
class SpringBootJwtAuthenticationApplicationTests {

	@Test
	void contextLoads() {
		int a = 42_339;
		System.out.printf("a_0 = %d%n", a%10);
		System.out.printf("a_1 = %d%n", (a/10)%10);
		System.out.printf("a_2 = %d%n", (a/1_00)%10);
		System.out.printf("a_3 = %d%n", (a/1_000)%10);
		System.out.printf("a_4 = %d%n", (a/10_000)%10);

		System.out.println("separarDigitos(a) = " + separarDigitos(a));
	}

	private List<Integer> separarDigitos(Integer n){
		String str = n.toString();

		ArrayList<Integer> resultado = new ArrayList<>();
		for (char a : str.toCharArray()){
			resultado.add(Integer.valueOf(String.valueOf(a)));
		}
		
		return resultado;
	}

	private List<Integer> separarDigitosB(Integer n){
		String str = n.toString();

		ArrayList<Integer> resultado = new ArrayList<>();
		for (char a : str.toCharArray()){
			resultado.add(Integer.valueOf(String.valueOf(a)));
		}

		return resultado;
	}

}
