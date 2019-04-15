package com.strikalov.calculatorkotlin.dagger

import com.strikalov.calculatorkotlin.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)

}