package com.strikalov.calculatorkotlin.model.interactors


import com.strikalov.calculatorkotlin.model.entites.Calculator
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.Callable

class CalculatorInteractorImpl(val calculator: Calculator,
                               val subscribeOnScheduler: Scheduler = Schedulers.computation(),
                               val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()) : CalculatorInteractor{

    companion object {
        /** Константа равная максимальному числу символов для числа*/
        private const val MAX_OUTPUT_NUMBER_LENGTH = 12
    }


    /** Сложение */
    override fun plus(a: String, b: String): Single<String> =
        Single.fromCallable(object : Callable<BigDecimal> {
            override fun call(): BigDecimal {
                return calculator.plus(BigDecimal(a), BigDecimal(b))
            }

        }).map(object : Function<BigDecimal, String>{
            override fun apply(t: BigDecimal): String {
                return formatBigDecimalToString(t)
            }

        }).subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)

    /** Вычитание */
    override fun minus(a: String, b: String): Single<String> =
        Single.fromCallable(object : Callable<BigDecimal> {
            override fun call(): BigDecimal {
                return calculator.minus(BigDecimal(a), BigDecimal(b))
            }

        }).map(object : Function<BigDecimal, String>{
            override fun apply(t: BigDecimal): String {
                return formatBigDecimalToString(t)
            }

        }).subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)

    /** Умножение */
    override fun multiplication(a: String, b: String): Single<String> =
        Single.fromCallable(object : Callable<BigDecimal> {
            override fun call(): BigDecimal {
                return calculator.multiplication(BigDecimal(a), BigDecimal(b))
            }

        }).map(object : Function<BigDecimal, String>{
            override fun apply(t: BigDecimal): String {
                return formatBigDecimalToString(t)
            }

        }).subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)

    /** Деление */
    override fun division(a: String, b: String): Single<String> =
        Single.fromCallable(object : Callable<BigDecimal> {
            override fun call(): BigDecimal {
                return calculator.division(BigDecimal(a), BigDecimal(b))
            }

        }).map(object : Function<BigDecimal, String>{
            override fun apply(t: BigDecimal): String {
                return formatBigDecimalToString(t)
            }

        }).subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)


    /**
     * Переводит число BigDecimal в String формат, и если оно слишком длинное
     * выводит его в сокращенной форме, чтобы помещалось на экран
     */
    private fun formatBigDecimalToString(bigDecimal: BigDecimal):String =

        if(bigDecimal.toPlainString().length <= MAX_OUTPUT_NUMBER_LENGTH){
            bigDecimal.toPlainString()
        }else{
            String.format("%.5e",bigDecimal).replace(',','.')
        }


}