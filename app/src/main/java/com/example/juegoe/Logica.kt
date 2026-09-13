package com.example.juegoe
import kotlin.math.*

class Logica {
    var anguloObjetivo: Float = 0f
    val tiempoTotalSegundos: Int = 60

    fun iniciarNuevaPartida() {
        anguloObjetivo = (0..359).random().toFloat()
    }

    fun obtenerDiferenciaAngulo(anguloActual: Float): Float {
        var diferencia = abs(anguloActual - anguloObjetivo)
        if (diferencia > 180) {
            diferencia = 360 - diferencia
        }
        return diferencia
    }

    fun obtenerTemperatura(diferencia: Float): String {
        return when {
            diferencia <= 15 -> "¡CALIENTE!"
            diferencia <= 45 -> "Tibio"
            else -> "Muy Frío"
        }
    }

    fun calcularPrecision(diferencia: Float): Int {
        val precision = ((180 - diferencia) / 180) * 100
        return precision.toInt()
    }

    fun calcularPuntaje(tiempoRestanteSegundos: Int, precision: Int): Int {
        val bonificacionTiempo = tiempoRestanteSegundos * 10
        val bonificacionPrecision = precision * 5
        return bonificacionTiempo + bonificacionPrecision
    }
}