package com.example.test

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class ResultadoNota(
    val nombreEstudiante: String,
    val nombreMateria: String,
    val notaDefinitiva: Double,
    val gano: Boolean
)

class NotasActivity : AppCompatActivity() {
    
    private lateinit var editTextNombre: EditText
    private lateinit var editTextMateria: EditText
    private lateinit var editTextNota1: EditText
    private lateinit var editTextNota2: EditText
    private lateinit var editTextNota3: EditText
    private lateinit var botonCalcular: Button
    private lateinit var textViewResultado: TextView
    
    private var resultadoActual: ResultadoNota? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextMateria = findViewById(R.id.editTextMateria)
        editTextNota1 = findViewById(R.id.editTextNota1)
        editTextNota2 = findViewById(R.id.editTextNota2)
        editTextNota3 = findViewById(R.id.editTextNota3)
        botonCalcular = findViewById(R.id.botonCalcular)
        textViewResultado = findViewById(R.id.textViewResultado)
    }
    
    private fun setupListeners() {
        botonCalcular.setOnClickListener {
            calcularPromedio()
        }
    }
    
    private fun calcularPromedio() {
        val nombre = editTextNombre.text.toString().trim()
        val materia = editTextMateria.text.toString().trim()
        val nota1Str = editTextNota1.text.toString()
        val nota2Str = editTextNota2.text.toString()
        val nota3Str = editTextNota3.text.toString()
        
        if (nombre.isEmpty() || materia.isEmpty() || nota1Str.isEmpty() || 
            nota2Str.isEmpty() || nota3Str.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val nota1 = nota1Str.toDouble()
            val nota2 = nota2Str.toDouble()
            val nota3 = nota3Str.toDouble()
            
            if (nota1 < 0 || nota1 > 5 || nota2 < 0 || nota2 > 5 || nota3 < 0 || nota3 > 5) {
                Toast.makeText(this, "Las notas deben estar entre 0 y 5", Toast.LENGTH_SHORT).show()
                return
            }
            
            // Calcular promedio con las ponderaciones correctas
            val promedio = (nota1 * 0.33) + (nota2 * 0.33) + (nota3 * 0.34)
            
            // Almacenar el resultado
            resultadoActual = ResultadoNota(nombre, materia, promedio, promedio >= 4.0)
            
            // Mostrar resultado en popup
            mostrarResultadoPopup(nombre, materia, promedio)
            
            // También mostrar en el TextView
            val resultadoTexto = if (promedio >= 4.0) {
                "¡Felicidades $nombre! Ganaste la materia $materia con un promedio de %.2f".format(promedio)
            } else {
                "Lo siento $nombre. No ganaste la materia $materia. Tu promedio es %.2f".format(promedio)
            }
            
            textViewResultado.text = resultadoTexto
            textViewResultado.setTextColor(
                if (promedio >= 4.0) getColor(android.R.color.holo_green_dark) 
                else getColor(android.R.color.holo_red_dark)
            )
            
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor ingrese números válidos", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun mostrarResultadoPopup(nombre: String, materia: String, promedio: Double) {
        val mensaje = if (promedio >= 4.0) {
            """
            ¡FELICIDADES! 🎉
            
            Estudiante: $nombre
            Materia: $materia
            Nota Definitiva: %.2f
            
            ¡GANASTE LA MATERIA!
            """.trimIndent().format(promedio)
        } else {
            """
            LO SIENTO 😔
            
            Estudiante: $nombre
            Materia: $materia
            Nota Definitiva: %.2f
            
            NO GANASTE LA MATERIA
            """.trimIndent().format(promedio)
        }
        
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Resultado Final")
            .setMessage(mensaje)
            .setIcon(
                if (promedio >= 4.0) android.R.drawable.ic_menu_save
                else android.R.drawable.ic_dialog_alert
            )
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
        
        alertDialog.show()
        
        // Cambiar color del botón según el resultado
        val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        button.setTextColor(
            if (promedio >= 4.0) getColor(android.R.color.holo_green_dark)
            else getColor(android.R.color.holo_red_dark)
        )
    }
}
