package com.group1.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.components.TitleText
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme

class TodoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: TaskDetailViewModel = viewModel() // Get the ViewModel
            viewModel.updateDarkModePreference(Datasource.isDarkTheme(LocalContext.current))
            val uiState: TaskDetailUiState by viewModel.uiState.collectAsState() // Get the UI state
            ToDoAppTheme(darkTheme = uiState.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoListLayout()
                }
            }
        }
    }

    @Composable
    fun TitleDisplay(title : MutableState< String >) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Title",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )

            TextField(
                value = title.value,
                onValueChange = {
                    title.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }

    @Composable
    fun DisplayTask(title : MutableState<String>, desc : MutableState<String>) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField (
                value = title.value,
                onValueChange = { title.value = it } ,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            TextField(
                value = desc.value,
                onValueChange = { desc.value = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }

    @Composable
    fun TodoListLayout() {
        val title = remember { mutableStateOf("") }
        val tasks = remember { mutableStateOf(listOf<MutableState<TaskData>>()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* Title */
            Divider(color = Color.Black, modifier = Modifier.padding(top = 25.dp))
            TitleText(text = "Create Todo List")
            Divider(color = Color.Black, modifier = Modifier.padding(bottom = 25.dp))

            Column(
                modifier = Modifier.padding(start = 50.dp, end = 50.dp)
            ) {
                TitleDisplay(title)

                tasks.value.forEach() {
                    val taskTitle = remember { mutableStateOf(it.value.title) }
                    val taskDesc = remember { mutableStateOf(it.value.description) }
                    DisplayTask(taskTitle, taskDesc)
                    it.value = TaskData(taskTitle.value, taskDesc.value, false)
                }

                Button(
                    onClick = {
                        tasks.value += mutableStateOf(TaskData("task ${tasks.value.count()}", "", false))
                        Log.i("TODOLIST", "Adding Task")
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("New Task")
                }
                Button(
                    onClick = {
                        UserDataFactory.AddTodo(TodoData(0, title.value, tasks.value.map { task -> task.value }.toMutableList()))

                        val intent = Intent(this@TodoListActivity, MainActivity::class.java)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Confirm")
                }
                Button(
                    onClick = {
                        val intent = Intent(this@TodoListActivity, MainActivity::class.java)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Back")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TodoListPreview() {
        ToDoAppTheme {
            TodoListLayout()
        }
    }
}