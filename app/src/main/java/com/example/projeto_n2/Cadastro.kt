package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeto_n2.databinding.ActivityCadastroBinding
import com.example.projeto_n2.databinding.ActivityLoginBinding

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
    }
}