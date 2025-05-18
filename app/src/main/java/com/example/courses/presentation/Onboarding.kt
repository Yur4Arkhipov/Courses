package com.example.courses.presentation

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.courses.R
import com.example.courses.ui.theme.CoursesTheme

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    Column(
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

        Spacer(modifier = Modifier.height(50.dp))

        CoursesFlowCard(viewModel)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {},
            modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Text("Продолжить")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoursesFlowCard(viewModel: OnboardingViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 4,
    ) {
        uiState.courses.forEach { course ->
            val isRotated = uiState.rotateStates[course] == true
            val randomAngle = remember(course) { if ((0..1).random() == 0) 30f else -30f }
            val rotateAnimation by animateFloatAsState(
                targetValue = if (isRotated) randomAngle else 0f,
                animationSpec = tween(durationMillis = 400),
                label = "rotate_$course",
            )

            OutlinedButton(
                onClick = {
                    viewModel.changeRotateState(course)
                },
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = if (isRotated) Color(0x32333A4D) else Color(
                        0x12B956FF
                    ),
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.Red
                ),
                border = null,
                modifier = Modifier
                    .rotate(rotateAnimation)
                    .graphicsLayer(
                        renderEffect = BlurEffect(
                            radiusX = 0.21f,
                            radiusY = 0.21f,
                            edgeTreatment = TileMode.Decal
                        )
                    )
            ) {
                Text(text = course)
            }
        }
    }
}

@Preview(
    name = "Night mode onboarding",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OnboardingScreenPreview() {
    CoursesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OnboardingScreen(Modifier.padding(innerPadding))
        }
    }
}