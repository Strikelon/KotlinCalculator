package com.strikalov.calculatorkotlin.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy


interface MainView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showInfo(string: String)

}