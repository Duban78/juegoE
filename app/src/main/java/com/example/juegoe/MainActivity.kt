package com.example.juegoe

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.juegoe.ui.theme.JuegoETheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuegoETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavegacionApp()
                }
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    var pantallaActual by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf("inicio") }
    var tiempoFinalSegundos by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(0) }
    var puntosFinales by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(0) }
    var precisionFinal by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(0) }

    when (pantallaActual) {
        "inicio" -> PantallaInicio(
            alIniciarJuego = { pantallaActual = "juego" },
            alAbrirComoJugar = { pantallaActual = "como_jugar" }
        )
        "como_jugar" -> PantallaComoJugar(
            alVolver = { pantallaActual = "inicio" }
        )
        "juego" -> PantallaJuego(
            alVolver = { pantallaActual = "inicio" },
            alGanar = { tiempoUsado, puntos, precision ->
                tiempoFinalSegundos = tiempoUsado
                puntosFinales = puntos
                precisionFinal = precision
                pantallaActual = "resultado"
            }
        )
        "resultado" -> PantallaResultado(
            tiempoSegundos = tiempoFinalSegundos,
            puntos = puntosFinales,
            precision = precisionFinal,
            alVolverMenu = { pantallaActual = "inicio" }
        )
    }
}

@Composable
fun PantallaInicio(
    alIniciarJuego: () -> Unit,
    alAbrirComoJugar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                text = "CALIENTE / FRÍO",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF388E3C)
            )
            Text(
                text = "¡ENCUÉNTRALO!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Bienvenido, Explorador!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Encuentra al personaje escondido antes de que se acabe el tiempo",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Mejor tiempo: 01:45", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "Mejor puntuación: 1200 pts", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = alIniciarJuego,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("NUEVA PARTIDA", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("RANKING")
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CONFIGURACIÓN")
            }

            OutlinedButton(
                onClick = alAbrirComoJugar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CÓMO JUGAR")
            }
        }
    }
}

@Composable
fun PantallaComoJugar(alVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = alVolver) {
                Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "CÓMO JUGAR",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🎯 Objetivo del juego",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "Al iniciar la partida, el juego selecciona aleatoriamente una posición objetivo (el personaje escondido) [cite: 1]. Debes encontrarlo antes de que se agote el tiempo del temporizador [cite: 1].",
                    fontSize = 14.sp
                )

                HorizontalDivider()

                Text(
                    text = "📱 Cómo mover el teléfono",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "Gira y mueve físicamente tu teléfono [cite: 1]. La aplicación utiliza los sensores del dispositivo para calcular tu orientación en tiempo real [cite: 1].",
                    fontSize = 14.sp
                )

                HorizontalDivider()

                Text(
                    text = "🌡️ Estados de Temperatura",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "• Muy frío: Estás lejos de la dirección del objetivo [cite: 1].\n" +
                            "• Tibio: Te estás aproximando a la dirección correcta [cite: 1].\n" +
                            "• ¡Caliente!: Te encuentras muy cerca de la posición objetivo [cite: 1].",
                    fontSize = 14.sp
                )

                HorizontalDivider()

                Text(
                    text = "⭐ Puntuación y Tiempo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "Entre más rápido encuentres al personaje y con mayor precisión angular lo hagas, obtendrás una mayor puntuación [cite: 1].",
                    fontSize = 14.sp
                )
            }
        }

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("ENTENDIDO, VOLVER AL MENÚ")
        }
    }
}

@Composable
fun PantallaJuego(
    alVolver: () -> Unit,
    alGanar: (Int, Int, Int) -> Unit
) {
    val context = LocalContext.current
    val juegoLogica = remember { JuegoLogica() }

    var orientacionActual by remember { mutableStateOf(0f) }
    var estadoTemperatura by remember { mutableStateOf("Muy frío") }
    var tiempoRestante by remember { mutableStateOf(45) }
    var seAcaboElTiempo by remember { mutableStateOf(false) }

    // --- TEMPORIZADOR EN TIEMPO REAL ---
    LaunchedEffect(key1 = tiempoRestante) {
        if (tiempoRestante > 0 && !seAcaboElTiempo) {
            delay(1000L)
            tiempoRestante--
        } else if (tiempoRestante == 0 && !seAcaboElTiempo) {
            seAcaboElTiempo = true
        }
    }

    // Conexión con los sensores físicos del dispositivo
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    val x = event.values[0]
                    orientacionActual = (Math.abs(x * 35f)) % 360f
                    estadoTemperatura = juegoLogica.evaluarTemperatura(orientacionActual)
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = alVolver) {
                Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                Text(
                    text = " ⏱️ 00:${tiempoRestante.toString().padStart(2, '0')} ",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))) {
                Text(
                    text = " Puntos: ${juegoLogica.puntosActuales} ",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (seAcaboElTiempo) {
                Text(
                    text = "¡TIEMPO AGOTADO!\nNo lograste encontrar al personaje.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Gira y mueve tu teléfono para encontrar al personaje escondido.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(110.dp),
                    color = when(estadoTemperatura) {
                        "¡Caliente!" -> Color(0xFFFFCDD2)
                        "Tibio" -> Color(0xFFFFF9C4)
                        else -> Color(0xFFE8F5E9)
                    }
                ) {}
                Text(
                    text = if (seAcaboElTiempo) "FIN" else estadoTemperatura.uppercase(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF2E7D32)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCC80))
            ) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "💡 OBJETIVO EN: ${juegoLogica.objetivoGrados.toInt()}° | TU GIRO: ${orientacionActual.toInt()}°",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (seAcaboElTiempo) {
                        alVolver()
                    } else {
                        val tiempoUsado = 45 - tiempoRestante
                        val diferenciaGrados = kotlin.math.abs(orientacionActual - juegoLogica.objetivoGrados).let { if (it > 180f) 360f - it else it }
                        val precisionCalculada = (100f - (diferenciaGrados / 180f * 100f)).coerceIn(0f, 100f).toInt()
                        val puntosCalculados = (precisionCalculada * 10) + (tiempoUsado * 2)
                        alGanar(tiempoUsado, puntosCalculados, precisionCalculada)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (seAcaboElTiempo) "VOLVER AL MENÚ" else "¡Simular que lo encontré!")
            }
        }
    }
}

@Composable
fun PantallaResultado(
    tiempoSegundos: Int,
    puntos: Int,
    precision: Int,
    alVolverMenu: () -> Unit
) {
    val minutos = tiempoSegundos / 60
    val segundos = tiempoSegundos % 60
    val tiempoTexto = String.format("%02d:%02d", minutos, segundos)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "¡LO ENCONTRASTE!", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tiempo total: $tiempoTexto", fontSize = 16.sp)
        Text(text = "Puntuación: $puntos pts", fontSize = 16.sp)
        Text(text = "Precisión: $precision%", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = alVolverMenu,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("VOLVER AL MENÚ")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevisualizacionPantallaInicio() {
    JuegoETheme {
        PantallaInicio(alIniciarJuego = {}, alAbrirComoJugar = {})
    }
}