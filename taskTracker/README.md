# Task Tracker
A simple todo list project created challenge from roadmap.sh.

---
## Prerequisites

- ***JDK 21+***
    - You can check your JDK version by running:
      ```bash
      java -version
      ```

---
## How to run

---
Clone the repository and run the following command:
```bash
git clone https://github.com/enzosarmento/roadmap-sh-projects
cd roadmap-sh-projects/taskTracker/
```

---
Run the following command to build and run the project:
```bash
./gradlw build -x test
java -jar build/libs/taskTracker-1.0.jar
```

---
## Example
The list of commands and their usage is given below:
```bash
# Adding a new task
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating and deleting tasks
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1

# Marking a task as in progress or done
task-cli mark-in-progress 1
task-cli mark-done 1

# Listing all tasks
task-cli list

# Listing tasks by status
task-cli list done
task-cli list todo
task-cli list in-progress
```