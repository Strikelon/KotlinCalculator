package com.strikalov.calculatorkotlin.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.strikalov.calculatorkotlin.common.CalculatorNumberElements
import com.strikalov.calculatorkotlin.common.CalculatorOperations
import com.strikalov.calculatorkotlin.model.interactors.CalculatorInteractor
import com.strikalov.calculatorkotlin.view.MainView
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(interactor: CalculatorInteractor): MvpPresenter<MainView>() {

    private val calculatorInteractor: CalculatorInteractor = interactor

    companion object {
        /** Константа равная 0*/
        private const val ZERO_RESULT = "0"

        /** Константа равная -0*/
        private const val NEGATIVE_ZERO_RESULT = "-0"

        /** Константа равная минусу*/
        private const val MINUS = '-'
    }

    /**
     * Инициализация:
     * Первое и второе число, которые вводит пользователь для арифмитических операций
     */
    private var firstNumber = StringBuilder(ZERO_RESULT)
    private var secondNumber = StringBuilder(ZERO_RESULT)

    /** Флаг, который сообщает: поставлена точка или нет*/
    private var isPointSet = false
    /**Флаг, который сообщает: какое число набирает пользователь, первое или второе*/
    private var isFirstNumberPreparing = true
    /**Флаг, который равеен true, если второе число вводить еще не начали, и false если ввод уже в процессе */
    private var isBeforeSecondNumberInsert = true

    /** При выборе пользователем арифмитической операции, она будет хранится в этой переменной
     * либо переменная будет равна NONE*/
    private var selectedArithmeticOperation = CalculatorOperations.NONE

    /**
     * При первом аттаче активити показываем в поле вывода 0
     */
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showInfo(ZERO_RESULT)
    }

    /**
     * При нажатии на кнопку reset вызываем метод resetCalculator
     * и просим его вывести на экран 0
     */
    fun onButtonReset(){
        resetCalculator(ZERO_RESULT)
    }

    /**
     * Устанавливает переменным калкулятора исходные значения и выводит заданное сообщение на экран или 0
     */
    private fun resetCalculator(message: String?){
        firstNumber = StringBuilder(ZERO_RESULT)
        secondNumber = StringBuilder(ZERO_RESULT)
        isPointSet = false
        isFirstNumberPreparing = true
        isBeforeSecondNumberInsert = true
        selectedArithmeticOperation = CalculatorOperations.NONE
        message?.let {
            viewState.showInfo(message)
        } ?: viewState.showInfo(ZERO_RESULT)

    }

    /**
     * Когда пользователь нажимает на кнопку смены знака редактируемого числа, вызывается этот метод.
     * Определяем, какое число вводит пользователь и передаем его в метод changeNumberSign()
     */
    fun onButtonChangeSign(){
        if(isFirstNumberPreparing) {
            if (selectedArithmeticOperation == CalculatorOperations.NONE) {
                changeNumberSign(firstNumber)
            } else {
                isFirstNumberPreparing = false
                isBeforeSecondNumberInsert = false
                isPointSet = false
                changeNumberSign(secondNumber)
            }
        }else{
            if(selectedArithmeticOperation == CalculatorOperations.NONE) {
                isFirstNumberPreparing = true
                isPointSet = false
                changeNumberSign(firstNumber)
            }else {
                isBeforeSecondNumberInsert = false
                changeNumberSign(secondNumber)
            }
        }
    }

    /**
     * В этом методе меняется знак у числа, которое редактирует пользователь
     * и оно выводится на экран
     */
    private fun changeNumberSign(preparingNumber: StringBuilder){
        if(preparingNumber[0] == MINUS){
            preparingNumber.deleteCharAt(0)
        }else{
            preparingNumber.insert(0, MINUS)
        }
        viewState.showInfo(preparingNumber.toString())

    }

    /**
     *  Метод срабатывает, при нажатии кнопки "Равно". Здесь у интерактора запрашивается выполнение
     *  нужной арифмитической операции с заданными пользователем числами.
     */
    fun onButtonEqually(calculatorOperations: CalculatorOperations = CalculatorOperations.NONE){
        if(selectedArithmeticOperation != CalculatorOperations.NONE){
            when(selectedArithmeticOperation){
                CalculatorOperations.PLUS ->
                    calculatorInteractor.plus(firstNumber.toString(), secondNumber.toString()).subscribe(ArithmeticOperationObserver(calculatorOperations))
                CalculatorOperations.MINUS ->
                    calculatorInteractor.minus(firstNumber.toString(), secondNumber.toString()).subscribe(ArithmeticOperationObserver(calculatorOperations))
                CalculatorOperations.MULTIPLICATION ->
                    calculatorInteractor.multiplication(firstNumber.toString(), secondNumber.toString()).subscribe(ArithmeticOperationObserver(calculatorOperations))
                CalculatorOperations.DIVISION ->
                    calculatorInteractor.division(firstNumber.toString(), secondNumber.toString()).subscribe(ArithmeticOperationObserver(calculatorOperations))
            }
        }
    }

    /**
     * Метод срабатывает при нажатии на любую кнопку с арифмитической операцией.
     * - если оба числа еще не заданы, то выбранная арифмитическая операция записывается в переменную selectedArithmeticOperation
     * - если оба числа уже заданы, и логически пользователь должен нажать кнопку "равно", метод определяет, что сложилась именно такая
     * ситуация и вызывает метод onButtonEqually() c сохранением переданного знака
     */
    fun onArithmeticButton(calculatorOperation: CalculatorOperations){
        if(isFirstNumberPreparing){

            selectedArithmeticOperation = calculatorOperation

        }else{

            if(isBeforeSecondNumberInsert){

                selectedArithmeticOperation = calculatorOperation

            }else {

                onButtonEqually(calculatorOperation)

            }

        }
    }

    /**
     * При нажатии цифры или точки пользователем, данные попадают в этот метод.
     * Мы определяем, какое число вводит пользователь (первое или второе), и передаем его в метод
     * prepareNumber(), который собирает число из полученных элементов
     */
    fun onNumberButton(calculatorNumberElement: CalculatorNumberElements) {
        if (isFirstNumberPreparing) {
            if (selectedArithmeticOperation == CalculatorOperations.NONE) {
                prepareNumber(calculatorNumberElement, firstNumber)
            } else {
                isFirstNumberPreparing = false
                isPointSet = false
                isBeforeSecondNumberInsert = false
                prepareNumber(calculatorNumberElement, secondNumber)
            }
        } else {
            if(selectedArithmeticOperation == CalculatorOperations.NONE){
                isFirstNumberPreparing = true
                isPointSet = false
                firstNumber.clear().append(ZERO_RESULT)
                prepareNumber(calculatorNumberElement, firstNumber)
            }else{
                isBeforeSecondNumberInsert = false
                prepareNumber(calculatorNumberElement, secondNumber)
            }
        }
    }

    /**
     * В этом методе собирается число, которое вводит пользователь,
     * и каждый этап выводится на экран
     */
    private fun prepareNumber(calculatorNumberElement: CalculatorNumberElements, preparingNumber: StringBuilder) {

        if (calculatorNumberElement.value == CalculatorNumberElements.POINT.value) {
            if (!isPointSet) {
                preparingNumber.append(calculatorNumberElement.value)
                isPointSet = true
            }
        } else if (preparingNumber.toString() == ZERO_RESULT) {
            preparingNumber.clear().append(calculatorNumberElement.value)
        } else if (preparingNumber.toString() == NEGATIVE_ZERO_RESULT) {
            preparingNumber.clear().append(MINUS).append(calculatorNumberElement.value)
        } else {
            preparingNumber.append(calculatorNumberElement.value)
        }

        viewState.showInfo(preparingNumber.toString())
    }


    /**
     * Наблюдатель, ожидает результата выполнения арифмитической операции у интерактора, в случае успеха выводит результат
     * на экран, в случае ошибки, метод resetCalculator для сброса всех флагов и вывода ошибки на экран
     */
    private inner class ArithmeticOperationObserver(val calculatorOperations: CalculatorOperations = CalculatorOperations.NONE )
        : DisposableSingleObserver<String>() {
        override fun onSuccess(t: String) {
            firstNumber = StringBuilder(t)
            secondNumber = StringBuilder(ZERO_RESULT)
            isPointSet = false
            selectedArithmeticOperation = calculatorOperations
            isBeforeSecondNumberInsert = true

            viewState.showInfo(firstNumber.toString())
        }

        override fun onError(e: Throwable) {
            resetCalculator("Error: ${e.message}")
        }
    }
}