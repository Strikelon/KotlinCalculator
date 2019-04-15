package com.strikalov.calculatorkotlin.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.strikalov.calculatorkotlin.App
import com.strikalov.calculatorkotlin.R
import com.strikalov.calculatorkotlin.common.CalculatorNumberElements
import com.strikalov.calculatorkotlin.common.CalculatorOperations
import com.strikalov.calculatorkotlin.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provide() = mainPresenter

    init{
        App.getAppComponent().injectMainActivity(this)
    }

    /**
     * Текстовое поле для вывода результатов на экран
     */
    private lateinit var textResult: TextView

    /**
     * Кнопки для нажатия цифр
     */
    private lateinit var buttonZero: Button
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button

    /**
     * Листенер для кнопок с цифрами
     */
    private val numberButtonListener = NumberButtonsListener()

    /**
     * Кнопки для арифмитических операций
     */
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiplication: Button
    private lateinit var buttonDivision: Button

    /**
     * Листенер для кнопок с арифмитическими операциями
     */
    private val arithmeticButtonsListener = ArithmeticButtonsListener()

    /** Кнопка для обнуления результатов и текстового поля */
    private lateinit var buttonReset: Button

    /** Кнопка для смена знака +- */
    private lateinit var buttonChangeSign : Button

    /** Кнопка "Точка" */
    private lateinit var buttonPoint: Button

    /** Кнопка равно "=" */
    private lateinit var buttonEqually: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViewAndButtons()
        setButtonsListeners()

    }

    /**
     * Инициализация текстового поля и кнопок
     */
    private fun initTextViewAndButtons(){

        textResult = findViewById(R.id.text_result)

        buttonZero = findViewById(R.id.button_zero)
        buttonOne = findViewById(R.id.button_one)
        buttonTwo = findViewById(R.id.button_two)
        buttonThree = findViewById(R.id.button_three)
        buttonFour = findViewById(R.id.button_four)
        buttonFive = findViewById(R.id.button_five)
        buttonSix = findViewById(R.id.button_six)
        buttonSeven = findViewById(R.id.button_seven)
        buttonEight = findViewById(R.id.button_eight)
        buttonNine = findViewById(R.id.button_nine)

        buttonPlus = findViewById(R.id.button_plus)
        buttonMinus = findViewById(R.id.button_minus)
        buttonMultiplication = findViewById(R.id.button_multiplication)
        buttonDivision = findViewById(R.id.button_division)

        buttonReset = findViewById(R.id.button_reset)
        buttonChangeSign = findViewById(R.id.button_change_sign)
        buttonPoint = findViewById(R.id.button_point)
        buttonEqually = findViewById(R.id.button_equally)

    }

    /**
     * Установка кнопкам слушателей
     */
    private fun setButtonsListeners(){

        buttonZero.setOnClickListener(numberButtonListener)
        buttonOne.setOnClickListener(numberButtonListener)
        buttonTwo.setOnClickListener(numberButtonListener)
        buttonThree.setOnClickListener(numberButtonListener)
        buttonFour.setOnClickListener(numberButtonListener)
        buttonFive.setOnClickListener(numberButtonListener)
        buttonSix.setOnClickListener(numberButtonListener)
        buttonSeven.setOnClickListener(numberButtonListener)
        buttonEight.setOnClickListener(numberButtonListener)
        buttonNine.setOnClickListener(numberButtonListener)
        buttonPoint.setOnClickListener(numberButtonListener)

        buttonPlus.setOnClickListener(arithmeticButtonsListener)
        buttonMinus.setOnClickListener(arithmeticButtonsListener)
        buttonMultiplication.setOnClickListener(arithmeticButtonsListener)
        buttonDivision.setOnClickListener(arithmeticButtonsListener)

        buttonReset.setOnClickListener{
            mainPresenter.onButtonReset()
        }

        buttonChangeSign.setOnClickListener{
            mainPresenter.onButtonChangeSign()
        }

        buttonEqually.setOnClickListener{
            mainPresenter.onButtonEqually()
        }
    }

    /**
     *  Определение класса Листенер для кнопок с цифрами и точкой
     */
    private inner class NumberButtonsListener: View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.button_zero -> mainPresenter.onNumberButton(CalculatorNumberElements.ZERO)
                R.id.button_one -> mainPresenter.onNumberButton(CalculatorNumberElements.ONE)
                R.id.button_two -> mainPresenter.onNumberButton(CalculatorNumberElements.TWO)
                R.id.button_three -> mainPresenter.onNumberButton(CalculatorNumberElements.THREE)
                R.id.button_four -> mainPresenter.onNumberButton(CalculatorNumberElements.FOUR)
                R.id.button_five -> mainPresenter.onNumberButton(CalculatorNumberElements.FIVE)
                R.id.button_six -> mainPresenter.onNumberButton(CalculatorNumberElements.SIX)
                R.id.button_seven -> mainPresenter.onNumberButton(CalculatorNumberElements.SEVEN)
                R.id.button_eight -> mainPresenter.onNumberButton(CalculatorNumberElements.EIGHT)
                R.id.button_nine -> mainPresenter.onNumberButton(CalculatorNumberElements.NINE)
                R.id.button_point -> mainPresenter.onNumberButton(CalculatorNumberElements.POINT)
            }
        }

    }

    /**
     * Определение класса Листенер для кнопок с арифметическими операциями
     */
    private inner class ArithmeticButtonsListener: View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.button_plus -> mainPresenter.onArithmeticButton(CalculatorOperations.PLUS)
                R.id.button_minus -> mainPresenter.onArithmeticButton(CalculatorOperations.MINUS)
                R.id.button_multiplication -> mainPresenter.onArithmeticButton(CalculatorOperations.MULTIPLICATION)
                R.id.button_division -> mainPresenter.onArithmeticButton(CalculatorOperations.DIVISION)
            }
        }

    }

    /**
     * Отображает в textResult то, что приказал Презентер
     */
    override fun showInfo(string: String) {
        textResult.text = string
    }


}

