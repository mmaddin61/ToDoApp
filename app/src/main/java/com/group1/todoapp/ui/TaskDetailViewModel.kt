package com.group1.todoapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.group1.todoapp.TaskData
import com.group1.todoapp.TodoData
import com.group1.todoapp.UserDataFactory
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.data.PreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                id = toDoData.id,
                title = toDoData.title,
                tasks = newTasks
            )
            updateTaskDetailState(newToDoData) // Update UI state
            UserDataFactory.UpdateTodo(toDoData.id, toDoData)
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
        updateTaskDetailState(TodoData(0, title, tasks))
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

    fun updateDarkModePreference(darkMode: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                darkMode = darkMode
            )
        }
    }

    fun onDarkModePreferenceChange(darkMode: Boolean, context: Context) {
        updateDarkModePreference(darkMode)

        Datasource.setDarkTheme(darkMode, context)
    }

    fun isDarkMode(context: Context): Boolean {
        var darkMode = false
        CoroutineScope(Dispatchers.Default).launch {
            darkMode = PreferencesRepository(context).isDarkMode.first() ?: false
        }
        return darkMode
    }
}