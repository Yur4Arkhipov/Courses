package com.example.courses.presentation.course_details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courses.R
import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.model.formatDate
import com.example.courses.presentation.favorites.FavoritesViewModel
import com.example.courses.presentation.home.BlurredBox
import com.example.courses.presentation.home.HomeScreenViewModel
import kotlinx.coroutines.launch


@Composable
fun CourseDetailsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel
) {
    val course by viewModel.selectedCourse.collectAsState()
    Log.i("TAG", "$course")

    if (course == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Курс не выбран")
        }
        return
    }

    val scrollState = rememberScrollState()
    var imageHeight by remember { mutableFloatStateOf(240f) }

    LaunchedEffect(scrollState.value) {
        val newHeight = (240f - scrollState.value * 0.5f).coerceAtLeast(0f)
        imageHeight = newHeight
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        TopPreview(
            onReturnBackScreen = { navController.popBackStack() },
            course = course
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = course?.title ?: "null",
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
            Row {
                Image(
                    painter = painterResource(R.drawable.company_image),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(100))
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Автор",
                        fontSize = 14.sp,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Text(
                        text = "Merion Academy",
                        fontSize = 18.sp,
                        lineHeight = 18.sp
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Начать курс",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                )
            }
            Spacer(Modifier.height(3.dp))
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = "Перейти на платформу",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            CourseDescription()

/*        repeat(15) {
            Text(
                text = "Подробная информация о курсе... $it",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }*/
        }

    }
}

@Composable
fun CourseDescription() {
    Column {
        Text(
            text = "О Курсе",
            fontSize = 22.sp,
            lineHeight = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "У вас будет 7 видеоуроков в высоком качестве. " +
                    "На них спикер объясняет теорию и показывает как " +
                    "выполнять практические задания. Доступ к материалам " +
                    "сохраняется на 2 года.\n\n" +
                    "Кроме теоретических материалов вас ждут тесты и практические задания. " +
                    "Они помогут лучше запомнить новую информацию и прокачать навыки, " +
                    "которые необходимы для реальной работы с RabbitMQ.",
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun TopPreview(
    onReturnBackScreen: () -> Unit,
    course: CourseCardDto?,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoritesState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.cover_jpg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = { onReturnBackScreen() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface
            )
        }

        IconButton(
            onClick = {
                course?.let {
                    coroutineScope.launch {
                        viewModel.toggleFavorite(it)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bookmark_dark),
                contentDescription = null,
                tint = if (course?.id in favorites) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            BlurredBox(
                modifier = Modifier
                    .width(46.dp)
                    .height(22.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star_fill),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = course?.rate ?: "null",
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(Modifier.width(5.dp))
            BlurredBox(
                modifier = Modifier
                    .width(86.dp)
                    .height(22.dp)
            ) {
                Text(
                    text = course?.startDate.toString().formatDate(),
                    fontSize = 12.sp,
                )
            }
        }
    }
}