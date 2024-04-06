package com.example.scribepad.di

import android.content.Context
import androidx.room.Room
import com.example.scribepad.data.database.NoteDao
import com.example.scribepad.data.database.NoteDatabase
import com.example.scribepad.data.repository.NoteRepositoryImpl
import com.example.scribepad.domain.repository.NoteRepository
import com.example.scribepad.domain.usecase.DeleteNoteUseCase
import com.example.scribepad.domain.usecase.GetAllNotesUseCase
import com.example.scribepad.domain.usecase.GetNoteByIdUseCase
import com.example.scribepad.domain.usecase.InsertNoteUseCase
import com.example.scribepad.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "note_database").build()
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(repo: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(repo: NoteRepository): GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideInsertNoteUseCase(repo: NoteRepository): InsertNoteUseCase{
        return InsertNoteUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateNoteUseCase(repo: NoteRepository): UpdateNoteUseCase {
        return UpdateNoteUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(repo: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repo)
    }

    @Provides
    fun provideDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.getNoteDao()
    }

    @Provides
    fun provideRepo(dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(dao)
    }
}