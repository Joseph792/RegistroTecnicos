package edu.ucne.registrotecnicos.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TecnicoList : Screen()
    @Serializable
    data class Tenico(val tecnicoId: Int) : Screen()
}