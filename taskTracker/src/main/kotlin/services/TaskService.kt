package services

import models.Status
import models.Task
import repositories.TaskRepository
import java.time.LocalDate

class TaskService(private val taskRepository: TaskRepository) {

    fun createTask(description: String): Int {
        val id = taskRepository.findAll().size + 1
        val task = Task(
            id,
            description,
            Status.TODO,
            LocalDate.now(),
            LocalDate.now()
        )
        taskRepository.save(task)
        return task.id
    }

    fun updateTask(id: Int, newDescription: String): Boolean {
        return taskRepository.updatedById(id, newDescription)
    }

    fun removeTask(id: Int): Boolean {
        return taskRepository.deleteById(id)
    }

    fun taskStatus(id: Int, status: Status): Boolean {
        return taskRepository.updatedById(id, status)
    }

    fun listAllTasks() = taskRepository.findAll()

    fun listAllTodo() = taskRepository.findAllTodo()

    fun listAllInProgress() = taskRepository.findAllInProgress()

    fun listAllDone() = taskRepository.findAllDone()
}