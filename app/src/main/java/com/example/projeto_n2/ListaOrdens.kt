package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
class ListaOrdens : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // Superfície que utiliza a cor de fundo do tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { TopAppBarComponent(this) }
                    ) { paddingValues ->
                        CardListScreen(paddingValues)
                    }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(activity: ListaOrdens) { // Adicione uma referência para a atividade
    TopAppBar(
        title = { Text("My App") },
        navigationIcon = {
            IconButton(onClick = { /* ação de navegação */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                // Ação para iniciar a atividade de cadastro de ordem de serviço
                val intent = Intent(activity, CadastroOrdemServico::class.java)
                activity.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar_ordem"
                )
            }
        }
    )
}

@Composable
fun CardListScreen(paddingValues: PaddingValues) {
    // Supondo que você tenha uma lista de dados para exibir
    val items = remember { sampleData() }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            InfoCard(item)
        }
    }
}

@Composable
fun InfoCard(item: InfoItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), // Aumentei a altura para acomodar melhor as informações
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD) // Azul claro
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "ID da Ordem: ${item.title}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cliente: ${item.nomeCliente}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Descrição: ${item.descricao}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Data de Entrega: ${item.dataEntrega}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Valor: ${item.valor}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class InfoItem(val title: String, val nomeCliente: String, val descricao: String, val dataEntrega: String, val valor: String)

// Função para gerar dados de exemplo
fun sampleData(): List<InfoItem> {
    return listOf(
        InfoItem("123", "Cliente A", "Descrição A", "10/06/2024", "$100.00"),
        InfoItem("456", "Cliente B", "Descrição B", "15/06/2024", "$150.00"),
        InfoItem("789", "Cliente C", "Descrição C", "20/06/2024", "$200.00"),
        InfoItem("101", "Cliente D", "Descrição D", "25/06/2024", "$250.00"),
        InfoItem("112", "Cliente E", "Descrição E", "30/06/2024", "$300.00")
    )
}