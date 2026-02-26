package com.mcmaster.sfwreng3s03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class Question4Test {

    private Question4 calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Question4();
    }

    // Initial Test Suite (2 tests)

    @Test
    public void testSimpleDivision() {
        assertEquals(2.0, calculator.divide(10.0, 5.0), "10 / 5 = 2.0");
    }

    @Test
    public void testNegativeDivision() {
        assertEquals(-3.0, calculator.divide(9.0, -3.0), "9 / -3 = -3.0");
    }

    // Improved Test Suite (2 more tests)

    @Test
    public void testDivisionByZero() {
        // Testing missing functionality identified in step (c)
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10.0, 0.0);
        }, "Dividing by zero must throw ArithmeticException");
    }

    @Test
    public void testDecimalDivision() {
        // Testing additonal cases!
        assertEquals(2.5, calculator.divide(5.0, 2.0), 0.001);
    }
}