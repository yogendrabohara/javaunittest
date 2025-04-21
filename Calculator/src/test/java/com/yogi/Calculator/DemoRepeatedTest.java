package com.yogi.Calculator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DemoRepeatedTest {
    CalculatorApplication calculatorApplication;


    @BeforeEach
    void beforeEachTestMethod() {
        calculatorApplication = new CalculatorApplication();
        System.out.println("Executing @BeforeEach method");
    }
    // @RepeatedTest(3)
    @RepeatedTest(value = 3, name = "{displayName}, Repetition {currentRepetition} of }" +  "{totalRepetitions}")
    @DisplayName("Division by zero")
        //@Disabled
    void testIntegerDivision_WhenDividendIsDividedByZero_shouldThrowArithmeticException(
            RepetitionInfo repetitionInfo,
            TestInfo testInfo
    ) {
        System.out.println("Running " + testInfo.getTestMethod().get().getName());
        System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());
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

}
