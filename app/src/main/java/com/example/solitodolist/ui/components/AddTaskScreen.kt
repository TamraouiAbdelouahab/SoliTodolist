package com.example.solitodolist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation

import androidx.compose.material3.TextField
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar


@Composable
fun AddTaskScreen()
{
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var creationDate by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Add task",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("description") }
        )
        OutlinedTextField(
            value = creationDate,
            onValueChange = { creationDate = it },
            label = { Text("Start date") }
        )
        OutlinedTextField(
            value = creationDate,
            onValueChange = { creationDate = it },
            label = { Text("Start date") }
        )
        DatePickerComposable { date ->
            Log.d("DateSélectionnée", date)
        }
        Button(onClick = {  },modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text("Add task")
        }
    }
}


@Composable
fun DatePickerComposable(
    label: String = "Sélectionnez une date",
    onDateSelected: (String) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val selectedDate = remember { mutableStateOf(String.format("%04d-%02d-%02d", year, month, day)) }
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DatePickerDialog(
            context,
            { _, y, m, d ->
                val formattedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
                selectedDate.value = formattedDate
                onDateSelected(formattedDate)
                showDialog.value = false
            },
            year, month, day
        ).show()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog.value = true
                Log.i("Rice", showDialog.value.toString())
            }
    )
    {
        OutlinedTextField(
            value = selectedDate.value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth().pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showDialog.value = true
                    }
                }
            }
        )
    }

}

