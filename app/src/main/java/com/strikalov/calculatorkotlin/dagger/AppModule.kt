package com.strikalov.calculatorkotlin.dagger

import com.strikalov.calculatorkotlin.model.entites.Calculator
import com.strikalov.calculatorkotlin.model.interactors.CalculatorInteractor
import com.strikalov.calculatorkotlin.model.interactors.CalculatorInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCalculator() = Calculator()

    @Provides
    @Singleton
    fun provideCalculatorInteractor(calculator: Calculator): CalculatorInteractor
            = CalculatorInteractorImpl(calculator)

}