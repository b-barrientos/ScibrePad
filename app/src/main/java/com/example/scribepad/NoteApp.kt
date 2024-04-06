package com.example.scribepad

import android.app.Application
import androidx.room.Room
import com.example.scribepad.data.database.NoteDatabase
import com.pluto.Pluto
import com.pluto.plugin.Plugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher
import com.pluto.plugins.rooms.db.PlutoRoomsDatabasePlugin
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {

    private lateinit var database: NoteDatabase

    override fun onCreate() {
        super.onCreate()

        //Initialize Pluto
        Pluto.Installer(this)
            .addPlugin(PlutoRoomsDatabasePlugin())
            .addPlugin(PlutoLoggerPlugin())
            .install()

        //Initialize Room Database
        database = Room.databaseBuilder(
            applicationContext, NoteDatabase::class.java,
            "note_database"
        ).build()

        //Start watching Database in Pluto for Debugging
        PlutoRoomsDBWatcher.watch("note_database", NoteDatabase::class.java)
    }
}