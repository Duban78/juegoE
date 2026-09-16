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

// --- PALETA DE COLORES EN MORADO CLARITO / LILA ---
val MoradoPrincipal = Color(0xFF7C4DFF)
val MoradoOscuro = Color(0xFF512DA8)
val MoradoFondoTarjeta = Color(0xFFF3E5F5)
val MoradoBorde = Color(0xFFD1C4E9)
val TextoOscuro = Color(0xFF311B92)

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
        // Título del juego
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 28.dp)
        ) {
            Text(
                text = "🔥 CALIENTE / FRÍO ❄️",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MoradoOscuro
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "¡ENCUÉNTRALO!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 2.sp
            )
        }

        // Tarjeta central de bienvenida / estadísticas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MoradoFondoTarjeta),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "👀 ¡Bienvenido, Explorador!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Encuentra al personaje escondido antes de que se acabe el tiempo.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MoradoBorde, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Mejor tiempo", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "01:45", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Mejor puntos", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "1200 pts", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
                    }
                }
            }
        }

        // Botones principales (Sugerencia: Solo Nueva Partida y Cómo Jugar)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = alIniciarJuego,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("▶  NUEVA PARTIDA", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = alAbrirComoJugar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MoradoOscuro)
            ) {
                Text("❓ CÓMO JUGAR", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
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
                Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "CÓMO JUGAR",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MoradoOscuro
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MoradoFondoTarjeta),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🎯 Objetivo del juego",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextoOscuro
                )
                Text(
                    text = "Al iniciar la partida, el juego selecciona aleatoriamente una posición objetivo (el personaje escondido). Debes encontrarlo antes de que se agote el tiempo.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                HorizontalDivider(color = MoradoBorde)

                Text(
                    text = "📱 Mueve tu teléfono",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextoOscuro
                )
                Text(
                    text = "Gira físicamente tu dispositivo. La app utiliza los sensores para saber en tiempo real hacia dónde estás apuntando.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                HorizontalDivider(color = MoradoBorde)

                Text(
                    text = "🌡️ Temperatura",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextoOscuro
                )
                Text(
                    text = "• Muy frío: Estás lejos del objetivo.\n" +
                            "• Tibio: Te estás aproximando.\n" +
                            "• ¡Caliente!: Estás prácticamente encima del objetivo.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        Button(
            onClick = alVolver,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("ENTENDIDO, VOLVER", fontSize = 15.sp, fontWeight = FontWeight.Bold)
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

    LaunchedEffect(key1 = tiempoRestante) {
        if (tiempoRestante > 0 && !seAcaboElTiempo) {
            delay(1000L)
            tiempoRestante--
        } else if (tiempoRestante == 0 && !seAcaboElTiempo) {
            seAcaboElTiempo = true
        }
    }

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
                Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MoradoFondoTarjeta),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = " ⏱️ 00:${tiempoRestante.toString().padStart(2, '0')} ",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextoOscuro
                )
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = " Puntos: ${juegoLogica.puntosActuales} ",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFF57F17)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (seAcaboElTiempo) {
                Text(
                    text = "¡TIEMPO AGOTADO!\nNo lograste encontrar al personaje.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Gira tu teléfono para encontrar al personaje",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

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
                        else -> MoradoFondoTarjeta
                    }
                ) {}
                Text(
                    text = if (seAcaboElTiempo) "FIN" else estadoTemperatura.uppercase(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = if (estadoTemperatura == "¡Caliente!") Color(0xFFC62828) else MoradoOscuro
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MoradoFondoTarjeta),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "💡 OBJETIVO EN: ${juegoLogica.objetivoGrados.toInt()}° | TU GIRO: ${orientacionActual.toInt()}°",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextoOscuro
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
                colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    if (seAcaboElTiempo) "VOLVER AL MENÚ" else "¡Simular que lo encontré!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
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
        Text(text = "🎉 ¡LO ENCONTRASTE!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MoradoFondoTarjeta),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "Tiempo total: $tiempoTexto", fontSize = 16.sp, color = TextoOscuro)
                Text(text = "Puntuación: $puntos pts", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
                Text(text = "Precisión: $precision%", fontSize = 16.sp, color = TextoOscuro)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = alVolverMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("VOLVER AL MENÚ", fontSize = 15.sp, fontWeight = FontWeight.Bold)
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