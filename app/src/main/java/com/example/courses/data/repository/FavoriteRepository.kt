package com.example.courses.data.repository

import com.example.courses.data.local.FavoriteCourse
import com.example.courses.data.local.FavoriteCourseDao
import com.example.courses.data.model.CourseCardDto
import com.example.courses.domain.repository.FavoriteRepository
import jakarta.inject.Inject
import kotlinx.datetime.LocalDate

class FavoritesRepository @Inject constructor(
    private val favoriteCourseDao: FavoriteCourseDao
) : FavoriteRepository {
    override suspend fun addToFavorites(course: CourseCardDto) {
        favoriteCourseDao.insert(course.toFavoriteCourse())
    }

    override suspend fun removeFromFavorites(courseId: Int) {
        favoriteCourseDao.delete(FavoriteCourse(id = courseId, title = "", text = "",
            price = 0, rate = "", startDate = "", hasLike = false, publishDate = ""))
    }

    override suspend fun isFavorite(courseId: Int): Boolean {
        return favoriteCourseDao.isFavorite(courseId)
    }

    override suspend fun getAllFavorites(): List<CourseCardDto> {
        return favoriteCourseDao.getAllFavorites().map { it.toCourseCardDto() }
    }

}

fun FavoriteCourse.toCourseCardDto(): CourseCardDto = CourseCardDto(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = LocalDate.parse(startDate),
    hasLike = hasLike,
    publishDate = LocalDate.parse(publishDate)
)

fun CourseCardDto.toFavoriteCourse(): FavoriteCourse = FavoriteCourse(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate.toString(),
    hasLike = hasLike,
    publishDate = publishDate.toString()
)
