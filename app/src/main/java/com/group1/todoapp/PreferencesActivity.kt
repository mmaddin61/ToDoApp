package com.group1.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.todoapp.components.BackTitleTopAppBar
import com.group1.todoapp.data.Datasource
import com.group1.todoapp.ui.TaskDetailUiState
import com.group1.todoapp.ui.TaskDetailViewModel
import com.group1.todoapp.ui.theme.ToDoAppTheme

class PreferencesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TaskDetailViewModel = viewModel()
            viewModel.updateDarkModePreference(Datasource.isDarkTheme(LocalContext.current))
            val uiState: TaskDetailUiState by viewModel.uiState.collectAsState()

            ToDoAppTheme(darkTheme = uiState.darkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BackTitleTopAppBar(
                            title = "Preferences",
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    PreferencesLayout(
                        isDarkMode = uiState.darkMode.value,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun PreferenceCard(
        name: String,
        isChecked: Boolean,
        onCheckedChange: ((Boolean) -> Unit)? = {}
    ) {
        ElevatedCard(
            //border = BorderStroke(width = 1.dp, color = Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .padding(top = 10.dp, start = 50.dp, end = 50.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }

    @Composable
    fun PreferencesLayout(
        isDarkMode: Boolean,
        modifier: Modifier = Modifier
    ) {
        val viewModel: TaskDetailViewModel = viewModel()
        val context = LocalContext.current

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PreferenceCard(
                name = "Dark Mode",
                isChecked = isDarkMode,
                onCheckedChange = {
                    viewModel.onDarkModePreferenceChange(!isDarkMode, context)
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreferencesPreview() {
        ToDoAppTheme {
            PreferencesLayout(false)
        }
    }
}