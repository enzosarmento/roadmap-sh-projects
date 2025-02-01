package repositories

import models.Task
import java.time.LocalDate

class TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun save(task: Task) {
        tasks.add(task)
    }

    fun findAll() = tasks

    fun updatedById(id: Int, newDescription: String): Boolean {
        val taskIndex = tasks.indexOfFirst { it.id == id }
        return if (taskIndex != -1) {
            tasks[taskIndex].description = newDescription
            tasks[taskIndex].updatedAt = LocalDate.now()
            true
        } else {
            false
        }
    }

    fun deleteById(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }
}