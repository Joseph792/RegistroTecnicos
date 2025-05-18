package edu.ucne.registrotecnicos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicosViewModel

@Composable
fun TecnicosNavHost(
navHostController: NavHostController,
tecnicoList: List<TecnicoEntity>,
viewModel: TecnicosViewModel,
navController: NavController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TecnicoList
    ) {
        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onEdit = { tecnicoId ->
                    navHostController.navigate(Screen.Tecnico(tecnicoId))
                },
                onDelete = { tecnico ->
                    viewModel.deleteTecnico(tecnico)
                }
            )
        }

        composable<Screen.Tecnico>{ backStack ->
            val tecnicoId  = backStack.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId,
                viewModel,
                navController
            ) {
            }
        }
    }
}