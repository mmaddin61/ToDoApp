package com.group1.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.components.BackTitleTopAppBar
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme

class TaskDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TaskDetailViewModel = viewModel() // Get the ViewModel
            //viewModel.updateDarkModePreference(Datasource.isDarkTheme(LocalContext.current))
            val uiState: TaskDetailUiState by viewModel.uiState.collectAsState() // Get the UI state
            val context = LocalContext.current
            val darkMode by Datasource.isDarkTheme(context).collectAsState(initial = false)
            viewModel.updateDarkModePreference(darkMode ?: false)

            ToDoAppTheme(darkTheme = uiState.darkMode) {
                val toDoDataIndex = intent.getIntExtra("toDoListIndex", 0) // Get the index of the to-do list to be displayed
                val toDoData: TodoData = UserDataFactory.GetTodo(toDoDataIndex) // Get to-do list data using the index

                viewModel.updateTaskDetailState(toDoData) // Store the to-do list data in the UI state

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BackTitleTopAppBar(
                            title = toDoData.title,
                            onBackClick = {
                                finish()
                            }
                        )
                    }
                ) { innerPadding ->
                    TaskDetailLayout(
                        toDoData = uiState.todoData,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun TaskCard(
        title : String, finished : MutableState < Boolean >
    ) {
        val viewModel: TaskDetailViewModel = viewModel()
        ElevatedCard(
            //border = BorderStroke(width = 1.dp, color = Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = finished.value,
                    onCheckedChange = {
                        finished.value = !finished.value;
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                var decoration: TextDecoration? = null
                if (finished.value) {
                    decoration = TextDecoration.LineThrough
                }
                Text(
                    text = title,
                    fontStyle = FontStyle.Italic,
                    textDecoration = decoration,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                TextButton(
                    onClick = { /*TODO*/ },
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier
                        .padding(end = 10.dp, start = 0.dp)
                ) {
                    Text("Edit")
                }
            }
        }
    }

    @Composable
    fun TaskCardList(id: Int) {
        val todoData = remember { mutableStateOf(UserDataFactory.GetTodo(id)) }
        val tasks = remember { todoData.value.tasks.map { it }.toMutableList() }

        LazyColumn(
            modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 10.dp)
        ) {
            items(todoData.value.tasks) { task ->
                val taskFinish = remember { mutableStateOf(task.finished) }
                TaskCard(task.title, taskFinish)

                task.finished = taskFinish.value

                todoData.value = TodoData(todoData.value.id, todoData.value.title, tasks.toMutableList())
            }
        }
    }

    @Composable
    fun TaskDetailLayout(
        toDoData: TodoData,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskCardList(toDoData.id)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TaskDetailPreview() {
        ToDoAppTheme {
            val toDoData: TodoData = TodoData(
                id = 0,
                title = "Test List",
                tasks = mutableListOf(
                    TaskData(title = "Task 1", description = "Task 1 description", finished = false),
                    TaskData("Task 2", "Task 2 description", false),
                    TaskData("Task 3", "Task 3 description", false),
                    TaskData("Task 4", "Task 4 description", true)
                )
            )
            TaskDetailLayout(toDoData)
        }
    }
}