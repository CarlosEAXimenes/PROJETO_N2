package com.example.projeto_n2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_n2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCliente.setOnClickListener{
            val navTelaCli = Intent(this,Acesso_Cliente::class.java)
            startActivity(navTelaCli)
        }
        binding.btnEmpresa.setOnClickListener{
            val navTelaEmp = Intent(this,Login::class.java)
            startActivity(navTelaEmp)
        }
    }
}

