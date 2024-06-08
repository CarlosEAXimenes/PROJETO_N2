package com.example.projeto_n2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.MyAppTheme
class ListaOrdens : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardListScreen()
                }
        }
    }
}

@Composable
fun CardListScreen() {
    // Supondo que você tenha uma lista de dados para exibir
    val items = remember { sampleData() }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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
            .height(150.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = item.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class InfoItem(val title: String, val description: String)

// Função para gerar dados de exemplo
fun sampleData(): List<InfoItem> {
    return listOf(
        InfoItem("Título 1", "Descrição 1"),
        InfoItem("Título 2", "Descrição 2"),
        InfoItem("Título 3", "Descrição 3"),
        InfoItem("Título 4", "Descrição 4"),
        InfoItem("Título 5", "Descrição 5")
    )
}