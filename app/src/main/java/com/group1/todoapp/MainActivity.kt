package com.group1.todoapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.components.HeadingText
import com.group1.todoapp.components.TitleTopAppBar
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.data.PreferencesRepository
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserDataFactory.SaveUserData(filesDir.path)
        UserDataFactory.LoadUserData(filesDir.path)

        enableEdgeToEdge()
        setContent {
            val viewModel: TaskDetailViewModel = viewModel()
            viewModel.updateDarkModePreference(Datasource.isDarkTheme(LocalContext.current))
            val uiState: TaskDetailUiState by viewModel.uiState.collectAsState()

            ToDoAppTheme(darkTheme = uiState.darkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TitleTopAppBar()
                    }
                ) { innerPadding ->
                    ToDoMenuLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        UserDataFactory.SaveUserData(filesDir.path)
    }

    @Composable
    fun ToDoListCard(
        toDoData: TodoData
    ) {
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
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = {
                        val intent = Intent(this@MainActivity, TaskDetailActivity::class.java).apply {
                            putExtra("toDoListIndex", toDoData.id)
                        }
                        try {
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Log.e("MainActivity", "Failed to start TaskDetailActivity.")
                        }
                    },
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier
                        .padding(end = 5.dp, start = 10.dp)
                ) {
                    Text("View")
                }
                Text(
                    text = toDoData.title,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }

    @Composable
    fun ToDoListCardList(
        toDoDataList: List<TodoData>
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        ) {
            items(toDoDataList) {toDoList ->
                ToDoListCard(toDoData = toDoList)
            }
        }
    }

    @Composable
    fun ToDoMenuLayout(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* Buttons */
            Column(
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val intent = Intent(this@MainActivity, TodoListActivity::class.java)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "New To-Do List")
                }
                Button(
                    onClick = {
                        val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 0.dp, start = 50.dp, end = 50.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Options")
                }
            }

            /* List of to-do lists */
            Divider(modifier = Modifier.padding(top = 20.dp, start = 0.dp, end = 0.dp))
            HeadingText(text = "Lists")

            ToDoListCardList(toDoDataList = UserDataFactory.GetTodoList())

            // Add the new Help button
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val intent = Intent(this@MainActivity, HelpActivity::class.java)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 50.dp, end = 50.dp)
                        .fillMaxWidth()
                ) {
                    // Add the image "help.jpg" to this button
                    //Image(painter = painterResource(id = R.drawable.help), contentDescription = "Help Image")
                    Text(text = "Help")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ToDoMenuPreview() {
        ToDoAppTheme {
            ToDoMenuLayout()
        }
    }
}

