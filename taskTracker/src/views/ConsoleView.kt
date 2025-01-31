package views

import controllers.TaskController
import exceptions.CommandException
import models.Task

object ConsoleView {

    private val listMenu = listOf(
        "add 'description'", "update 'id'", "delete 'id'", "mark-in-progress 'id'",
        "mark-in-done 'id'", "list", "list done", "list todo", "list in-progress", "exit"
        )
    private val taskList = mutableListOf<Task>()
    private val taskController = TaskController(taskList)

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

                    }
                    "delete" -> {

                    }
                    "mark-in-progress" -> {

                    }
                    "mark-in-done" -> {

                    }
                    "list" -> {

                    }
                    "exit" -> {

                    }
                    else -> println("Invalid options")
                }
            }

        } catch (e: CommandException) {
            println(e.message)
        }
    }
}