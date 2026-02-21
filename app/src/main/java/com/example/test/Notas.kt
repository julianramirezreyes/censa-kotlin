package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Notas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas)

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextNota1 = findViewById<EditText>(R.id.editTextNota1)
        val editTextNota2 = findViewById<EditText>(R.id.editTextNota2)
        val editTextNota3 = findViewById<EditText>(R.id.editTextNota3)
        val botonCalcular = findViewById<Button>(R.id.botonCalcular)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

        botonCalcular.setOnClickListener {
            calcularPromedio()
        }
    }

    private fun calcularPromedio() {
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextNota1 = findViewById<EditText>(R.id.editTextNota1)
        val editTextNota2 = findViewById<EditText>(R.id.editTextNota2)
        val editTextNota3 = findViewById<EditText>(R.id.editTextNota3)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

        val nombre = editTextNombre.text.toString()
        val nota1Str = editTextNota1.text.toString()
        val nota2Str = editTextNota2.text.toString()
        val nota3Str = editTextNota3.text.toString()

        if (nombre.isEmpty() || nota1Str.isEmpty() || nota2Str.isEmpty() || nota3Str.isEmpty()) {
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

            val promedio = (nota1 * 0.33) + (nota2 * 0.33) + (nota3 * 0.34)

            val resultado = if (promedio >= 4) {
                "$nombre, ¡Felicidades! Pasaste la materia con un promedio de %.2f".format(promedio)
            } else {
                "$nombre, lo siento. No pasaste la materia. Tu promedio es %.2f".format(promedio)
            }

            textViewResultado.text = resultado
            textViewResultado.setTextColor(
                if (promedio >= 4) getColor(android.R.color.holo_green_dark) 
                else getColor(android.R.color.holo_red_dark)
            )

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor ingrese números válidos", Toast.LENGTH_SHORT).show()
        }
    }
}
