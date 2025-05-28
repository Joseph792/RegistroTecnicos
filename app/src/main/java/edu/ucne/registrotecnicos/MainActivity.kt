package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.repository.PrioridadesRepository
import edu.ucne.registrotecnicos.data.repository.TecnicosRepository
import edu.ucne.registrotecnicos.data.repository.TicketsRepository
import edu.ucne.registrotecnicos.presentation.navigation.HomeNavHost
import edu.ucne.registrotecnicos.presentation.prioridad.PrioridadesViewModel
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicosViewModel
import edu.ucne.registrotecnicos.presentation.ticket.TicketsViewModel
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicoDb

    private lateinit var tecnicosRepository: TecnicosRepository
    private lateinit var tecnicosViewModel: TecnicosViewModel

    private lateinit var prioridadesRepository: PrioridadesRepository
    private lateinit var prioridadViewModel: PrioridadesViewModel

    private lateinit var ticketsRepository: TicketsRepository
    private lateinit var ticketsViewModel: TicketsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnico.db"
        ).fallbackToDestructiveMigration()
            .build()

        tecnicosRepository = TecnicosRepository(tecnicoDb.TecnicoDao())
        tecnicosViewModel = TecnicosViewModel(tecnicosRepository)

        prioridadesRepository = PrioridadesRepository(tecnicoDb.PrioridadDao())
        prioridadViewModel = PrioridadesViewModel(prioridadesRepository)

        ticketsRepository = TicketsRepository(tecnicoDb.TicketDao())
        ticketsViewModel = TicketsViewModel(
            ticketsRepository,
            tecnicosRepository,
            prioridadesRepository
        )

        setContent {
            RegistroTecnicosTheme {
                val nav = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        HomeNavHost(
                            navHostController = nav,
                            prioridadesViewModel = prioridadViewModel,
                            tecnicosViewModel = tecnicosViewModel,
                            ticketsViewModel = ticketsViewModel
                        )
                    }
                }
            }
        }
    }
}