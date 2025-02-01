package views

import controllers.TaskController
import exceptions.CommandException
import repositories.TaskRepository
import services.TaskService

object ConsoleView {

    private val listMenu = listOf(
        "add 'description'", "update 'id'", "delete 'id'", "mark-in-progress 'id'",
        "mark-in-done 'id'", "list", "list done", "list todo", "list in-progress", "exit"
    )

    private val taskController = TaskController(TaskService(TaskRepository()))

    fun showMenu() {
        println("Task Tracker")
        listMenu.forEach {
            println("Write task-cli $it")
        }
    }

    fun option(taskOption: String) {
        try {
            val commands = taskOption.split(" ")
            if (commands[0] != "task-cli") throw CommandException("Wrong command, use task-cli")

            if (commands.size > 1) {
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
                            println(taskController.updateTask(commands[2].toInt(), description))
                        } else {
                            println("Task updated description is empty")
                        }
                    }
                    "delete" -> {
                        println(taskController.removeTask(commands[commands.size - 1].toInt()))
                    }
                    "mark-in-progress" -> {
                        println("OK")
                    }
                    "mark-in-done" -> {
                        println("OK")
                    }
                    "list" -> {
                        taskController.listAllTasks()
                    }
                    "exit" -> {
                        println("OK")
                    }
                    else -> println("Invalid option")
                }
            }

        } catch (e: CommandException) {
            println(e.message)
        }
    }
}