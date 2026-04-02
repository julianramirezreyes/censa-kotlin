package com.example.test.ui

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.databinding.ActivityApiTesterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ApiTesterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiTesterBinding
    private var lastResponseBody: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityApiTesterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.executeButton.setOnClickListener {
            executeRequest()
        }

        binding.toggleBodyButton.setOnClickListener {
            toggleBodyVisibility()
        }
    }

    private fun executeRequest() {
        val urlString = binding.urlEditText.text.toString().trim()

        // Validación: URL vacío
        if (urlString.isEmpty()) {
            showError("Por favor ingresa una URL")
            return
        }

        // Validación: URL válida
        if (!isValidUrl(urlString)) {
            showError("URL inválida. Ejemplo: https://api.ejemplo.com/endpoint")
            return
        }

        // Asegurar que la URL tenga protocolo
        val finalUrl = if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
            urlString
        } else {
            "https://$urlString"
        }

        // Ocultar resultados anteriores
        hideResults()
        showLoading(true)

        // Ejecutar petición en segundo plano
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    performGetRequest(finalUrl)
                }
                showLoading(false)
                showResult(response)
            } catch (e: Exception) {
                showLoading(false)
                showError("Error de conexión: ${e.message}")
            }
        }
    }

    private fun isValidUrl(url: String): Boolean {
        // Verificar si tiene formato válido de URL o dominio básico
        val urlWithProtocol = if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
        return Patterns.WEB_URL.matcher(urlWithProtocol).matches() ||
               url.matches(Regex("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
    }

    private fun performGetRequest(urlString: String): ApiResponse {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.setRequestProperty("Accept", "application/json")

            val statusCode = connection.responseCode
            val statusMessage = connection.responseMessage
            val body = try {
                connection.inputStream.bufferedReader().readText()
            } catch (e: Exception) {
                try {
                    connection.errorStream.bufferedReader().readText()
                } catch (e2: Exception) {
                    null
                }
            }

            lastResponseBody = body

            connection.disconnect()

            ApiResponse(
                statusCode = statusCode,
                statusMessage = statusMessage,
                body = body
            )
        } catch (e: Exception) {
            throw e
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressIndicator.visibility = if (show) View.VISIBLE else View.GONE
        binding.executeButton.isEnabled = !show
    }

    private fun hideResults() {
        binding.resultCard.visibility = View.GONE
        binding.errorCard.visibility = View.GONE
        binding.bodyContainer.visibility = View.GONE
        binding.toggleBodyButton.text = "▼ Mostrar Body"
    }

    private fun showResult(response: ApiResponse) {
        binding.resultCard.visibility = View.VISIBLE
        binding.errorCard.visibility = View.GONE

        binding.statusCodeTextView.text = response.statusCode.toString()
        binding.statusMessageTextView.text = response.statusMessage
        binding.statusDescriptionTextView.text = getStatusDescription(response.statusCode)

        // Color del código según tipo de respuesta
        val color = when {
            response.statusCode in 200..299 -> android.R.color.holo_green_dark
            response.statusCode in 300..399 -> android.R.color.holo_orange_dark
            response.statusCode in 400..499 -> android.R.color.holo_red_dark
            response.statusCode >= 500 -> android.R.color.holo_red_dark
            else -> android.R.color.black
        }
        binding.statusCodeTextView.setTextColor(getColor(color))

        // Resetear botón de body
        binding.bodyContainer.visibility = View.GONE
        binding.toggleBodyButton.text = "▼ Mostrar Body"

        // Mostrar body si existe
        if (!response.body.isNullOrEmpty()) {
            binding.bodyTextView.text = formatJson(response.body)
            binding.toggleBodyButton.visibility = View.VISIBLE
        } else {
            binding.toggleBodyButton.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.errorCard.visibility = View.VISIBLE
        binding.resultCard.visibility = View.GONE
        binding.errorTextView.text = message
    }

    private fun toggleBodyVisibility() {
        val isVisible = binding.bodyContainer.visibility == View.VISIBLE
        binding.bodyContainer.visibility = if (isVisible) View.GONE else View.VISIBLE
        binding.toggleBodyButton.text = if (isVisible) "▼ Mostrar Body" else "▲ Ocultar Body"
    }

    private fun getStatusDescription(code: Int): String {
        return when (code) {
            100 -> "Continuar - El servidor recibió los headers, el cliente debe proceder"
            101 -> "Cambio de protocolos - El servidor cambia el protocolo"
            200 -> "OK - La solicitud fue exitosa"
            201 -> "Creado - El recurso fue creado exitosamente"
            202 -> "Aceptado - La solicitud fue aceptada para procesamiento"
            204 -> "Sin contenido - La solicitud fue exitosa sin retornar contenido"
            301 -> "Redirección permanente - El recurso se movió a otra ubicación"
            302 -> "Redirección temporal - Redirige temporalmente a otra URL"
            304 -> "No modificado - El recurso no ha cambiado desde la última solicitud"
            400 -> "Solicitud incorrecta - La sintaxis de la solicitud es inválida"
            401 -> "No autorizado - Se requiere autenticación"
            403 -> "Prohibido - El servidor rechaza la solicitud"
            404 -> "No encontrado - El recurso no existe en el servidor"
            405 -> "Método no permitido - El método HTTP no está permitido"
            408 -> "Tiempo de espera agotado - El cliente no envió la solicitud a tiempo"
            409 -> "Conflicto - La solicitud conflije con el estado del servidor"
            429 -> "Demasiadas solicitudes - Rate limit excedido"
            500 -> "Error interno del servidor - El servidor encontró un error"
            501 -> "No implementado - El servidor no soporta esta funcionalidad"
            502 -> "Bad Gateway - El servidor actuando como gateway recibió respuesta inválida"
            503 -> "Servicio no disponible - El servidor está en mantenimiento"
            504 -> "Gateway Timeout - El servidor como gateway no recibió respuesta a tiempo"
            else -> "Código de respuesta HTTP: $code"
        }
    }

    private fun formatJson(json: String?): String {
        if (json.isNullOrEmpty()) return "(Sin contenido)"
        return try {
            // Intentar formatear como JSON si es posible
            val trimmed = json.trim()
            if (trimmed.startsWith("{") || trimmed.startsWith("[")) {
                // Simple JSON formatter
                var formatted = ""
                var indent = 0
                var inString = false
                var escape = false

                for (char in trimmed) {
                    when {
                        escape -> {
                            formatted += char
                            escape = false
                        }
                        char == '\\' -> {
                            formatted += char
                            escape = true
                        }
                        inString -> {
                            formatted += char
                            if (char == '"') inString = false
                        }
                        char == '"' -> {
                            formatted += char
                            inString = true
                        }
                        char == '{' || char == '[' -> {
                            formatted += char
                            formatted += "\n${"  ".repeat(++indent)}"
                        }
                        char == '}' || char == ']' -> {
                            formatted += "\n${"  ".repeat(--indent)}$char"
                        }
                        char == ',' -> {
                            formatted += char
                            formatted += "\n${"  ".repeat(indent)}"
                        }
                        char == ':' -> {
                            formatted += ": "
                        }
                        char.isWhitespace() && !inString -> {
                            // Skip whitespace outside strings
                        }
                        else -> {
                            formatted += char
                        }
                    }
                }
                formatted
            } else {
                json
            }
        } catch (e: Exception) {
            json
        }
    }
}

data class ApiResponse(
    val statusCode: Int,
    val statusMessage: String,
    val body: String?
)
