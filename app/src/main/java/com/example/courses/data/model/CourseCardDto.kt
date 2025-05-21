package com.example.courses.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class CourseCardDto(
    val id: Int,
    val title: String,
    val text: String,
    @Serializable(with = PriceSerializer::class)
    val price: Int,
    val rate: String,
    @Serializable(with = LocalDateSerializer::class)
    val startDate: LocalDate,
    val hasLike: Boolean,
    @Serializable(with = LocalDateSerializer::class)
    val publishDate: LocalDate
)

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

object PriceSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Price", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Int {
        val priceString = decoder.decodeString().replace(" ", "")
        return priceString.toInt()
    }
}

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    val date = inputFormat.parse(this) ?: return this
    return outputFormat.format(date)
}