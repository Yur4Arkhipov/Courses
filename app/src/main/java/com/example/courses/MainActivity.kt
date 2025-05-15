package com.example.courses

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.courses.ui.theme.CoursesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoursesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnboardingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    val courseNames = listOf(
        "1С Администрирование", "RabbitMQ", "Трафик",
        "Контент маркетинг", "B2B маркетинг", "Google аналитика",
        "UX исследователь", "Веб-аналитика", "Big Data",
        "Геймдизайн", "Веб-дизайн", "Cinema 4D", "Промпт инжениринг",
        "Webflow", "Three.js", "Парсинг", "Python-разработка"
    )

    var rotateStates = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = stringResource(R.string.onboarding_description),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = modifier.horizontalScroll(rememberScrollState())
        ) {
            FlowRow(
                modifier.width(600.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                courseNames.forEach { course ->
                    var isRotated = rotateStates[course] == true
                    val randomAngle = remember(course) { if ((0..1).random() == 0) 30f else -30f }
                    val rotateAnimation by animateFloatAsState(
                        targetValue = if (isRotated) randomAngle else 0f,
                        animationSpec = tween(durationMillis = 400),
                        label = "rotate_$course",
                    )

                    OutlinedButton(
                        onClick = {
                            rotateStates[course] = !isRotated
                        },
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        colors = ButtonColors(
                            contentColor = Color.White,
                            containerColor = Color.Gray,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.Red
                        ),
                        modifier = Modifier.rotate(rotateAnimation)
                    ) {
                        Text(text = course)
                    }
                }
            }
        }

        Button(
            onClick = {},
            modifier.fillMaxWidth()
        ) {
            Text("Продолжить")
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun OnboardingScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        OnboardingScreen(Modifier.padding(innerPadding))
    }
}