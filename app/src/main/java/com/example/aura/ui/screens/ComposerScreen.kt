package com.example.aura.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aura.AuraApplication
import com.example.aura.ui.theme.AuraTheme
import com.example.aura.ui.viewmodels.ComposerViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposerScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ComposerViewModel = if (LocalInspectionMode.current) {
        // Dummy for preview
        viewModel()
    } else {
        viewModel(
            factory = ComposerViewModel.Factory(
                (LocalContext.current.applicationContext as AuraApplication).repository
            )
        )
    }
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.resetFields()
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = viewModel.date
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.date = datePickerState.selectedDateMillis ?: viewModel.date
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "New Entry",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.saveEntry(onNavigateBack) },
                        enabled = viewModel.isInputValid
                    ) {
                        Text(
                            "Save",
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.isInputValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                placeholder = { 
                    Text(
                        "Entry Title", 
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Rounded.DateRange, 
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = formatDateShort(viewModel.date),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            MoodSelector(
                selectedMood = viewModel.mood,
                onMoodSelected = { viewModel.mood = it }
            )

            OutlinedTextField(
                value = viewModel.content,
                onValueChange = { viewModel.content = it },
                placeholder = { Text("What's on your mind?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodSelector(
    selectedMood: String,
    onMoodSelected: (String) -> Unit
) {
    val moods = listOf("Happy", "Calm", "Reflective", "Grateful", "Anxious", "Sad")
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "How are you feeling?",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moods.forEach { mood ->
                val isSelected = selectedMood == mood
                FilterChip(
                    selected = isSelected,
                    onClick = { onMoodSelected(mood) },
                    label = { Text(mood) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        selectedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}

fun formatDateShort(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.getDefault())
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneOffset.UTC)
        .format(formatter)
}

@Preview(showBackground = true)
@Composable
fun MoodSelectorPreview() {
    AuraTheme {
        Box(modifier = Modifier.padding(24.dp)) {
            MoodSelector(selectedMood = "Happy", onMoodSelected = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposerScreenPreview() {
    AuraTheme {
        // We can't easily preview the whole screen because of the ViewModel
        // but we can preview the layout structure with dummy data
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "New Entry",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            MoodSelector(selectedMood = "Calm", onMoodSelected = {})
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("What's on your mind?") },
                modifier = Modifier.fillMaxWidth().height(200.dp),
                shape = MaterialTheme.shapes.extraLarge
            )
        }
    }
}
