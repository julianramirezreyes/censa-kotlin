package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var userName: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupWindowInsets()
        loadUserName()
        setupUI()
        setupListeners()
    }
    
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun loadUserName() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        userName = prefs.getString("user_name", "Usuario") ?: "Usuario"
    }
    
    private fun setupUI() {
        binding.welcomeTextView.text = getString(R.string.main_welcome, userName)
    }
    
    private fun setupListeners() {
        binding.cardNotas.setOnClickListener {
            startActivity(Intent(this, NotasActivity::class.java))
        }
        
        binding.cardPrimos.setOnClickListener {
            startActivity(Intent(this, PrimosActivity::class.java))
        }
        
        binding.cardDivisas.setOnClickListener {
            startActivity(Intent(this, DivisasActivity::class.java))
        }
    }
}
