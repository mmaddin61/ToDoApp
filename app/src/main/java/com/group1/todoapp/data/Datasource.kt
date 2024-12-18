package com.group1.todoapp.data

import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val TAG = "Datasource"
object Datasource {

    private var isDarkTheme: Boolean = false

    private val toDoLists: MutableList<TodoData> = mutableListOf(
        TodoData(
            id = 0,
            title = "Shopping List",
            tasks = mutableListOf(
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
            id = 0,
            title = "Chores",
            tasks = mutableListOf(
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

    /**
     * Generates a list of tasks for testing purposes.
     *
     * @param count Number of tasks to generate.
     * @return List of tasks.
     */
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

    /**
     * Generates a list of to-do lists and tasks for testing purposes.
     *
     * @param listCount Number of to-do lists to generate.
     * @param taskCount Number of tasks to generate for each to-do list.
     * @return Test to-do list data.
     */
    private fun testDataFactory(listCount: Int = 1, taskCount: Int = 1): List<TodoData> {
        val todoLists: MutableList<TodoData> = emptyList<TodoData>().toMutableList()
        for (i in 1..listCount) {
            todoLists.add(
                TodoData(
                    id = 0,
                    title = "Test List $i",
                    tasks = taskDataFactory(taskCount)
                )
            )
        }
        return todoLists.toList()
    }

    /**
     * Gets the index of the to-do list from stored data.
     *
     * @param toDoData To-do list to get the index of.
     * @return The index of the to-do list or -1 if it was not found.
     */
    fun findIndexOf(toDoData: TodoData): Int {
        toDoLists.forEach {
            // The titles are compared as changing something like a single
            // task in the to-do list's tasks can result in the equals
            // operator returning false.
            // TODO: Add a business rule preventing two tasks from having the same title
            if (it.title == toDoData.title) {
                return toDoLists.indexOf(it)
            }
        }
        return -1
    }

    /**
     * @return List of stored to-do lists.
     */
    fun fetchToDoLists(): List<TodoData> {
        return toDoLists.toList()
    }

    /**
     * Updates a stored to-do list.
     *
     * @param index Index of the to-do list.
     * @param toDoData To-do list to store.
     */
    fun updateToDoLists(index: Int, toDoData: TodoData) {
        if (index >= 0 && index < toDoLists.size) {
            toDoLists[index] = toDoData
        } else {
            Log.e(TAG, "Index of $index is out of bounds.")
        }
    }

    /**
     * Adds a to-do list to stored data.
     *
     * @param toDoList To-do list to store.
     */
    fun addToDoList(toDoList: TodoData) {
        toDoLists.add(toDoList)
    }

    /**
     * Adds a task to a stored to-do list.
     *
     * @param toDoData To-do list the task is related to.
     * @param task Task to store.
     */
    fun addTask(toDoData: TodoData, task: TaskData) {
        val index = findIndexOf(toDoData)
        if (index != -1) {
            val newTasks = toDoData.tasks.toMutableList()
            newTasks.add(task)
            val newData = TodoData(0, toDoData.title, newTasks.toMutableList())
            toDoLists[index] = newData
        }
    }

    /*
    fun isDarkTheme(context: Context): Boolean {
        var darkMode: Boolean = false
        CoroutineScope(Dispatchers.Default).launch {
            darkMode = PreferencesRepository(context).isDarkMode.first() ?: false
        }
        return darkMode
    }

     */

    fun isDarkTheme(context: Context): Flow<Boolean?> {
        return PreferencesRepository(context).isDarkMode
    }

    fun setDarkTheme(darkTheme: Boolean, context: Context) {
        CoroutineScope(Dispatchers.Default).launch {
            PreferencesRepository(context).setDarkMode(darkTheme)
        }
    }
}