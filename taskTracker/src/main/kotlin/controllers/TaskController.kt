package controllers

import models.Status
import services.TaskService

class TaskController(private val taskService: TaskService) {

    fun addTask(description: String): String {
        val taskId = taskService.createTask(description)
        return "Task added successfully (ID: $taskId)"
    }

    fun updateTask(id: Int, newDescription: String): String {
        return if (taskService.updateTask(id, newDescription)) {
            "Task updated"
        } else {
            "Task not found"
        }
    }

    fun removeTask(id: Int): String {
        return if (taskService.removeTask(id)) {
            "Task removed"
        } else {
            "Task not found"
        }
    }

    fun taskStatus(id: Int, status: Status): String {
        return if (taskService.taskStatus(id, status)) {
            "Task status updated"
        } else {
            "Task not found"
        }
    }

    fun listAllTasks() {
        if (taskService.listAllTasks().isNotEmpty()) {
            taskService.listAllTasks().forEach {
                val createdAtFormatted = "${it.createdAt.dayOfMonth}/${it.createdAt.month.value}/${it.createdAt.year}"
                val updatedAtFormatted = "${it.updatedAt.dayOfMonth}/${it.updatedAt.month.value}/${it.updatedAt.year}"
                println("${it.id} - ${it.description} - created at $createdAtFormatted- updated at $updatedAtFormatted - Status: ${it.status}")
            }
        } else {
         println("Task List is empty")
        }
    }
}