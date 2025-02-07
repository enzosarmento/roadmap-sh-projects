package repositories

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Status
import models.Task
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate

class TaskRepository {

    private val pathName = "src/main/resources"
    private var tasks: MutableList<Task>

    init {
        tasks = initializeList()
    }

    fun save(task: Task): Boolean {
        val existingTask = tasks.find { it.id == task.id }
        if (existingTask == null) {
            tasks.add(task)
            writeJson()
            return true
        }
        return false
    }

    fun findAll() = tasks

    fun findAllTodo() = tasks.filter { it.status == Status.TODO }

    fun findAllInProgress() = tasks.filter { it.status == Status.PROGRESS }

    fun findAllDone() = tasks.filter { it.status == Status.DONE }

    fun findById(id: Int) = tasks.find { it.id == id }

    fun updatedById(id: Int, newDescription: String): Boolean {
        val taskIndex = tasks.indexOfFirst { it.id == id }
        return if (taskIndex != -1) {
            tasks[taskIndex].description = newDescription
            tasks[taskIndex].updatedAt = LocalDate.now()
            writeJson()
            true
        } else {
            false
        }
    }

    fun updatedById(id: Int, status: Status): Boolean {
        val taskIndex = tasks.indexOfFirst { it.id == id }
        return if (taskIndex != -1) {
            tasks[taskIndex].status = status
            writeJson()
            true
        } else {
            false
        }
    }

    fun deleteById(id: Int): Boolean {
        if (tasks.removeIf { it.id == id }) {
            writeJson()
            return true
        }
        return false
    }

    private fun writeJson() {
        val json = Json.encodeToString(tasks)
        File("${pathName}/tasks.json").writeText(json)
    }

    private fun initializeList(): MutableList<Task> {
        try {
            val json = File("${pathName}/tasks.json").readText()
            return Json.decodeFromString(json)
        } catch (e: Exception) {
            File("${pathName}/tasks.json").createNewFile()
            val emptyListTask = mutableListOf<Task>()
            val json = Json.encodeToString(emptyListTask)
            File("${pathName}/tasks.json").writeText(json)
            return Json.decodeFromString(json)
        }
    }
}