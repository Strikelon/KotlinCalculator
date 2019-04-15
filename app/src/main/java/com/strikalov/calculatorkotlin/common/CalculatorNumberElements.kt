package com.strikalov.calculatorkotlin.common

/**
 *  Константы для передачи из активити в презентер,
 *  цифр или точек, которые нажал пользователь на калькуляторе
 */
enum class CalculatorNumberElements(val value: String){

    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"),
    FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),
    POINT(".")

}