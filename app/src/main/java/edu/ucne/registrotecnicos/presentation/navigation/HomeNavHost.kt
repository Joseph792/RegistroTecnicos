package edu.ucne.registrotecnicos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrotecnicos.presentation.Home.HomeScreen
import edu.ucne.registrotecnicos.presentation.prioridad.PrioridadListScreen
import edu.ucne.registrotecnicos.presentation.prioridad.PrioridadScreen
import edu.ucne.registrotecnicos.presentation.prioridad.PrioridadesViewModel
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicosViewModel
import edu.ucne.registrotecnicos.presentation.ticket.TicketListScreen
import edu.ucne.registrotecnicos.presentation.ticket.TicketScreen
import edu.ucne.registrotecnicos.presentation.ticket.TicketsViewModel

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    prioridadesViewModel: PrioridadesViewModel,
    tecnicosViewModel: TecnicosViewModel,
    ticketsViewModel: TicketsViewModel
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home
    ) {
        //inicio
        composable <Screen.Home> {
            HomeScreen(navController = navHostController)
        }

        //pantalla lista de prioridades
        composable <Screen.PrioridadList> {
            val prioridades by prioridadesViewModel.prioridades.collectAsState()

            PrioridadListScreen(
                prioridadList = prioridades,
                onEdit = { id ->
                    navHostController.navigate(Screen.Prioridad(id ?: 0))
                },
                onDelete = { prioridad ->
                    prioridadesViewModel.deletePrioridad(prioridad)
                }
            )

        }

        //pantalla formulario de prioridades
        composable <Screen.Prioridad> { backStack ->
            val prioridadId = backStack.toRoute<Screen.Prioridad>().prioridadId
            PrioridadScreen(
                prioridadId = prioridadId,
                viewModelPrioridad = prioridadesViewModel,
                navController = navHostController,
                function = { navHostController.popBackStack()}
            )
        }

        //pantalla lista de tecnicos
        composable<Screen.TecnicoList>{
            val tecnicos by tecnicosViewModel.tecnicos.collectAsState()

            TecnicoListScreen(
                tecnicoList = tecnicos,
                onEdit = { id ->
                    navHostController.navigate(Screen.Tecnico(id ?: 0))
                },
                onDelete = { tecnico ->
                    tecnicosViewModel.deleteTecnico(tecnico)
                }
            )
        }

        //pantalla formulario de tecnico
        composable <Screen.Tecnico>{ backStack ->
            val tecnicoId = backStack.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId = tecnicoId,
                viewModel = tecnicosViewModel,
                navController = navHostController,
                function = {navHostController.popBackStack()}
            )
        }

        //pantalla lista de tickets
        composable <Screen.TicketList>{
            val tickets by ticketsViewModel.ticketsS.collectAsState()

            TicketListScreen(
                ticketList = tickets,
                onEdit = { id ->
                    navHostController.navigate(Screen.Ticket(id ?: 0))
                },
                onDelete = {ticket ->
                    ticketsViewModel.deleteTicket(ticket)
                }
            )
        }

        //pantalla formulario tickets
        composable <Screen.Ticket>{ backStack ->
            val ticketId = backStack.toRoute<Screen.Ticket>().ticketId
            TicketScreen(
                ticketId = ticketId,
                viewModel = ticketsViewModel,
                navController = navHostController,
                function = { navHostController.popBackStack()}
            )
        }
    }
}