package com.example.juegoe

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
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

// --- PALETA DE COLORES ---
val MoradoPrincipal = Color(0xFF7C4DFF)
val MoradoOscuro = Color(0xFF512DA8)
val MoradoGradienteInicio = Color(0xFF8E24AA)
val MoradoAcentoLila = Color(0xFFBA68C8)
val MoradoFondoTarjeta = Color(0xFFF8F0FC)
val MoradoBorde = Color(0xFFE1BEE7)
val TextoOscuro = Color(0xFF212121)

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .offset(x = (-60).dp, y = (-40).dp)
                .clip(CircleShape)
                .background(color = MoradoAcentoLila.copy(alpha = 0.15f))
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 60.dp, y = 100.dp)
                .clip(CircleShape)
                .background(color = MoradoPrincipal.copy(alpha = 0.12f))
        )

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
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MoradoFondoTarjeta,
                    shadowElevation = 2.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = " 🎮 JUEGO DE SENSORES ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MoradoOscuro,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        letterSpacing = 1.5.sp
                    )
                }

                Text(
                    text = "CALIENTE & FRÍO",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = MoradoOscuro
                )
                Text(
                    text = "Encuentra la posición secreta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(color = MoradoAcentoLila.copy(alpha = 0.25f))
                )
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(MoradoPrincipal, MoradoOscuro)
                            )
                        )
                )
                Text(
                    text = "🔍",
                    fontSize = 54.sp
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MoradoBorde, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.88f)),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "👀 ¡Bienvenido, Explorador!",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Gira tu dispositivo y sigue las señales de temperatura para ganar.",
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MoradoBorde.copy(alpha = 0.6f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "⏱️ Mejor tiempo", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "01:45", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MoradoOscuro)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(30.dp)
                                .background(color = MoradoBorde)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "⭐ Récord Puntos", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "1200 pts", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MoradoOscuro)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = alIniciarJuego,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(10.dp, shape = RoundedCornerShape(18.dp), spotColor = MoradoPrincipal),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(MoradoGradienteInicio, MoradoPrincipal)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "▶  NUEVA PARTIDA",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                OutlinedButton(
                    onClick = alAbrirComoJugar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MoradoOscuro,
                        containerColor = Color.White.copy(alpha = 0.6f)
                    ),
                    border = BorderStroke(1.5.dp, MoradoPrincipal)
                ) {
                    Text("❓ CÓMO JUGAR", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
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
                    text = "Al iniciar la partida, el juego selecciona aleatoriamente una posición objetivo. Debes encontrarlo antes de que se agote el tiempo.",
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

    // Cálculo dinámico de la precisión en tiempo real (0 - 100%)
    val diferenciaGradosActual = remember(orientacionActual) {
        val diff = kotlin.math.abs(orientacionActual - juegoLogica.objetivoGrados)
        if (diff > 180f) 360f - diff else diff
    }
    val precisionEnVivo = remember(diferenciaGradosActual) {
        (100f - (diferenciaGradosActual / 180f * 100f)).coerceIn(0f, 100f).toInt()
    }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFF3E5F5),
                        Color(0xFFEDE7F6)
                    )
                )
            )
    ) {
        // Círculos decorativos de fondo
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = 100.dp, y = (-20).dp)
                .clip(CircleShape)
                .background(color = MoradoAcentoLila.copy(alpha = 0.12f))
        )
        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 50.dp)
                .clip(CircleShape)
                .background(color = MoradoPrincipal.copy(alpha = 0.10f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Superior: Volver, Temporizador y Chip de Precisión en Vivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = alVolver) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MoradoOscuro)
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "⏱️ 00:${tiempoRestante.toString().padStart(2, '0')}",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        color = TextoOscuro
                    )
                }

                // Chip de Precisión Reincorporado
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MoradoFondoTarjeta
                    ),
                    border = BorderStroke(1.dp, MoradoBorde),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "🎯 $precisionEnVivo%",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MoradoOscuro
                    )
                }
            }

            // Indicador de nivel / proximidad
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White.copy(alpha = 0.8f),
                border = BorderStroke(1.dp, MoradoBorde),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when(estadoTemperatura) {
                            "¡Caliente!" -> "🔥 ¡Muy Cerca!"
                            "Tibio" -> "⛅ Acercándote..."
                            else -> "❄️ Lejano"
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MoradoOscuro
                    )
                }
            }

            // Contenido Central con Anillos Estilo Brújula
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (seAcaboElTiempo) {
                    Text(
                        text = "¡TIEMPO AGOTADO!\nNo lograste encontrar la posición.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Gira tu teléfono para encontrar el objetivo",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.size(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Anillos concéntricos decorativos estilo Radar
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MoradoBorde.copy(alpha = 0.5f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(210.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MoradoBorde, CircleShape)
                    )

                    // Círculo central interactivo de Temperatura
                    Surface(
                        modifier = Modifier.size(170.dp),
                        shape = CircleShape,
                        color = when(estadoTemperatura) {
                            "¡Caliente!" -> Color(0xFFFFCDD2)
                            "Tibio" -> Color(0xFFFFF9C4)
                            else -> Color.White
                        },
                        shadowElevation = 6.dp
                    ) {}

                    Text(
                        text = if (seAcaboElTiempo) "FIN" else estadoTemperatura.uppercase(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black,
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
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "💡 OBJETIVO EN: ${juegoLogica.objetivoGrados.toInt()}° | TU GIRO: ${orientacionActual.toInt()}°",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextoOscuro
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (estadoTemperatura == "¡Caliente!" || seAcaboElTiempo) {
                    Button(
                        onClick = {
                            if (seAcaboElTiempo) {
                                alVolver()
                            } else {
                                val tiempoUsado = 45 - tiempoRestante
                                val puntosCalculados = (precisionEnVivo * 10) + (tiempoUsado * 2)
                                alGanar(tiempoUsado, puntosCalculados, precisionEnVivo)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (seAcaboElTiempo) MoradoOscuro else Color(0xFF4CAF50)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            if (seAcaboElTiempo) "VOLVER AL MENÚ" else "¡ENCONTRADO!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(52.dp))
                }
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
                Text(text = "Precisión alcanzada: $precision%", fontSize = 16.sp, color = TextoOscuro)
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