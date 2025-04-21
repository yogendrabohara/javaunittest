package com.yogi.Calculator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Test Method in Calculator class")
class CalculatorApplicationTests {
	CalculatorApplication calculatorApplication;
	@BeforeAll
	static void setup() {
		System.out.println("Executing @BeforeAll method");



	}

	@AfterAll
	static void cleanup(){
		System.out.println("Executing @AfterAll method");

	}



	@BeforeEach
	void beforeEachTestMethod() {
		calculatorApplication = new CalculatorApplication();
		System.out.println("Executing @BeforeEach method");
	}


	@AfterEach
	void afterEachTestMethod() {
		System.out.println("Executing @AfterEach method");

	}

	@DisplayName("Test 4/2 = 2")
	@Test
	void testIntegerDivision_whenFourIsDividedByTwo_ShouldReturnTwo() {
		//Arrange  ---> Given
		System.out.println("Four divison by 2");
		int dividend = 4;
		int divisor = 2;
		int expectedResult = 2;

		//Act ----> when
		int actualResult = calculatorApplication.integerDivision(4, 2);

		//Assert ----> Then
		assertEquals(expectedResult, actualResult, "Integer division works well");
	}

	@Test
	@DisplayName("Division by zero")
	//@Disabled
	void testIntegerDivision_WhenDividendIsDividedByZero_shouldThrowArithmeticException() {
		System.out.println("Running division by zero");
		int dividend = 4;
		int divisor = 0;
		String expectedExpectionMessage= "/ by zero";
		//Act & Assert
		ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
			//Act
			calculatorApplication.integerDivision(dividend, divisor);

				}, "division by zero should have thrown an Arthimetic exception.");

		//Assert
		assertEquals(expectedExpectionMessage, actualException);
		//fail("Not implemented yet");
	}


//	@Test
//	@DisplayName("Test 33-1 = 32 without parameters")
//	void testIntegerSubstraction_WhenTwoIsSubstractedFromFour_shouldReturnTwo() {
//		//arrange
//		System.out.println("Substracting one from 33");
////        int result = calculatorApplication.integerSubstraction(4, 2 );
////        assertEquals(2, result);
//		int minuend = 33;
//		int subtrahead = 1;
//		int expectedResult = 32;
//
//		int actualResult = calculatorApplication.integerSubstraction(minuend, subtrahead);
//		assertEquals(expectedResult,
//				actualResult, () -> minuend
//						+ "- " + subtrahead + " did not produce " + expectedResult);
//	}


//public static Stream<Arguments> integerSubstractionInputParameters() {
//	return Stream.of(
//			Arguments.of(33,1,32),
//			Arguments.of(20,2,18),
//			Arguments.of(45,10,35),
//			Arguments.of(6,2,4)
//	);
//}
//
//
//@DisplayName("Test integer substraction [minuend, subtrahead, expectedResult]  --> with parameters")
//@ParameterizedTest
//@MethodSource("integerSubstractionInputParameters")
//void integerSubstraction(int minuend, int subtrahead, int expectedResult) {
//	System.out.println("Substracting " + minuend + " - " +  subtrahead + " = " + expectedResult);
//	int actualResult = calculatorApplication.integerSubstraction(minuend, subtrahead);
//	assertEquals(expectedResult,
//			actualResult, () -> minuend
//					+ "- " + subtrahead + " did not produce " + expectedResult);
//}






	@DisplayName("Test integer substraction [minuend, subtrahead, expectedResult]  --> with parameters")
	@ParameterizedTest
	@CsvFileSource(resources = "/integerSubstraction.csv")
//	@CsvSource({
//			"34, 1, 33",
//			"25, 2 , 23",
//			"11, 9, 2"
//
//	})
	//	@CsvSource({
	//			"apple, orange",
	//			"apple, '' ",
	//			"apple, "
	//
	//	})
	void integerSubstraction(int minuend, int subtrahead, int expectedResult) {
		System.out.println("Substracting " + minuend + " - " +  subtrahead + " = " + expectedResult);
		int actualResult = calculatorApplication.integerSubstraction(minuend, subtrahead);
		assertEquals(expectedResult,
				actualResult, () -> minuend
						+ "- " + subtrahead + " did not produce " + expectedResult);
	}


	@ParameterizedTest
	@ValueSource(strings = {"John", "Kate", "Alice"})
	void valueSourceDemonstration(String firstName){
		System.out.println(firstName);
		assertNotNull(firstName);

	}

}



