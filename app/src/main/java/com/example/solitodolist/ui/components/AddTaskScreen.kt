package com.example.solitodolist.ui.components

import android.annotation.SuppressLint
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
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.TextField
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import java.time.LocalDate
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskScreen(toNavigate:()->Unit)
{
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var creationDate by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var istitleError by remember { mutableStateOf(false) }
    var isdescriptionError by remember { mutableStateOf(false) }
    var iscreationDateError by remember { mutableStateOf(false) }
    var isdueDateError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
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
            singleLine = true,
            isError = istitleError,
            onValueChange = {
                title = it
            },
            label = { Text("Title") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                    istitleError = if(title == "") true else false
                }
            )
        )
        OutlinedTextField(
            value = description,
            singleLine = true,
            onValueChange = {
                description = it
            },
            isError = isdescriptionError,
            label = { Text("description") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                    isdescriptionError = if (description == "") true else false
                }
            )
        )
        DatePickerComposable("Start Date",true,
            onDateSelected = { selected ->
                creationDate = selected
            },
            isError = iscreationDateError
        )
        DatePickerComposable("End Date",
            onDateSelected = { selected ->
                dueDate = selected
            },
            isError = isdueDateError
        )
        Button(onClick = {
            if(creationDate == "") iscreationDateError = true
            if(dueDate == "") isdueDateError = true
            if(title == "" ) istitleError = true
            if(description == "") isdescriptionError = true
            if(
                title != "" &&
                description != "" &&
                creationDate != "" &&
                dueDate != ""
            )
            {
                val task = Task(
                    id = null,
                    title = title,
                    description = description,
                    creationDate = creationDate,
                    dueDate = dueDate,
                    tasklist_id = 1
                )
                TaskOpertaions().store(task)
                toNavigate()
            }


        },modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text("Add task")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun DatePickerComposable(
    label: String = "Sélectionnez une date",
    InitialValue:Boolean=false,
    isError:Boolean = false,
    onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    val initialValue = if (InitialValue){String.format("%04d-%02d-%02d", year, month, day)} else ""
    val selectedDate = remember { mutableStateOf(initialValue) }
    val showDialog = remember { mutableStateOf(false) }
    if (selectedDate.value != initialValue)
    {
        year = LocalDate.parse(selectedDate.value).year
        month = LocalDate.parse(selectedDate.value).monthValue - 1
        day = LocalDate.parse(selectedDate.value).dayOfMonth
    }
    Log.i("Show",showDialog.value.toString())
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
        ).apply {
                setOnCancelListener {
                    showDialog.value = false
                }
        }.show()
    }
    OutlinedTextField(
        value = selectedDate.value,
        onValueChange = {},
        readOnly = true,
        isError = isError,
        label = { Text(label) },
        modifier = Modifier.pointerInput(selectedDate) {
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

