package com.strikalov.calculatorkotlin.presenter

import com.strikalov.calculatorkotlin.common.CalculatorNumberElements
import com.strikalov.calculatorkotlin.common.CalculatorOperations
import com.strikalov.calculatorkotlin.model.interactors.CalculatorInteractor
import com.strikalov.calculatorkotlin.view.MainView
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest{

    lateinit var mainPresenter: MainPresenter

    @Mock
    lateinit var calculatorInteractor: CalculatorInteractor

    @Mock
    lateinit var mainView: MainView

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(calculatorInteractor)
        mainPresenter.attachView(mainView)
    }

    /** Тестируем кнопку reset */
    @Test
    fun resetCalculatorTest(){

        mainPresenter.onNumberButton(CalculatorNumberElements.ONE)
        mainPresenter.onNumberButton(CalculatorNumberElements.TWO)
        mainPresenter.onNumberButton(CalculatorNumberElements.THREE)
        mainPresenter.onButtonReset()

        verify(mainView, times(2)).showInfo("0")
        verify(mainView).showInfo("1")
        verify(mainView).showInfo("12")
        verify(mainView).showInfo("123")

    }

    /** Тестируем кнопку с точкой*/
    @Test
    fun pointSetCalculatorTest(){

        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.EIGHT)

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("5")
        verify(mainView).showInfo("5.")
        verify(mainView, times(2)).showInfo("5.7")
        verify(mainView).showInfo("5.78")
    }

    /** Тестируем кноку смены знака +- */
    @Test
    fun changeSignCalculatorTest(){

        mainPresenter.onNumberButton(CalculatorNumberElements.TWO)
        mainPresenter.onNumberButton(CalculatorNumberElements.EIGHT)
        mainPresenter.onButtonChangeSign()
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onButtonChangeSign()
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onButtonChangeSign()

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("2")
        verify(mainView).showInfo("28")
        verify(mainView).showInfo("-28")
        verify(mainView).showInfo("-28.")
        verify(mainView).showInfo("28.")
        verify(mainView).showInfo("28.5")
        verify(mainView).showInfo("28.56")
        verify(mainView).showInfo("-28.56")
    }

    /** Тестируем операцию сложения и кнопку равно */
    @Test
    fun plusOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.plus("6","4")).thenReturn(Single.just("10"))

        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onArithmeticButton(CalculatorOperations.PLUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.FOUR)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("6")
        verify(mainView).showInfo("4")
        verify(mainView).showInfo("10")

    }

    /** Тестируем, когда после операции сложения снова делаем операцию сложения, и потом равно*/
    @Test
    fun twoPlusOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.plus("5.5","6.5")).thenReturn(Single.just("12"))

        Mockito.`when`(calculatorInteractor.plus("12","7")).thenReturn(Single.just("19"))

        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.PLUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.PLUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")

        verify(mainView).showInfo("5")
        verify(mainView).showInfo("5.")
        verify(mainView).showInfo("5.5")

        verify(mainView).showInfo("6")
        verify(mainView).showInfo("6.")
        verify(mainView).showInfo("6.5")

        verify(mainView).showInfo("12")

        verify(mainView).showInfo("7")
        verify(mainView).showInfo("19")

    }

    /** Тестируем операцию вычитание и кнопку равно */
    @Test
    fun minusOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.minus("6","4")).thenReturn(Single.just("2"))

        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onArithmeticButton(CalculatorOperations.MINUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.FOUR)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("6")
        verify(mainView).showInfo("4")
        verify(mainView).showInfo("2")

    }

    /** Тестируем, когда после операции вычитание снова делаем операцию вычитание, и потом равно*/
    @Test
    fun twoMinusOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.minus("5.5","6.5")).thenReturn(Single.just("-1"))

        Mockito.`when`(calculatorInteractor.minus("-1","7")).thenReturn(Single.just("-8"))

        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.MINUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.MINUS)
        mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")

        verify(mainView).showInfo("5")
        verify(mainView).showInfo("5.")
        verify(mainView).showInfo("5.5")

        verify(mainView).showInfo("6")
        verify(mainView).showInfo("6.")
        verify(mainView).showInfo("6.5")

        verify(mainView).showInfo("-1")

        verify(mainView).showInfo("7")
        verify(mainView).showInfo("-8")

    }

    /** Тестируем операцию умножения и кнопку равно */
    @Test
    fun multiplicationOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.multiplication("6","4")).thenReturn(Single.just("24"))

        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onArithmeticButton(CalculatorOperations.MULTIPLICATION)
        mainPresenter.onNumberButton(CalculatorNumberElements.FOUR)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("6")
        verify(mainView).showInfo("4")
        verify(mainView).showInfo("24")

    }


    /** Тестируем, когда после операции умножение снова делаем операцию умножение, и потом равно*/
    @Test
    fun twoMultiplicationOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.multiplication("5.5","6.5")).thenReturn(Single.just("35.75"))

        Mockito.`when`(calculatorInteractor.multiplication("35.75","7")).thenReturn(Single.just("250.25"))

        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.MULTIPLICATION)
        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.MULTIPLICATION)
        mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")

        verify(mainView).showInfo("5")
        verify(mainView).showInfo("5.")
        verify(mainView).showInfo("5.5")

        verify(mainView).showInfo("6")
        verify(mainView).showInfo("6.")
        verify(mainView).showInfo("6.5")

        verify(mainView).showInfo("35.75")

        verify(mainView).showInfo("7")
        verify(mainView).showInfo("250.25")

    }

    /** Тестируем операцию деление и кнопку равно */
    @Test
    fun divisionOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.division("6","4")).thenReturn(Single.just("1.5"))

        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onArithmeticButton(CalculatorOperations.DIVISION)
        mainPresenter.onNumberButton(CalculatorNumberElements.FOUR)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")
        verify(mainView).showInfo("6")
        verify(mainView).showInfo("4")
        verify(mainView).showInfo("1.5")

    }

    /** Тестируем, когда после операции деление снова делаем операцию деление, и потом равно*/
    @Test
    fun twoDivisionOperationCalculatorTest(){

        Mockito.`when`(calculatorInteractor.division("5.5","6.5")).thenReturn(Single.just("8.46154e-01"))

        Mockito.`when`(calculatorInteractor.division("8.46154e-01","7")).thenReturn(Single.just("1.20879e-01"))

        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.DIVISION)
        mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
        mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
        mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
        mainPresenter.onArithmeticButton(CalculatorOperations.DIVISION)
        mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
        mainPresenter.onButtonEqually()

        verify(mainView).showInfo("0")

        verify(mainView).showInfo("5")
        verify(mainView).showInfo("5.")
        verify(mainView).showInfo("5.5")

        verify(mainView).showInfo("6")
        verify(mainView).showInfo("6.")
        verify(mainView).showInfo("6.5")

        verify(mainView).showInfo("8.46154e-01")

        verify(mainView).showInfo("7")
        verify(mainView).showInfo("1.20879e-01")

    }

}