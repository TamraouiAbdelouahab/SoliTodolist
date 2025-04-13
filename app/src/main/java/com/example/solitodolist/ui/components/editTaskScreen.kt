package com.example.solitodolist.ui.components
import android.annotation.SuppressLint
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
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.solitodolist.Repositories.TaskOpertaions
import com.example.solitodolist.data.Task
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun editTaskScreen(navController: NavController,taskId: Int)
{
    var task by remember { mutableStateOf<Task>(
        Task(id = null,title = "", description = "", creationDate = "", dueDate = "", tasklist_id = 1)
    ) }
    var Download by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        task = TaskOpertaions().show(taskId)!!
        Download = true
    }
    if (Download)
    {
        var title by remember { mutableStateOf(task.title) }
        var description by remember { mutableStateOf(task.description) }
        var creationDate by remember { mutableStateOf(task.creationDate) }
        var dueDate by remember { mutableStateOf(task.dueDate) }
        var istitleError by remember { mutableStateOf(false) }
        var isdescriptionError by remember { mutableStateOf(false) }
        var iscreationDateError by remember { mutableStateOf(false) }
        var isdueDateError by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        var showDialog by remember { mutableStateOf(false) }
        if(showDialog)
        {
            AlertDialogDelete(
                onDismissRequest = {
                    showDialog = false
                },
                onConfirmation = {
                    task.id?.let { TaskOpertaions().delete(it) }
                    navController.navigate(Routes.Tasks.name)
                }
                )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "edit task",
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
                DatePicker("Start Date",task.creationDate,
                    onDateSelected = { selected ->
                        creationDate = selected
                    },
                    isError = iscreationDateError
                )
                DatePicker("End Date",
                    task.dueDate,
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
                        task = task.copy(
                            title = title,
                            description = description,
                            creationDate = creationDate,
                            dueDate = dueDate
                        )
                        Log.i("taskUpdate",task.creationDate)
                        TaskOpertaions().update(task)
                        navController.navigate(Routes.Tasks.name)
                    }
                },modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text("edit task")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.End
                ) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                ) {
                    Icon(Icons.Filled.Delete, "Floating action button.")
                }
            }

        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun DatePicker(
    label: String = "Sélectionnez une date",
    InitialValue:String="",
    isError:Boolean = false,
    onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val selectedDate = remember { mutableStateOf(InitialValue) }
    val year = LocalDate.parse(selectedDate.value).year
    val month = LocalDate.parse(selectedDate.value).monthValue - 1
    val day = LocalDate.parse(selectedDate.value).dayOfMonth
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
@Composable
fun AlertDialogDelete(
    onDismissRequest: () -> Unit =  {},
    onConfirmation: () -> Unit = {},
    dialogTitle: String = "Are you sure ? ",
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

