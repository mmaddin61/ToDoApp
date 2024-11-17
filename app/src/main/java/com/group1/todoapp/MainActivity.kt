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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group1.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoMenuLayout()
                }
            }
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(10.dp)
    )
}

@Composable
fun HeadingText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(10.dp)
    )
}

@Composable
fun ToDoListCard(
    name: String
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
                onClick = { /*TODO*/ },
                //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .padding(end = 5.dp, start = 10.dp)
            ) {
                Text("View")
            }
            Text(
                text = name,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

        }
    }
}

@Composable
fun ToDoMenuLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* Title */
        Divider(color = Color.Black, modifier = Modifier.padding(top = 25.dp))
        TitleText(text = stringResource(id = R.string.app_name))
        Divider(color = Color.Black, modifier = Modifier.padding(bottom = 25.dp))

        /* Buttons */
        Column(
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "New To-Do List")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Options")
            }
        }

        /* List of to-do lists */
        Divider(modifier = Modifier.padding(top = 25.dp, start = 0.dp, end = 0.dp))
        HeadingText(text = "Lists")
        Column(
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        ) {
            ToDoListCard(name = "Test List")
            ToDoListCard(name = "Shopping List")
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