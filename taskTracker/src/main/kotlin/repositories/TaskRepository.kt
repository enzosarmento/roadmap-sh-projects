package repositories

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Status
import models.Task
import java.io.File
import java.time.LocalDate

class TaskRepository {

    private var tasks: MutableList<Task>

    init {
        tasks = initializeList()
    }

    fun save(task: Task) {
        tasks.add(task)
        writeJson()
    }

    fun findAll() = tasks

    fun findAllInProgress() = tasks.filter { it.status == Status.PROGRESS }

    fun findAllInDone() = tasks.filter { it.status == Status.DONE }

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
        File("src/main/resources/tasks.json").writeText(json)
    }

    private fun initializeList(): MutableList<Task> {
        val json = File("src/main/resources/tasks.json").readText()
        return Json.decodeFromString(json)
    }
}