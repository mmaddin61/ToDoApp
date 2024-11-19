package com.group1.todoapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData
import com.group1.todoapp.data.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val TAG = "TaskDetailViewModel"
class TaskDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    fun onTaskFinishedChange(
        task: TaskData,
        isChecked: Boolean
    ) {
        val taskIndex = _uiState.value.todoData.tasks.indexOf(task)
        if (taskIndex != -1) {
            val toDoData: TodoData = _uiState.value.todoData
            val newTasks: MutableList<TaskData> = toDoData.tasks.toMutableList()
            newTasks[taskIndex] = TaskData(task.title, task.description, isChecked)
            val newToDoData: TodoData = TodoData(
                title = toDoData.title,
                tasks = newTasks.toList()
            )
            updateTaskDetailState(newToDoData)
            Datasource.updateToDoLists(Datasource.findIndexOf(toDoData), newToDoData)
        } else {
            Log.e(TAG, "Task named '${task.title}' not found!")
        }
    }

    fun updateTaskDetailState(title: String, tasks: MutableList<TaskData>) {
        updateTaskDetailState(TodoData(title, tasks))
    }

    fun updateTaskDetailState(toDoData: TodoData) {
        _uiState.update { currentState ->
            currentState.copy(
                todoData = toDoData
            )
        }
    }
}