package com.strikalov.calculatorkotlin.model.entites

import java.math.BigDecimal

class Calculator {

    /** Сложение */
    fun plus(a: BigDecimal, b: BigDecimal) = a.add(b).stripTrailingZeros()

    /** Вычитание */
    fun minus(a: BigDecimal, b: BigDecimal) = a.subtract(b).stripTrailingZeros()

    /** Умножение */
    fun multiplication(a: BigDecimal, b: BigDecimal) = a.multiply(b).stripTrailingZeros()

    /** Деление */
    fun division(a: BigDecimal, b: BigDecimal):BigDecimal{

        if(b.compareTo(BigDecimal.ZERO) == 0){
            throw ArithmeticException("Нельзя делить на 0")
        }else if(a.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO
        } else {
            return a.divide(b,11,BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
        }

    }

}