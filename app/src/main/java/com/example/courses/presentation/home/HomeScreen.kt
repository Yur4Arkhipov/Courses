package com.example.courses.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courses.R
import com.example.courses.UiState
import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.model.formatDate
import com.example.courses.presentation.navigation.SubLevelRoutes
import com.example.courses.ui.theme.Glass


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val sortType = viewModel.sortType.collectAsState().value

    val combinedPadding = PaddingValues(
        top = innerPadding.calculateTopPadding(),
        bottom = 0.dp,
        start = 20.dp,
        end = 20.dp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(combinedPadding)
    ) {
        SearchBar()
        Spacer(Modifier.height(15.dp))
        FilterBar(
            sortType = sortType,
            onValueChanged = { newSortType ->
                viewModel.setSortType(newSortType)
            }
        )
        Spacer(Modifier.height(15.dp))
        when(state) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val courses = state.data

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(courses) { course ->
                        CourseCard(
                            course = course,
                            navController = navController
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        state.message,
                        color = Color.Red
                    )
                }
            }
            else -> Unit
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = "",
            enabled = false,
            onValueChange = { },
            placeholder = {
                Text(
                    text = "Search courses...",
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
        )

        Spacer(Modifier.width(10.dp))

        IconButton(
            onClick = {  },
            enabled = false,
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(28.dp)
                )
                .clip(RoundedCornerShape(30.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun FilterBar(
    sortType: HomeScreenViewModel.SortType,
    onValueChanged: (HomeScreenViewModel.SortType) -> Unit,
) {
    val sortText = when (sortType) {
        is HomeScreenViewModel.SortType.ByDate -> "По дате добавления"
        is HomeScreenViewModel.SortType.ByPrice -> "По цене"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val newSortType = when (sortType) {
                    is HomeScreenViewModel.SortType.ByDate -> HomeScreenViewModel.SortType.ByPrice
                    is HomeScreenViewModel.SortType.ByPrice -> HomeScreenViewModel.SortType.ByDate
                }
                onValueChanged(newSortType)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = sortText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        )
        Spacer(Modifier.width(5.dp))
        Image(
            painter = painterResource(R.drawable.arrow_down_up),
            contentDescription = null
        )
    }
}

@Composable
fun CourseCard(
    course: CourseCardDto,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
       modifier = modifier
           .height(230.dp)
           .fillMaxWidth()
           .background(
               color = MaterialTheme.colorScheme.surface,
               shape = RoundedCornerShape(16.dp)
           )
    ) {
        CourseCardTopSection(course, modifier = Modifier.weight(1f))
        CourseCardBottomSection(
            course = course,
            navController = navController,
            modifier = Modifier.weight(1f))
    }
}

@Composable
fun CourseCardTopSection(
    course: CourseCardDto,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.cover),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        BlurredBox(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(28.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bookmark),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
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
                        text = course.rate,
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
                    text = course.startDate.toString().formatDate(),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

/**
 * К сожалению эффекта замыленности добиться не удалось
 */
@Composable
fun BlurredBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Glass.copy(alpha = 0.3f),
    cornerRadius: Dp = 12.dp,
    blurRadius: Dp = 16.dp,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor, shape = RoundedCornerShape(cornerRadius)),
        contentAlignment = contentAlignment
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(blurRadius)
                .background(backgroundColor)
        )
        content()
    }
}

@Composable
fun CourseCardBottomSection(
    course: CourseCardDto,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(
            text = course.title,
            fontSize = 16.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.15.sp
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = course.text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
            modifier = Modifier.alpha(0.7f)
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${course.price} ₽")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(SubLevelRoutes.CourseDetails)
                }
            ) {
                Text(
                    text = "Подробнее",
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    letterSpacing = 0.4.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(3.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_short_fill),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/*@Preview(
    name = "Night mode home",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreview() {
    CoursesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeScreen(innerPadding = innerPadding)
        }
    }
}*/
