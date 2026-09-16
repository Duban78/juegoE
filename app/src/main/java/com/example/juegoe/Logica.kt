package com.example.juegoe

import kotlin.random.Random

class JuegoLogica {
    // Posición objetivo aleatoria en grados (de 0 a 360)
    var objetivoGrados: Float = 0f
    var tiempoRestante: Int = 45 // segundos
    var puntosActuales: Int = 500

    init {
        reiniciarPartida()
    }

    fun reiniciarPartida() {
        objetivoGrados = Random.nextFloat() * 360f
        tiempoRestante = 45
        puntosActuales = 500
    }

    // Calcula qué tan "caliente" o "frío" está el usuario según hacia dónde apunta (en grados)
    fun evaluarTemperatura(orientacionActual: Float): String {
        // Calculamos la diferencia absoluta entre el celular y el objetivo
        var diferencia = kotlin.math.abs(orientacionActual - objetivoGrados)
        if (diferencia > 180f) {
            diferencia = 360f - diferencia
        }

        return when {
            diferencia < 15f -> "¡Caliente!" // Muy cerca
            diferencia < 45f -> "Tibio"      // Cerca
            else -> "Muy frío"               // Lejos
        }
    }
}