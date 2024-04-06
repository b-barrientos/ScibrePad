package com.example.scribepad.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scribepad.R
import com.example.scribepad.presentation.navigation.Screen
import com.example.scribepad.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val deleted by viewModel.deleted.collectAsState()

    LaunchedEffect(deleted) {
        viewModel.getAllNotes()
    }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(text = stringResource(R.string.your_notes), style = Typography.titleLarge)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ), actions = {
                    IconButton(onClick = { navController.navigate(Screen.NoteScreen.route + "?note=${null}&addNote=${true}") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_post_add_24),
                            contentDescription = stringResource(R.string.create_a_new_note),
                            tint = Color.White
                        )
                    }
                }
            )
        }, content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.notes.isEmpty()) {
                    Text(text = stringResource(R.string.no_notes_found), color = Color.White, style = Typography.displayMedium)
                } else {
                    LazyColumn(modifier.fillMaxSize()) {
                        items(state.notes) { note ->
                            Card(
                                modifier = modifier.padding(8.dp),
                                onClick = { navController.navigate(Screen.NoteScreen.route + "?noteId=${note.id}&addNote=${false}&noteColor=${note.noteColor}") },
                                colors = CardDefaults.cardColors(containerColor = Color(note.noteColor))
                            ) {
                                Column(
                                    modifier = modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = note.noteTitle,
                                            maxLines = 1,
                                            style = Typography.titleMedium
                                        )
                                        IconButton(onClick = { viewModel.deleteNote(note) }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_delete_24),
                                                contentDescription = stringResource(R.string.delete_note)
                                            )
                                        }
                                    }
                                    Text(
                                        text = note.noteContent,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        style = Typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
}
