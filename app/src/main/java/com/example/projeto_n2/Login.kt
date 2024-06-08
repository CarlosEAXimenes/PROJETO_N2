package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_n2.databinding.ActivityLoginBinding
import com.example.projeto_n2.databinding.ActivityMainBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblFazerCadastro.setOnClickListener{
            val navTelaCad = Intent(this,Cadastro::class.java)
            startActivity(navTelaCad)
        }
    }
}