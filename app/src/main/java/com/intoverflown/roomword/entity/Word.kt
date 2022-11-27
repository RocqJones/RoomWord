package com.intoverflown.roomword.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Step 1. Create Entity
 * @PrimaryKey Every entity needs a primary key. To keep things simple, each word acts as its own primary key.
 * @ColumnInfo(name = "word") Specifies the name of the column in the table if you want it to be different
 * from the name of the member variable. This names the column "word".
 */
@Entity(tableName = "word_table")
class Word(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "word") val word: String
)
