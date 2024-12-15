package com.group1.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme

class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TaskDetailViewModel = viewModel()
            //viewModel.updateDarkModePreference(Datasource.isDarkTheme(LocalContext.current))
            val uiState: TaskDetailUiState by viewModel.uiState.collectAsState()
            val context = LocalContext.current
            val darkMode by Datasource.isDarkTheme(context).collectAsState(initial = false)
            viewModel.updateDarkModePreference(darkMode ?: false)
            ToDoAppTheme(darkTheme = uiState.darkMode) {
                HelpContent()
            }
        }
    }

    @Composable
    fun HelpContent() {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Help Information", modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "This is where you can add some instructions or help regarding how to use the app.")
            Text(text = "This to do application will help you get organized and take advantage of your life! Don’t let small things get in the way of your success. Use our to do list application to keep track of all the small daily tasks you need to accomplish and get it all done! ")
            Text(text = "Navigation Instructions")
            Text(text = "Click on the buttons or Next to Continue.")
            Text(text = "Click <- to go back")
        }
    }
}