package services

import models.Status
import models.Task
import repositories.TaskRepository
import java.time.LocalDate

class TaskService(private val taskRepository: TaskRepository) {

    fun createTask(description: String): Int {
        val task = newTask(description)
        return if (taskRepository.save(task)) {
            task.id
        } else {
            -1
        }
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

    private fun newTask(description: String): Task {
        return if (taskRepository.findAll().size > 0) {
            Task(
                taskRepository.findAll()[taskRepository.findAll().lastIndex].id + 1,
                description,
                Status.TODO,
                LocalDate.now(),
                LocalDate.now()
            )
        } else {
            Task(
                taskRepository.findAll().size + 1,
                description,
                Status.TODO,
                LocalDate.now(),
                LocalDate.now()
            )
        }
    }
}