package com.example.projeto_n2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_n2.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.projeto_n2.databinding.ActivityCadastroOrdemServicoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class CadastroOrdemServico : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroOrdemServicoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_ordem_servico)

        binding = ActivityCadastroOrdemServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener {
            val txtNome = binding.txtNome.text.toString()
            val txtEmail = binding.txtEmail.text.toString()
            val txtTelefone = binding.txtTelefone.text.toString()
            val txtDocumento = binding.txtDoc.text.toString()
            val txtEndereco = binding.txtEndereco.text.toString()

            val txtDescricao = binding.txtDescServico.text.toString()
            val txtData = binding.txtData.text.toString()
            val txtDataEntrega = binding.txtEntrega.text.toString()
            val txtValor = binding.txtValor.text.toString()


            var db = FirebaseFirestore.getInstance()

            var customerCollection = db.collection("customers")
            var orderServicesCollection = db.collection("order_services")

            var idCompany = FirebaseAuth.getInstance().currentUser?.uid.toString()

            val customer = hashMapOf(
                "name" to txtNome,
                "email" to txtEmail,
                "phone" to txtTelefone,
                "document" to txtDocumento,
                "address" to txtEndereco,
                "idCompany" to idCompany
            )

            customerCollection.add(customer).addOnSuccessListener {
                val orderService = hashMapOf(
                    "idCustomer" to it.id,
                    "description" to txtDescricao,
                    "dateOrder" to txtData,
                    "dateDelivery" to txtDataEntrega,
                    "price" to txtValor
                )
                orderServicesCollection.add(orderService).addOnSuccessListener {
                    Toast.makeText(this, "Ordem de serviço cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao cadastrar a ordem de serviço!", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao cadastrar o cliente!", Toast.LENGTH_SHORT).show()
            }

        }

    }

}