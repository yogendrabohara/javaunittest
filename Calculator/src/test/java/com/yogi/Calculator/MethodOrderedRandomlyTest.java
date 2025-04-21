package com.yogi.Calculator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class MethodOrderedRandomlyTest {
    @Test
    void testA() {
        System.out.println("Running test A");
    }

    @Test
    void testB() {
        System.out.println("Running test b");
    }


    @Test
    void testC() {
        System.out.println("Running test C");
    }

    @Test
    void testD() {
        System.out.println("Running test D");
    }

    @Test
    void testE() {
        System.out.println("Running test E");
    }


}
