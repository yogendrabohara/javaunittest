package com.yogi.Calculator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodsOrderedByOrderIndex {
    @Order(1)
    @Test
    void testA() {
        System.out.println("Running test A");
    }


    @Order(4)
    @Test
    void testC() {
        System.out.println("Running test C");
    }
    @Order(2)
    @Test
    void testB() {
        System.out.println("Running test b");
    }

    @Order(3)
    @Test
    void testD() {
        System.out.println("Running test D");
    }

}
