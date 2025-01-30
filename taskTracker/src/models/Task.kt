package models

import java.time.LocalDate

data class Task(
    val id: Int,
    var description: String,
    var status: Status,
    val createdAt: LocalDate,
    var updatedAt: LocalDate
)
