package com.example.test

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DivisasActivity : AppCompatActivity() {
    
    private lateinit var editTextCantidad: EditText
    private lateinit var spinnerMonedaOrigen: Spinner
    private lateinit var spinnerMonedaDestino: Spinner
    private lateinit var botonConvertir: Button
    private lateinit var textViewResultado: TextView
    
    // Tasas de cambio ficticias (base: USD)
    private val tasasCambio = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.73,
        "JPY" to 110.0,
        "COP" to 4000.0,
        "MXN" to 20.0,
        "ARS" to 98.0,
        "CAD" to 1.25,
        "AUD" to 1.35,
        "CHF" to 0.92
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divisas)
        
        initViews()
        setupSpinners()
        setupListeners()
    }
    
    private fun initViews() {
        editTextCantidad = findViewById(R.id.editTextCantidad)
        spinnerMonedaOrigen = findViewById(R.id.spinnerMonedaOrigen)
        spinnerMonedaDestino = findViewById(R.id.spinnerMonedaDestino)
        botonConvertir = findViewById(R.id.botonConvertir)
        textViewResultado = findViewById(R.id.textViewResultado)
    }
    
    private fun setupSpinners() {
        val monedas = tasasCambio.keys.toList()
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        spinnerMonedaOrigen.adapter = adapter
        spinnerMonedaDestino.adapter = adapter
        
        // Seleccionar valores por defecto
        spinnerMonedaOrigen.setSelection(monedas.indexOf("USD"))
        spinnerMonedaDestino.setSelection(monedas.indexOf("EUR"))
    }
    
    private fun setupListeners() {
        botonConvertir.setOnClickListener {
            convertirDivisa()
        }
    }
    
    private fun convertirDivisa() {
        val cantidadStr = editTextCantidad.text.toString().trim()
        
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese una cantidad", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val cantidad = cantidadStr.toDouble()
            
            if (cantidad <= 0) {
                Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show()
                return
            }
            
            val monedaOrigen = spinnerMonedaOrigen.selectedItem.toString()
            val monedaDestino = spinnerMonedaDestino.selectedItem.toString()
            
            if (monedaOrigen == monedaDestino) {
                Toast.makeText(this, "Seleccione monedas diferentes", Toast.LENGTH_SHORT).show()
                return
            }
            
            // Convertir a USD primero (base)
            val cantidadEnUSD = if (monedaOrigen == "USD") {
                cantidad
            } else {
                cantidad / tasasCambio[monedaOrigen]!!
            }
            
            // Luego convertir de USD a la moneda destino
            val cantidadConvertida = if (monedaDestino == "USD") {
                cantidadEnUSD
            } else {
                cantidadEnUSD * tasasCambio[monedaDestino]!!
            }
            
            val resultado = String.format("%.2f", cantidadConvertida)
            
            val mensaje = """
                $cantidad $monedaOrigen = $resultado $monedaDestino
                
                Tasa de cambio utilizada:
                1 $monedaOrigen = ${String.format("%.4f", tasasCambio[monedaDestino]!! / tasasCambio[monedaOrigen]!!)} $monedaDestino
            """.trimIndent()
            
            textViewResultado.text = mensaje
            textViewResultado.visibility = View.VISIBLE
            
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor ingrese un número válido", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al convertir la divisa", Toast.LENGTH_SHORT).show()
        }
    }
}
