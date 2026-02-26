package com.mcmaster.sfwreng3s03;

public class Question4 {
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public double oldDivide(double a, double b) {
        return a / b;
    }
}