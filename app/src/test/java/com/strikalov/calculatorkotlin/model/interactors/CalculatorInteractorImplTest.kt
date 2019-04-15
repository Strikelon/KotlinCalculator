package com.strikalov.calculatorkotlin.model.interactors

import com.strikalov.calculatorkotlin.model.entites.Calculator
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class CalculatorInteractorImplTest {

    lateinit var interactorImplTest: CalculatorInteractorImpl

    @Mock
    lateinit var calculator: Calculator

    @Before
    fun setUp() {

        interactorImplTest = CalculatorInteractorImpl(calculator, Schedulers.trampoline(), Schedulers.trampoline())

    }

    @Test
    fun plusTest1() {

        val testObserver = TestObserver<String>()
        `when`(calculator.plus(BigDecimal("6.0"),BigDecimal("4.0"))).thenReturn(BigDecimal("10.0"))

        interactorImplTest.plus("6.0", "4.0").subscribe(testObserver)
        testObserver.assertValue("10.0")

    }

    @Test
    fun plusTest2() {

        val testObserver = TestObserver<String>()
        `when`(calculator.plus(BigDecimal("16.3"),BigDecimal("30.0"))).thenReturn(BigDecimal("46.3"))

        interactorImplTest.plus("16.3", "30.0").subscribe(testObserver)
        testObserver.assertValue("46.3")

    }

    @Test
    fun plusTest3() {

        val testObserver = TestObserver<String>()
        `when`(calculator.plus(BigDecimal("20.0"),BigDecimal("18.071"))).thenReturn(BigDecimal("38.071"))

        interactorImplTest.plus("20.0", "18.071").subscribe(testObserver)
        testObserver.assertValue("38.071")

    }

    @Test
    fun plusTest4() {

        val testObserver = TestObserver<String>()
        `when`(calculator.plus(BigDecimal("13.033"),BigDecimal("4.2"))).thenReturn(BigDecimal("17.233"))

        interactorImplTest.plus("13.033", "4.2").subscribe(testObserver)
        testObserver.assertValue("17.233")

    }

    @Test
    fun minusTest1() {

        val testObserver = TestObserver<String>()
        `when`(calculator.minus(BigDecimal("20.0"),BigDecimal("15.0"))).thenReturn(BigDecimal("5.0"))

        interactorImplTest.minus("20.0", "15.0").subscribe(testObserver)
        testObserver.assertValue("5.0")

    }

    @Test
    fun minusTest2() {

        val testObserver = TestObserver<String>()
        `when`(calculator.minus(BigDecimal("15.0"),BigDecimal("4.8"))).thenReturn(BigDecimal("10.2"))

        interactorImplTest.minus("15.0", "4.8").subscribe(testObserver)
        testObserver.assertValue("10.2")

    }

    @Test
    fun minusTest3() {

        val testObserver = TestObserver<String>()
        `when`(calculator.minus(BigDecimal("22.28"),BigDecimal("17.0"))).thenReturn(BigDecimal("5.28"))

        interactorImplTest.minus("22.28", "17.0").subscribe(testObserver)
        testObserver.assertValue("5.28")

    }

    @Test
    fun minusTest4() {

        val testObserver = TestObserver<String>()
        `when`(calculator.minus(BigDecimal("36.6"),BigDecimal("12.2"))).thenReturn(BigDecimal("24.4"))

        interactorImplTest.minus("36.6", "12.2").subscribe(testObserver)
        testObserver.assertValue("24.4")

    }

    @Test
    fun multiplicationTest1() {

        val testObserver = TestObserver<String>()
        `when`(calculator.multiplication(BigDecimal("7.0"),BigDecimal("7.0"))).thenReturn(BigDecimal("49.0"))

        interactorImplTest.multiplication("7.0", "7.0").subscribe(testObserver)
        testObserver.assertValue("49.0")

    }

    @Test
    fun multiplicationTest2() {

        val testObserver = TestObserver<String>()
        `when`(calculator.multiplication(BigDecimal("5.6"),BigDecimal("2.0"))).thenReturn(BigDecimal("11.2"))

        interactorImplTest.multiplication("5.6", "2.0").subscribe(testObserver)
        testObserver.assertValue("11.2")

    }

    @Test
    fun multiplicationTest3() {

        val testObserver = TestObserver<String>()
        `when`(calculator.multiplication(BigDecimal("18.0"),BigDecimal("16.3"))).thenReturn(BigDecimal("293.4"))

        interactorImplTest.multiplication("18.0", "16.3").subscribe(testObserver)
        testObserver.assertValue("293.4")

    }

    @Test
    fun multiplicationTest4() {

        val testObserver = TestObserver<String>()
        `when`(calculator.multiplication(BigDecimal("56.6"),BigDecimal("12.7"))).thenReturn(BigDecimal("718.82"))

        interactorImplTest.multiplication("56.6", "12.7").subscribe(testObserver)
        testObserver.assertValue("718.82")

    }

    @Test
    fun divisionTest1() {

        val testObserver = TestObserver<String>()
        `when`(calculator.division(BigDecimal("14.0"),BigDecimal("7.0"))).thenReturn(BigDecimal("2.0"))

        interactorImplTest.division("14.0", "7.0").subscribe(testObserver)
        testObserver.assertValue("2.0")

    }

    @Test
    fun divisionTest2() {

        val testObserver = TestObserver<String>()
        `when`(calculator.division(BigDecimal("0.0"),BigDecimal("7.17"))).thenReturn(BigDecimal("0.0"))

        interactorImplTest.division("0.0", "7.17").subscribe(testObserver)
        testObserver.assertValue("0.0")

    }

    @Test
    fun divisionTest3() {

        val testObserver = TestObserver<String>()
        `when`(calculator.division(BigDecimal("15.0"),BigDecimal("7.0"))).thenReturn(BigDecimal("2.14285714286"))

        interactorImplTest.division("15.0", "7.0").subscribe(testObserver)
        testObserver.assertValue("2.14286e+00")

    }

    @Test
    fun divisionTest4() {

        val testObserver = TestObserver<String>()
        `when`(calculator.division(BigDecimal("90.0"),BigDecimal("-2.3"))).thenReturn(BigDecimal("-39.13043478261"))

        interactorImplTest.division("90.0", "-2.3").subscribe(testObserver)
        testObserver.assertValue("-3.91304e+01")

    }

    @Test
    fun divisionOnZero(){

        val testObserver = TestObserver<String>()
        `when`(calculator.division(BigDecimal("10.0"),BigDecimal("0.0")))
            .thenThrow(ArithmeticException("Нельзя делить на 0"))

        interactorImplTest.division("10.0", "0.0").subscribe(testObserver)
        testObserver.assertError(ArithmeticException::class.java)

    }
}