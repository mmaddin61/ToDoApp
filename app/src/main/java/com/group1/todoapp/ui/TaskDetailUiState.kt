package com.group1.todoapp.ui

import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData

data class TaskDetailUiState(
    val todoData: TodoData = TodoData(0, "", mutableListOf())
)
