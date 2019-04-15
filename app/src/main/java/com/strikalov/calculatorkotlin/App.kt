package com.strikalov.calculatorkotlin

import android.app.Application
import com.strikalov.calculatorkotlin.dagger.AppComponent
import com.strikalov.calculatorkotlin.dagger.DaggerAppComponent

class App: Application() {

    companion object {

        private lateinit var appComponent: AppComponent

        fun getAppComponent() = appComponent

    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }


}