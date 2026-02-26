package com.mcmaster.sfwreng3s03.question5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Professional Test Suite for Assignment 1, Question 5.
 * Targets 90% branch coverage across the e-commerce sub-system.
 */
public class Question5Test {

    // Original Test Suite!
    private OrderService orderService;
    private Order order;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        order = new Order();
    }

    // --- Core Logic & Branch Coverage ---

    @Test
    void testProcessOrder_SuccessfulCardPayment() {
        order.addItem(new OrderItem("Textbook", 1, 50.0));
        // Logic: 50 * 0.9 (Student) = 45.0. Tax = 45 * 0.2 = 9.0. Total = 54.0.
        double total = orderService.processOrder(order, "STUDENT10", "card");
        
        assertEquals(54.0, total, 0.001);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void testProcessOrder_InvalidPaymentCancellation() {
        order.addItem(new OrderItem("Coffee", 2, 5.0));
        double total = orderService.processOrder(order, null, "crypto");
        
        assertEquals(0, total);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    // --- Defensive Programming & Exception Coverage ---

    @Test
    void testOrder_StateLockdown() {
        order.addItem(new OrderItem("Item", 1, 10.0));
        orderService.processOrder(order, null, "paypal");
        
        // Tests the IllegalStateException branch in Order.java
        assertThrows(IllegalStateException.class, () -> order.addItem(new OrderItem("Late", 1, 5.0)));
    }

    @Test
    void testDiscount_InvalidCode() {
        DiscountService ds = new DiscountService();
        // Tests the IllegalArgumentException branch in DiscountService.java
        assertThrows(IllegalArgumentException.class, () -> ds.applyDiscount(100.0, "INVALID"));
    }

    @Test
    void testPayment_UnsupportedMethod() {
        PaymentValidator pv = new PaymentValidator();
        // Tests the UnsupportedOperationException branch in PaymentValidator.java
        assertThrows(UnsupportedOperationException.class, () -> pv.isPaymentMethodValid("cash"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"STUDENT10", "BLACKFRIDAY", "REGULAR"})
    void testDiscount_CodeBranches(String code) {
        DiscountService ds = new DiscountService();
        double result = ds.applyDiscount(100.0, code);
        assertTrue(result <= 100.0);
    }

    // Enhanced Test Suite after more prompting:

    @Test
    void testOrderItem_NegativeQuantity() {
        // Tests the (quantity <= 0) branch in OrderItem.java
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("Apple", -1, 1.0));
    }

    @Test
    void testOrderItem_ZeroQuantity() {
        // Tests the (quantity <= 0) branch with the boundary value 0
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("Apple", 0, 1.0));
    }

    @Test
    void testOrderItem_NegativeUnitPrice() {
        // Tests the (unitPrice < 0) branch in OrderItem.java
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("Apple", 1, -0.5));
    }

    @Test
    void testOrderItem_Getters() {
        // Tests the getQuantity() and getTotalPrice() methods
        OrderItem item = new OrderItem("Apple", 5, 2.0);
        assertEquals(5, item.getQuantity());
        assertEquals(10.0, item.getTotalPrice(), 0.001);
    }
}