package com.example.contentpanes1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // For layout components
import androidx.compose.foundation.lazy.LazyColumn
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

import androidx.compose.foundation.lazy.items // lazy columns

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
    val products = listOf(
        Product("Product A", "$100", "Product A is the most affordable and breaks easily."),
        Product("Product B", "$150", "Product B is slightly more expensive but breaks less easily."),
        Product("Product C", "$10,000", "Premium product C, Stop looking at it you know can't afford it.")
    )

    var selectedItem by remember { mutableStateOf<String?>(null) }

    if (windowInfo.isWideScreen) {
        // Two-pane layout for wide screens, one for the task list
        // the other for the task details
        Row(modifier = Modifier.fillMaxSize()) {
            TaskList(allProds = products, onItemSelected = { selectedItem = it }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            TaskDetailPane(prod = selectedItem, modifier = Modifier.weight(1f), products)
        }
    } else {
        // Single-pane layout for narrow screens
        if (selectedItem == null) {
            TaskList(allProds = products, onItemSelected = { selectedItem = it }, modifier = Modifier.fillMaxSize())
        } else {
            TaskDetailPane(prod = selectedItem, modifier = Modifier.fillMaxSize(), products)
        }
    }
}

@Composable
fun TaskList(allProds: List<Product>, onItemSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    // Tasks displayed in a column in the task list pane
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // List Title
        Text(
            text = "Products",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = modifier.padding(16.dp) // Add padding around the list
        ) {
            items(allProds) { product ->
                // Display each product's name
                Text(
                    text = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemSelected(product.name) } // Corrected the clickable behavior
                        .padding(8.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


@Composable
fun TaskDetailPane(prod: String?, modifier: Modifier = Modifier, allProds: List<Product>) {
    // Task details pane used when the user selects a particular task
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // add for loop here
        if (prod != null) {
            // Task Detail
            Text(
                text = "Details for $prod",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This is the detailed description of $prod.",
                fontSize = 16.sp
            )
        } else {
            // No task selected
            Text(
                text = "Select a product to view details.",
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

data class Product(
    val name: String,
    val cost: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun SimpleContentPanesAppPreview() {
    SimpleContentPanesApp()
}