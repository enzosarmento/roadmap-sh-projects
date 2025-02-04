import views.ConsoleView

fun main() {
    consoleTaskTracker()
}

private fun consoleTaskTracker() {
    while (true) {
        val command = readln()
        ConsoleView.option(command)
    }
}