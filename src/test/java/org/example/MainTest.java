package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainPrimeTest {

    //check primes
    @Test
    void primes() {
        assertTrue(Main.isPrime(2));
        assertTrue(Main.isPrime(3));
        assertTrue(Main.isPrime(7));
        assertTrue(Main.isPrime(2147483647));
    }

    //check non primes
    @Test
    void nonPrimesAndEdgeCases() {
        assertFalse(Main.isPrime(-5));
        assertFalse(Main.isPrime(0));
        assertFalse(Main.isPrime(1));
        assertFalse(Main.isPrime(100));
    }
}
