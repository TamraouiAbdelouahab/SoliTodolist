package com.example.solitodolist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@Composable
fun TaskScreen(text:String = "", onNextButtonClicked: () -> Unit = {},modifier:Modifier = Modifier)
{
    Column {
        //Titre
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onNextButtonClicked ) {
          Text(text = "Click me")
        }
    }


}