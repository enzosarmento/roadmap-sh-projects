package views

import controllers.TaskController
import exceptions.CommandException
import models.Status
import models.Task
import repositories.TaskRepository
import services.TaskService
import kotlin.system.exitProcess

object ConsoleView {

    private val taskController = TaskController(TaskService(TaskRepository()))

    fun option(taskOption: String) {
        try {
            val commands = taskOption.split(" ")
            if (commands[0] != "task-cli") throw CommandException("Wrong command, use task-cli")

            if (commands.size > 1) {
                menuOptions(commands)
            }

        } catch (e: CommandException) {
            println(e.message)
        }
    }

    private fun menuOptions(commands: List<String>) {
        when (commands[1]) {
            "add" -> add(commands)
            "update" -> update(commands)
            "delete" -> delete(commands)
            "mark-in-progress" -> markInProgress(commands)
            "mark-in-done" -> markInDone(commands)
            "list" -> list(commands)
            "exit" -> {
                println("Task tracker closed")
                exitProcess(1)
            }
            else -> println("Invalid option")
        }
    }

    private fun add(commands: List<String>) {
        var description = ""
        if (commands.size > 2) {
            for (i in 2..<commands.size) {
                description += "${commands[i]} "
            }
            val message = taskController.addTask(description)
            println(message)
        } else {
            println("Task description is empty")
        }
    }

    private fun update(commands: List<String>) {
        var description = ""
        if (commands.size > 3) {
            for (i in 3..<commands.size) {
                description += "${commands[i]} "
            }
            if (isConvertibleToInt(commands[2])) {
                println(taskController.updateTask(commands[2].toInt(), description))
            } else {
                println("ID not specified")
            }
        } else {
            println("Task updated description is empty")
        }
    }

    private fun delete(commands: List<String>) {
        if (isConvertibleToInt(commands[commands.size - 1])) {
            println(taskController.removeTask(commands[commands.lastIndex].toInt()))
        } else {
            println("ID not specified")
        }
    }

    private fun markInProgress(commands: List<String>) {
        if (commands.size > 2) {
            if (isConvertibleToInt(commands[commands.lastIndex])) {
                println(taskController.taskStatus(commands[commands.lastIndex].toInt(), Status.PROGRESS))
            } else {
                println("ID not specified")
            }
        } else {
            println("Task mark in progress is empty")
        }
    }

    private fun markInDone(commands: List<String>) {
        if (commands.size > 2) {
            if (isConvertibleToInt(commands[commands.lastIndex])) {
                println(taskController.taskStatus(commands[commands.lastIndex].toInt(), Status.DONE))
            } else {
                println("ID not specified")
            }
        } else {
            println("Task mark in done is empty")
        }
    }

    private fun list(commands: List<String>) {
        if (commands.size > 2) {
            when (commands[commands.lastIndex]) {
                "todo" -> taskController.listAllTodo()
                "in-progress" -> taskController.listAllInProgress()
                "done" -> taskController.listAllDone()
                else -> taskController.listAllTasks()
            }
        } else {
            taskController.listAllTasks()
        }
    }

    private fun isConvertibleToInt(value: String): Boolean {
        return value.toIntOrNull() != null
    }
}