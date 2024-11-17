package com.group1.todoapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CommonUi {

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