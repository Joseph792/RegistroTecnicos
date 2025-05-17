package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import kotlinx.coroutines.launch

@Composable
fun TecnicoScreen(
    tecnicoId: Int?,
    saveTecnico: (TecnicoEntity) -> Unit
) {
    var nombre: String by remember { mutableStateOf("") }
    var sueldo: String by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf("") }
    var editando by remember { mutableStateOf<TecnicoEntity?>(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("Registro de tecnicos")

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        )

                    OutlinedTextField(
                        value = sueldo,
                        onValueChange = { sueldo = it },
                        label = { Text("Sueldo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    errorMessage?.let {
                        Text(text = it,
                            color = Color.Red
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text("Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        OutlinedButton(
                            onClick = {
                                if (nombre.isBlank()) {
                                    errorMessage = "Nombre vacio"
                                    return@OutlinedButton
                                }
                                if (sueldo.isBlank()) {
                                    errorMessage = "Sueldo vacio"
                                    return@OutlinedButton
                                }
                                scope.launch {
                                    saveTecnico(
                                        TecnicoEntity(
                                            nombre = nombre,
                                            sueldo = sueldo
                                        )
                                    )
                                    nombre = ""
                                    sueldo = ""
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}
