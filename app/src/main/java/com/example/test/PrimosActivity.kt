package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrimosActivity : AppCompatActivity() {
    
    private lateinit var editTextCantidad: EditText
    private lateinit var botonGenerar: Button
    private lateinit var textViewResultado: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primos)
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        editTextCantidad = findViewById(R.id.editTextCantidad)
        botonGenerar = findViewById(R.id.botonGenerar)
        textViewResultado = findViewById(R.id.textViewResultado)
    }
    
    private fun setupListeners() {
        botonGenerar.setOnClickListener {
            generarPrimos()
        }
    }
    
    private fun generarPrimos() {
        val cantidadStr = editTextCantidad.text.toString().trim()
        
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese una cantidad", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val cantidad = cantidadStr.toInt()
            
            if (cantidad <= 0) {
                Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show()
                return
            }
            
            if (cantidad > 1000) {
                Toast.makeText(this, "Para mejor rendimiento, ingrese un número menor a 1000", Toast.LENGTH_SHORT).show()
                return
            }
            
            val primos = obtenerPrimerosPrimos(cantidad)
            
            val resultado = if (primos.isEmpty()) {
                "No se encontraron números primos"
            } else {
                val resultadoStr = primos.joinToString(", ")
                "Los primeros $cantidad números primos son:\n\n$resultadoStr"
            }
            
            textViewResultado.text = resultado
            
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor ingrese un número válido", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al generar números primos", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun obtenerPrimerosPrimos(cantidad: Int): List<Int> {
        val primos = mutableListOf<Int>()
        var numero = 2
        
        while (primos.size < cantidad) {
            if (esPrimo(numero)) {
                primos.add(numero)
            }
            numero++
        }
        
        return primos
    }
    
    private fun esPrimo(numero: Int): Boolean {
        if (numero <= 1) return false
        if (numero == 2) return true
        if (numero % 2 == 0) return false
        
        val limite = kotlin.math.sqrt(numero.toDouble()).toInt()
        for (i in 3..limite step 2) {
            if (numero % i == 0) return false
        }
        
        return true
    }
}
