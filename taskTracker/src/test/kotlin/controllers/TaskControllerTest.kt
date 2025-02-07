package controllers

import models.Status
import models.Task
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.TaskRepository
import services.TaskService
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TaskControllerTest {

    private lateinit var controller: TaskController
    private lateinit var service: TaskService
    private lateinit var repository: TaskRepository

    @BeforeEach
    fun createController() {
        repository = TaskRepository()
        service = TaskService(repository)
        controller = TaskController(service)
    }

    @Test
    fun `must add a task`() {
        val res = controller.addTask("to study korean")
        val taskId = repository.findAll()[repository.findAll().lastIndex]
        assertEquals("Task added successfully (ID: ${taskId.id})", res)
    }

    @Test
    fun `must update status an existing task`() {
        val success = repository.save(Task(
            800,
            "to study chinese",
            Status.TODO,
            LocalDate.now(),
            LocalDate.now()
        ))

        assertTrue(success)

        val res = controller.taskStatus(800, Status.DONE)
        assertEquals("Task status updated", res)
    }

    @Test
    fun `must not update status an existing task`() {
        val res = controller.taskStatus(999, Status.PROGRESS)
        assertEquals("Task not found", res)
    }

    @Test
    fun `must update an existing task`() {
        val success = repository.save(Task(
            800,
            "to study chinese",
            Status.TODO,
            LocalDate.now(),
            LocalDate.now()
        ))

        assertTrue(success)

        val res = controller.updateTask(800, "to study greek")
        assertEquals("Task updated", res)
    }

    @Test
    fun `must not update an existing task`() {
        val res = controller.updateTask(999, "to study chinese")
        assertEquals("Task not found", res)
    }

    @Test
    fun `must remove an existing task`() {
        val res = controller.removeTask(repository.findAll()[repository.findAll().lastIndex].id)
        assertEquals("Task removed", res)
    }

    @Test
    fun `must not remove an existing task`() {
        val res = controller.removeTask(999)
        assertEquals("Task not found", res)
    }
}