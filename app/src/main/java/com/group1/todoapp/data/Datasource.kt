package com.group1.todoapp.data

import android.util.Log
import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData

const val TAG = "Datasource"
object Datasource {

    private val toDoLists: MutableList<TodoData> = mutableListOf(
        TodoData(
            title = "Shopping List",
            tasks = listOf(
                TaskData(
                    title = "Milk",
                    description = "1gal 2% milk",
                    finished = false
                ),
                TaskData(
                    title = "Eggs",
                    description = "Dozen eggs",
                    finished = false
                ),
                TaskData(
                    title = "Bread",
                    description = "Loaf of whole wheat bread",
                    finished = false
                ),
                TaskData(
                    title = "Butter",
                    description = "Stick of butter",
                    finished = false
                )
            )
        ),
        TodoData(
            title = "Chores",
            tasks = listOf(
                TaskData(
                    title = "Clean garage",
                    description = "Sweep the garage floor.",
                    finished = false
                ),
                TaskData(
                    title = "Clean kitchen",
                    description = "Sweep the floor and scrub all appliances.",
                    finished = false
                ),
                TaskData(
                    title = "Take out trash",
                    description = "Take garbage bags to the trash can and move it to the curb.",
                    finished = false
                ),
                TaskData(
                    title = "Feed pets",
                    description = "Feed the cat and dog.",
                    finished = false
                ),
                TaskData(
                    title = "Throw away expired food",
                    description = "Throw away any expired food in the fridge.",
                    finished = false
                )
            )
        )
    )

    private fun taskDataFactory(count: Int = 1): MutableList<TaskData> {
        val tasks: MutableList<TaskData> = emptyList<TaskData>().toMutableList()
        for (i in 1..count) {
            tasks.add(
                TaskData(
                    title = "Task $i",
                    description = "Description for Task $i.",
                    finished = i%2==0 // Set true if i is even
                )
            )
        }
        return tasks
    }

    private fun testDataFactory(listCount: Int = 1, taskCount: Int = 1): List<TodoData> {
        val todoLists: MutableList<TodoData> = emptyList<TodoData>().toMutableList()
        for (i in 1..listCount) {
            todoLists.add(
                TodoData(
                    title = "Test List $i",
                    tasks = taskDataFactory(taskCount)
                )
            )
        }
        return todoLists.toList()
    }

    fun findIndexOf(toDoData: TodoData): Int {
        toDoLists.forEach {
            if (it.title == toDoData.title) {
                return toDoLists.indexOf(it)
            }
        }
        return -1
    }

    fun fetchToDoLists(): List<TodoData> {
        return toDoLists.toList()
    }

    fun updateToDoLists(index: Int, toDoData: TodoData) {
        if (index >= 0 && index < toDoLists.size) {
            toDoLists[index] = toDoData
        } else {
            Log.e(TAG, "Index of $index is out of bounds.")
        }
    }

    fun addToDoList(toDoList: TodoData) {
        toDoLists.add(toDoList)
    }

    fun addTask(toDoData: TodoData, task: TaskData) {
        val index = findIndexOf(toDoData)
        if (index != -1) {
            val newTasks = toDoData.tasks.toMutableList()
            newTasks.add(task)
            val newData = TodoData(toDoData.title, newTasks.toList())
            toDoLists[index] = newData
        }
    }
}