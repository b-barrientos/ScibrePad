package com.example.scribepad.presentation.notescreen

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scribepad.R
import com.example.scribepad.domain.model.Note
import com.example.scribepad.presentation.navigation.Screen
import com.example.scribepad.ui.theme.Typography
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    addNote: Boolean,
    noteId: Int,
    noteColor: Int,
    viewModel: NoteScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val note by viewModel.note.collectAsState()
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    var noteTitle by remember {
        mutableStateOf("")
    }

    var noteContent by remember {
        mutableStateOf("")
    }
    var noteColorResult by remember {
        mutableIntStateOf(noteColor)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getNote(noteId)
        viewModel.navigate.collectLatest {
            when (it) {
                true -> {
                    navController.navigate(Screen.HomeScreen.route) {
                        navController.popBackStack()
                    }
                }

                false -> {}
            }
        }
    }

    LaunchedEffect(note) {
        if (note != null) {
            noteTitle = note?.noteTitle ?: ""
            noteContent = note?.noteContent ?: ""
            isLoading = false
        }
    }

    if (isLoading && !addNote) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Loading")
            }
        }
    } else {
        Scaffold(
            topBar = {
                androidx.compose.material3.TopAppBar(
                    title = {
                        Text(
                            text = if (addNote) stringResource(R.string.add_note) else stringResource(
                                R.string.edit_note
                            ),
                            style = Typography.titleLarge
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(noteColorResult),
                        titleContentColor = Color.Black,
                    ), actions = {
                        IconButton(onClick = {
                            if (noteTitle.isEmpty() || noteContent.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.note_title_and_note_content_cannot_be_empty_please_fill_them_in),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                viewModel.insertOrUpdateNote(
                                    addNote,
                                    noteTitle,
                                    noteContent,
                                    noteColorResult
                                )
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_check_24),
                                contentDescription = "Create a new note",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }, content = { values ->
                Column(
                    modifier = modifier
                        .padding(values)
                        .fillMaxSize()
                        .background(Color(noteColorResult)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Note.noteColors.forEach { color ->
                            val colorInt = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .shadow(15.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(color = color)
                                    .border(
                                        width = 3.dp,
                                        color = if (noteColorResult == colorInt) {
                                            Color.Black
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        scope.launch {
                                            noteBackgroundAnimatable.animateTo(
                                                targetValue = Color(colorInt),
                                                animationSpec = tween(durationMillis = 500)
                                            )
                                        }
                                        noteColorResult = colorInt
                                    }
                            ) {}
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(modifier = modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                        value = noteTitle,
                        maxLines = 1,
                        singleLine = true,
                        onValueChange = { noteTitle = it },
                        textStyle = Typography.titleMedium,
                        placeholder = { Text(text = "Enter Title") }
                        )

                    OutlinedTextField(modifier = modifier
                        .padding(14.dp)
                        .fillMaxSize(),
                        value = noteContent,
                        onValueChange = { noteContent = it },
                        placeholder = { Text(text = "Enter Content") })
                }
            })
    }
}