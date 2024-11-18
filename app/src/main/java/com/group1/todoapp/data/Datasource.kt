package com.group1.todoapp.data

import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData

class Datasource {
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
        return fetchToDoLists().indexOf(toDoData)
    }

    fun fetchToDoLists(): List<TodoData> {
        return testDataFactory(listCount = 3, taskCount = 5)
    }
}