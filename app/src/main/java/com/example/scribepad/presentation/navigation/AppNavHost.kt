package com.example.scribepad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scribepad.presentation.homescreen.HomeScreen
import com.example.scribepad.presentation.notescreen.NoteScreen

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object NoteScreen: Screen("note_screen")
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route ){
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.NoteScreen.route+"?noteId={noteId}&addNote={addNote}&noteColor={noteColor}", arguments = listOf(
            navArgument("noteId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("addNote") {
                type = NavType.BoolType
                defaultValue = false
            },
            navArgument("noteColor") {
                type = NavType.IntType
                defaultValue = -1
            }
        )) {
            val noteId = it.arguments?.getInt("noteId") ?: -1
            val addNote = it.arguments?.getBoolean("addNote") ?: false
            val noteColor = it.arguments?.getInt("noteColor") ?: -1
            NoteScreen(navController = navController, addNote = addNote, noteId = noteId, noteColor = noteColor)
        }
    }
}