package com.example.courses.presentation.course_details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.courses.R
import com.example.courses.presentation.home.BlurredBox
import com.example.courses.ui.theme.CoursesTheme


@Composable
fun CourseDetailsScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
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
                            text = "4.9",
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
                        text = "22 мая 2024",
                        fontSize = 12.sp,
                    )
                }
            }
        }
        Text("Course details screen")
    }
}

@Preview(
    name = "Night mode home",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewCourseDetails() {
    CoursesTheme {
        CourseDetailsScreen()
    }
}