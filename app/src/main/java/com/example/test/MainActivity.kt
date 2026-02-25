package com.example.test

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupWindowInsets()
        setupListeners()
        setupTextWatcher()
    }
    
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun setupListeners() {
        binding.button.setOnClickListener {
            procesarNombre()
        }
    }
    
    private fun setupTextWatcher() {
        binding.nombreText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.resultTextView.visibility = View.GONE
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun procesarNombre() {
        val nombre = binding.nombreText.text.toString().trim()
        
        if (nombre.isEmpty()) {
            binding.nombreTextInputLayout.error = getString(R.string.error_empty_name)
            binding.nombreText.requestFocus()
            return
        }
        
        binding.nombreTextInputLayout.error = null
        
        val mensaje = getString(R.string.welcome_message, nombre)
        binding.resultTextView.text = mensaje
        binding.resultTextView.visibility = View.VISIBLE
        
        // Guardar el nombre en SharedPreferences para usarlo en otras pantallas
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit().putString("user_name", nombre).apply()
        
        limpiarCampo()
        
        // Navegar al menú principal después de 2 segundos
        binding.resultTextView.postDelayed({
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }, 2000)
    }
    
    private fun limpiarCampo() {
        binding.nombreText.text?.clear()
    }
}