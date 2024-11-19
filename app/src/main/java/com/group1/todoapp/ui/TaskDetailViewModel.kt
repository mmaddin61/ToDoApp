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

    /**
     * Called when a task's checkbox is clicked. Handles updating the UI state and storing the changes.
     *
     * @param task Task that the checkbox is bound to.
     * @param isChecked The desired state for the checkbox after it is clicked.
     */
    fun onTaskFinishedChange(
        task: TaskData,
        isChecked: Boolean
    ) {
        val taskIndex = _uiState.value.todoData.tasks.indexOf(task) // Get the specified task's index
        if (taskIndex != -1) { // Check if the task exists; indexOf returns -1 if the element was not found
            val toDoData: TodoData = _uiState.value.todoData
            val newTasks: MutableList<TaskData> = toDoData.tasks.toMutableList()
            newTasks[taskIndex] = TaskData(task.title, task.description, isChecked) // Update the task
            val newToDoData: TodoData = TodoData(
                title = toDoData.title,
                tasks = newTasks.toList()
            )
            updateTaskDetailState(newToDoData) // Update UI state
            Datasource.updateToDoLists(Datasource.findIndexOf(toDoData), newToDoData) // Store the changes
        } else {
            Log.e(TAG, "Task named '${task.title}' not found!")
        }
    }

    /**
     * Updates the UI state.
     *
     * @param title Name of the to-do list.
     * @param tasks List of the to-do list's tasks.
     */
    fun updateTaskDetailState(title: String, tasks: MutableList<TaskData>) {
        updateTaskDetailState(TodoData(title, tasks))
    }

    /**
     * Updates the UI state.
     *
     * @param toDoData New to-do list data to be stored.
     */
    fun updateTaskDetailState(toDoData: TodoData) {
        _uiState.update { currentState ->
            currentState.copy(
                todoData = toDoData
            )
        }
    }
}