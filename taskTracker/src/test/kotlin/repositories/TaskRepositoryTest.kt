package repositories

import models.Status
import models.Task
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaskRepositoryTest {

    private lateinit var repository: TaskRepository

    @BeforeEach
    fun createRepository() {
        repository = TaskRepository()
    }

    private fun createTask(): Task {
        return if (repository.findAll().size > 0) {
            Task(
                repository.findAll()[repository.findAll().lastIndex].id + 1,
                "to study ${repository.findAll().size}",
                Status.TODO,
                LocalDate.now(),
                LocalDate.now()
            )
        } else {
            Task(
                repository.findAll().size + 1,
                "to study ${repository.findAll().size}",
                Status.TODO,
                LocalDate.now(),
                LocalDate.now()
            )
        }
    }

    @Test
    fun `must create a task`() {
        val success = repository.save(createTask())
        assertTrue(success, "It wasn't possible to create a task")
    }

    @Test
    fun `must update an existing task`() {
        repository.save(Task(801, "to study kotlin", Status.TODO, LocalDate.now(), LocalDate.now()))

        val success = repository.updatedById(801, "to study kotlin and spring")
        assertTrue(success)
        assertEquals("to study kotlin and spring", repository.findById(8)?.description)
    }

    @Test
    fun `must not create a task`() {
        val success = repository.save(Task(801, "to study kotlin", Status.TODO, LocalDate.now(), LocalDate.now()))
        assertFalse(success, "It was possible create a task")
    }

    @Test
    fun `must update status an existing task`() {
        repository.save(Task(802, "to study kotlin and junit", Status.TODO, LocalDate.now(), LocalDate.now()))

        val success = repository.updatedById(802, Status.DONE)
        assertTrue(success)
        assertEquals(Status.DONE, repository.findById(802)?.status)
    }

    @Test
    fun `must delete an existing task`() {
        repository.save(Task(803, "delete task", Status.TODO, LocalDate.now(), LocalDate.now()))

        val success = repository.deleteById(803)
        assertTrue(success)
        assertEquals(null, repository.findById(803))
    }
}