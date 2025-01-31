package controllers

import models.Status
import models.Task
import java.time.LocalDate

class TaskController(private val listTasks: MutableList<Task>) {

    companion object {
        var uuid = 1
    }

    fun addTask(description: String): String {
        val task = Task(
            uuid++,
            description,
            Status.TODO,
            LocalDate.now(),
            LocalDate.now()
        )
        listTasks.add(task)
        return "Task added successfully (ID: ${task.id})"
    }

    fun removeTask(): String {
        TODO()
    }
}