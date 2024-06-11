package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ListaOrdens : ComponentActivity() {
    private val items = mutableStateOf<List<InfoItem>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Superfície que utiliza a cor de fundo do tema
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = { TopAppBarComponent(this@ListaOrdens) }
                ) { paddingValues ->
                    CardListScreen(paddingValues, items.value)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarregar os dados quando a tela se tornar visível
        refreshData()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            items.value = fetchData()
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
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                // Ação para iniciar a atividade de cadastro de ordem de serviço
                val intent = Intent(activity, CadastroOrdemServico::class.java)
                activity.startActivity(intent)
                activity.finishActivity(2)
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
fun CardListScreen(paddingValues: PaddingValues, items: List<InfoItem>) {
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
            Text(text = "ID da ordem: ${item.orderId}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cliente: ${item.customerName}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Descrição: ${item.orderDescription}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Data de Entrega: ${item.orderDateDelivery}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Valor: ${item.orderPrice}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${item.orderStatus}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.weight(1f)) // Adiciona espaço flexível
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* ação para seta para a esquerda */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Seta para a esquerda"
                    )
                }
                IconButton(onClick = { /* ação para seta para a direita */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Seta para a direita"
                    )
                }
            }
        }
    }
}

data class InfoItem(
    val orderId: String,
    val customerName: String,
    val customerEmail: String,
    val customerPhone: String,
    val customerDocument: String,
    val customerAddress: String,
    val orderDescription: String,
    val orderDateOrder: String,
    val orderDateDelivery: String,
    val orderPrice: String,
    val orderStatus: String

)

suspend fun fetchData(): List<InfoItem> {
    val idCompany = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val customersCollection = FirebaseFirestore.getInstance().collection("customers")
        .whereEqualTo("idCompany", idCompany)
    val cardList = mutableListOf<InfoItem>()

    try {
        val customersSnapshot = customersCollection.get().await()
        for (customerDocument in customersSnapshot) {
            val idCustomer = customerDocument.id
            val customerData = customerDocument.data

            val orderServicesCollection =
                FirebaseFirestore.getInstance().collection("order_services")
                    .whereEqualTo("idCustomer", idCustomer)
            val orderServicesSnapshot = orderServicesCollection.get().await()
            for (orderDocument in orderServicesSnapshot) {
                val orderData = orderDocument.data

                val cardData = InfoItem(
                    customerName = customerData["name"].toString(),
                    customerEmail = customerData["email"].toString(),
                    customerPhone = customerData["phone"].toString(),
                    customerDocument = customerData["document"].toString(),
                    customerAddress = customerData["address"].toString(),
                    orderDescription = orderData["description"].toString(),
                    orderDateOrder = orderData["dateOrder"].toString(),
                    orderDateDelivery = orderData["dateDelivery"].toString(),
                    orderPrice = orderData["price"].toString(),
                    orderStatus = "A fazer" /*orderData["status"].toString()*/,
                    orderId = orderDocument.id
                )

                cardList.add(cardData)
            }
        }
    } catch (e: Exception) {
        Log.w("Firestore", "Error getting documents: ", e)
    }

    return cardList
}

