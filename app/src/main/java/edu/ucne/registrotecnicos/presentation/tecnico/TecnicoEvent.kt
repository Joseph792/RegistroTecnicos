package edu.ucne.registrotecnicos.presentation.tecnico

sealed interface TecnicoEvent{
    data class TecnicoChange(val tecnicoId: Int): TecnicoEvent
    data class NombreChange(val nombre: String): TecnicoEvent
    data class SueldoChange(val sueldo: String): TecnicoEvent
    data object Save: TecnicoEvent
    data object Delete: TecnicoEvent
    data object New: TecnicoEvent
}