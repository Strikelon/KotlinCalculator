package com.strikalov.calculatorkotlin.model.interactors

import io.reactivex.Single

interface CalculatorInteractor {

    fun plus(a: String, b: String): Single<String>

    fun minus(a: String, b: String): Single<String>

    fun multiplication(a: String, b: String): Single<String>

    fun division(a: String, b: String): Single<String>

}