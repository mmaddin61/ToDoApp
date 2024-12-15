package com.group1.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.components.TitleText
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme
import androidx.compose.material3.Text

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
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF212121))
        ) {
            Column {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text("Title", color = Color(0xFF9E9E9E), fontSize = 16.sp) },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 18.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF424242)))

                OutlinedTextField(
                    value = desc.value,
                    onValueChange = { desc.value = it },
                    placeholder = { Text("Description", color = Color(0xFF9E9E9E), fontSize = 14.sp) },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 18.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth().height(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
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
                        tasks.value += mutableStateOf(TaskData("", "", false))
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