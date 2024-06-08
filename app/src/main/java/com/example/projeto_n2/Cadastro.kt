package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeto_n2.databinding.ActivityCadastroBinding
import com.example.projeto_n2.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblFazerLogin.setOnClickListener{
            val navTelaCad = Intent(this,Login::class.java)
            startActivity(navTelaCad)
        }

        binding.btnCadastrar.setOnClickListener{

            val txtEmail = binding.txtEmail.text.toString()
            val txtSenha = binding.txtSenha.text.toString()
            val txtConfirmarSenha = binding.txtConfirmarSenha.text.toString()

            if(txtSenha == txtConfirmarSenha && txtSenha.isNotEmpty() && txtEmail.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    txtEmail,txtSenha
                )
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            val companyCollection = FirebaseFirestore.getInstance().collection("company")
                            val company = hashMapOf(
                                "email" to txtEmail,
                                "idCompany" to it.result.user?.uid.toString()
                            )
                            companyCollection.add(company)

                            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()

                            val navTelaCad = Intent(this,Login::class.java)
                            startActivity(navTelaCad)
                        }
            }

            }else {
                Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}