package com.example.courses.data.repository

import com.example.courses.data.model.CourseCardDto
import com.example.courses.data.utils.CoursesListWrapper
import com.example.courses.domain.repository.CourseCardRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject


class CourseCardRepositoryImpl @Inject constructor() : CourseCardRepository {
    override suspend fun getCourseCards(): List<CourseCardDto> {
        val json = "{\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"id\": 100,\n" +
                "      \"title\": \"Java-разработчик с нуля\",\n" +
                "      \"text\": \"Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.\",\n" +
                "      \"price\": \"999\",\n" +
                "      \"rate\": \"4.9\",\n" +
                "      \"startDate\": \"2024-05-22\",\n" +
                "      \"hasLike\": false,\n" +
                "      \"publishDate\": \"2024-02-02\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 101,\n" +
                "      \"title\": \"3D-дженералист\",\n" +
                "      \"text\": \"Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.\",\n" +
                "      \"price\": \"12 000\",\n" +
                "      \"rate\": \"3.9\",\n" +
                "      \"startDate\": \"2024-09-10\",\n" +
                "      \"hasLike\": false,\n" +
                "      \"publishDate\": \"2024-01-20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 102,\n" +
                "      \"title\": \"Python Advanced. Для продвинутых\",\n" +
                "      \"text\": \"Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения\",\n" +
                "      \"price\": \"1 299\",\n" +
                "      \"rate\": \"4.3\",\n" +
                "      \"startDate\": \"2024-10-12\",\n" +
                "      \"hasLike\": true,\n" +
                "      \"publishDate\": \"2024-08-10\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 103,\n" +
                "      \"title\": \"Системный аналитик\",\n" +
                "      \"text\": \"Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.\",\n" +
                "      \"price\": \"1 199\",\n" +
                "      \"rate\": \"4.5\",\n" +
                "      \"startDate\": \"2024-04-15\",\n" +
                "      \"hasLike\": false,\n" +
                "      \"publishDate\": \"2024-01-13\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 104,\n" +
                "      \"title\": \"Аналитик данных\",\n" +
                "      \"text\": \"В этом уроке вы узнаете, кто такой аналитик данных и какие задачи он решает. А главное — мы расскажем, чему вы научитесь по завершении программы обучения профессии «Аналитик данных».\",\n" +
                "      \"price\": \"899\",\n" +
                "      \"rate\": \"4.7\",\n" +
                "      \"startDate\": \"2024-06-20\",\n" +
                "      \"hasLike\": false,\n" +
                "      \"publishDate\": \"2024-03-12\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val wrapper = Json.decodeFromString<CoursesListWrapper>(json)
        return wrapper.courses
    }

    override suspend fun getCourseCard(): CourseCardDto {
        val json = "{\n" +
                "  \"id\": 100,\n" +
                "  \"title\": \"Java-разработчик с нуля\",\n" +
                "  \"text\": \"Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.\",\n" +
                "  \"price\": \"999\",\n" +
                "  \"rate\": \"4.9\",\n" +
                "  \"startDate\": \"2024-05-22\",\n" +
                "  \"hasLike\": false,\n" +
                "  \"publishDate\": \"2024-02-02\"\n" +
                "}"
        val course = Json.decodeFromString<CourseCardDto>(json)
        return course
    }
}
