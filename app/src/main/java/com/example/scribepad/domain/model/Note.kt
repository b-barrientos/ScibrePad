package com.example.scribepad.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scribepad.ui.theme.*
import java.sql.Date

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val noteTitle: String,
    val noteContent: String,
    val noteColor: Int,
    val dateLastModified: Date?
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}
