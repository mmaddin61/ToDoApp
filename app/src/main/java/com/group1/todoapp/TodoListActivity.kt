package com.group1.todoapp

import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group1.todoapp.components.TitleText
import com.group1.todoapp.ui.theme.ToDoAppTheme

class TodoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todoData = TodoData("", mutableListOf<TaskData>())

        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    TodoListLayout(todoData, {})
                }
            }
        }
    }

    @Composable
    fun InputField(hintText: String, text: String, onTextChange: (String) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Label Text
            Text(
                text = hintText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )

            // Text Input Field
            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f) // Makes TextField take the remaining space
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }

    @Composable
    fun DisplayTask(task:TaskData) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Label Text
            TextField (
                value = task.title,
                onValueChange = { /*task.title = it TODO*/ } ,
                modifier = Modifier
                    .weight(1f) // Makes TextField take the remaining space
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )

            // Text Input Field
            TextField(
                value = task.description,
                onValueChange = { /*task.description = it TODO*/ },
                modifier = Modifier
                    .weight(1f) // Makes TextField take the remaining space
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }

    @Composable
    fun TodoListLayout(todoData : TodoData, confirmTodo : () -> Unit) {

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
                InputField("Title", todoData.title, { todoData.title = it })

                todoData.tasks.forEach() {
                    task -> DisplayTask(task)
                }

                Button(
                    onClick = { /*todoData.tasks.add(TaskData("task ${todoData.tasks.count()}", "", false)) TODO*/ },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Add Task")
                }
                Button(
                    onClick = confirmTodo,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Confirm")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TodoListPreview() {
        val todoData = TodoData("", mutableListOf<TaskData>())
        ToDoAppTheme {
            TodoListLayout(todoData, {})
        }
    }
}