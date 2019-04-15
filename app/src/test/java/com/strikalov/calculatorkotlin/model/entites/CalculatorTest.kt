package com.strikalov.calculatorkotlin.model.entites

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal

class CalculatorTest {

    lateinit var calculator: Calculator

    @Before
    fun setUp() {

        calculator = Calculator()

    }

    @Test
    fun plus() {
        assertTrue(BigDecimal("10.0").compareTo(calculator.plus(BigDecimal("6.0"),BigDecimal("4.0"))) == 0)
        assertTrue(BigDecimal("46.3").compareTo(calculator.plus(BigDecimal("16.3"),BigDecimal("30.0"))) == 0)
        assertTrue(BigDecimal("38.071").compareTo(calculator.plus(BigDecimal("20.0"),BigDecimal("18.071"))) == 0)
        assertTrue(BigDecimal("17.233").compareTo(calculator.plus(BigDecimal("13.033"),BigDecimal("4.2"))) == 0)
    }

    @Test
    fun minus() {
        assertTrue(BigDecimal("5.0").compareTo(calculator.minus(BigDecimal("20.0"),BigDecimal("15.0"))) == 0)
        assertTrue(BigDecimal("10.2").compareTo(calculator.minus(BigDecimal("15.0"),BigDecimal("4.8"))) == 0)
        assertTrue(BigDecimal("5.28").compareTo(calculator.minus(BigDecimal("22.28"),BigDecimal("17.0"))) == 0)
        assertTrue(BigDecimal("24.4").compareTo(calculator.minus(BigDecimal("36.6"),BigDecimal("12.2"))) == 0)
    }

    @Test
    fun multiplication() {
        assertTrue(BigDecimal("49.0").compareTo(calculator.multiplication(BigDecimal("7.0"),BigDecimal("7.0"))) == 0)
        assertTrue(BigDecimal("11.2").compareTo(calculator.multiplication(BigDecimal("5.6"),BigDecimal("2.0"))) == 0)
        assertTrue(BigDecimal("293.4").compareTo(calculator.multiplication(BigDecimal("18.0"),BigDecimal("16.3"))) == 0)
        assertTrue(BigDecimal("718.82").compareTo(calculator.multiplication(BigDecimal("56.6"),BigDecimal("12.7"))) == 0)
    }

    @Test
    fun division() {
        assertTrue(BigDecimal("2.0").compareTo(calculator.division(BigDecimal("14.0"),BigDecimal("7.0"))) == 0)
        assertTrue(BigDecimal("0.0").compareTo(calculator.division(BigDecimal("0.0"),BigDecimal("7.17"))) == 0)
        assertTrue(BigDecimal("2.14285714286").compareTo(calculator.division(BigDecimal("15.0"),BigDecimal("7.0"))) == 0)
        assertTrue(BigDecimal("39.13043478261").compareTo(calculator.division(BigDecimal("90.0"),BigDecimal("2.3"))) == 0)
    }

    @Test(expected = ArithmeticException::class)
    fun divisionOnZero(){

        calculator.division(BigDecimal("10.0"),BigDecimal("0.0"))

    }
}