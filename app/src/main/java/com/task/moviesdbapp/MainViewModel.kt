package com.task.moviesdbapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
data class MainState(val greeting: String = "Hello, Movies!")

@HiltViewModel
class MainViewModel @Inject constructor() :
    ViewModel(),
    ContainerHost<MainState, Nothing> {

    override val container = container<MainState, Nothing>(MainState())

    fun setGreeting(name: String) = intent {
        reduce { state.copy(greeting = "Hello, $name!") }
    }
}