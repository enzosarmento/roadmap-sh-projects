package models

import kotlinx.serialization.Serializable
import serializers.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Task(
    val id: Int,
    var description: String,
    var status: Status,
    @Serializable(with = LocalDateSerializer::class)
    val createdAt: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    var updatedAt: LocalDate
)
