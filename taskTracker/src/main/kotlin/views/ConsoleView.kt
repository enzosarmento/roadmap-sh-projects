package views

import controllers.TaskController
import exceptions.CommandException
import models.Status
import models.Task
import repositories.TaskRepository
import services.TaskService

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
            "add" -> {
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
            "update" -> {
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
            "delete" -> {
                if (isConvertibleToInt(commands[commands.size - 1])) {
                    println(taskController.removeTask(commands[commands.lastIndex].toInt()))
                } else {
                    println("ID not specified")
                }
            }
            "mark-in-progress" -> {
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
            "mark-in-done" -> {
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
            "list" -> {
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
            "exit" -> println("Task tracker closed")
            else -> println("Invalid option")
        }
    }

    private fun isConvertibleToInt(value: String): Boolean {
        return value.toIntOrNull() != null
    }
}