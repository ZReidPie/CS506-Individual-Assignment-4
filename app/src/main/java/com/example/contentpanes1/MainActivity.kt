package com.example.contentpanes1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // For layout components
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.* // For state management
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleContentPanesApp()
        }
    }
}

@Composable
fun SimpleContentPanesApp() {
    val windowInfo = calculateCurrentWindowInfo()
    val items = listOf("Task 1", "Task 2", "Task 3", "Task 4") // sample tasks
    var selectedItem by remember { mutableStateOf<String?>(null) }

    if (windowInfo.isWideScreen) {
        // Two-pane layout for wide screens, one for the task list
        // the other for the task details
        Row(modifier = Modifier.fillMaxSize()) {
            TaskList(items = items, onItemSelected = { selectedItem = it }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            TaskDetailPane(task = selectedItem, modifier = Modifier.weight(1f))
        }
    } else {
        // Single-pane layout for narrow screens
        if (selectedItem == null) {
            TaskList(items = items, onItemSelected = { selectedItem = it }, modifier = Modifier.fillMaxSize())
        } else {
            TaskDetailPane(task = selectedItem, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TaskList(items: List<String>, onItemSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    // Tasks displayed in a column in the task list pane
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // List Title
        Text(
            text = "Tasks",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // List Items
        items.forEach { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected(item) }
                    .padding(8.dp),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TaskDetailPane(task: String?, modifier: Modifier = Modifier) {
    // Task details pane used when the user selects a particular task
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (task != null) {
            // Task Detail
            Text(
                text = "Details for $task",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This is the detailed description of $task.",
                fontSize = 16.sp
            )
        } else {
            // No task selected
            Text(
                text = "No task selected",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun calculateCurrentWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    // Set a breakpoint for wide vs narrow screens (600dp is commonly used)
    val isWideScreen = screenWidth >= 600

    return WindowInfo(
        isWideScreen = isWideScreen
    )
}

data class WindowInfo(
    val isWideScreen: Boolean
)

@Preview(showBackground = true)
@Composable
fun SimpleContentPanesAppPreview() {
    SimpleContentPanesApp()
}