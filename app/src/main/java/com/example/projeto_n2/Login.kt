package com.example.projeto_n2

import ListaOrdem
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_n2.databinding.ActivityLoginBinding
import com.example.projeto_n2.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblFazerCadastro.setOnClickListener {
            val navTelaCad = Intent(this, Cadastro::class.java)
            startActivity(navTelaCad)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val navTelaPrincipal = Intent(this, ListaOrdem::class.java)
                            startActivity(navTelaPrincipal)
                        } else {
                            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}