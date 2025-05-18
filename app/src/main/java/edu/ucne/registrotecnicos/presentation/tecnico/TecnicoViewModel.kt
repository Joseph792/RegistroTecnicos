package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.repository.TecnicosRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TecnicosViewModel(
    private val tecnicosRepository: TecnicosRepository
): ViewModel() {
    fun saveTecnico(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            tecnicosRepository.save(tecnico)
        }
    }

    suspend fun findTecnico(id: Int): TecnicoEntity? {
        return tecnicosRepository.find(id)
    }

    fun deleteTecnico(tecnico: TecnicoEntity) {
        viewModelScope.launch {
            tecnicosRepository.delete(tecnico)
        }
    }

    val tecnicos: StateFlow<List<TecnicoEntity>> = tecnicosRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}