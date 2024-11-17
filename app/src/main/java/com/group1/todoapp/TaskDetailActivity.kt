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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group1.todoapp.components.TitleText
import com.group1.todoapp.ui.theme.ToDoAppTheme

class TaskDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskDetailLayout()
                }
            }
        }
    }

    @Composable
    fun TaskCard(name: String, isCompleted: Boolean) {
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
                    checked = isCompleted,
                    onCheckedChange = {/*TODO*/},
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                var decoration: TextDecoration? = null
                if (isCompleted) {
                    decoration = TextDecoration.LineThrough
                }
                Text(
                    text = name,
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
    fun TaskDetailLayout() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* Title */
            Divider(color = Color.Black, modifier = Modifier.padding(top = 25.dp))
            TitleText(text = "Test List")
            Divider(color = Color.Black, modifier = Modifier.padding(bottom = 25.dp))

            Column(
                modifier = Modifier.padding(start = 50.dp, end = 50.dp)
            ) {
                TaskCard(name = "Task 1", isCompleted = false)
                TaskCard(name = "Task 2", isCompleted = false)
                TaskCard(name = "Task 3", isCompleted = true)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TaskDetailPreview() {
        ToDoAppTheme {
            TaskDetailLayout()
        }
    }
}