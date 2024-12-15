package com.group1.todoapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData

data class TaskDetailUiState(
    val todoData: TodoData = TodoData(0, "", mutableListOf()),
    val darkMode: MutableState<Boolean> = mutableStateOf(false)
)
