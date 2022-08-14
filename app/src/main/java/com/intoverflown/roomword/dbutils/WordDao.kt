package com.intoverflown.roomword.dbutils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intoverflown.roomword.entity.Word

/**
 * Step 2. DAO (data access object).
 * The DAO must be an interface or abstract class.
 * By default, all queries must be executed on a separate thread.
 * Room has Kotlin coroutines support. This allows your queries to be annotated with the suspend modifier
 * and then called from a coroutine or from another suspension function.
 * @Insert: The selected onConflict strategy ignores a new word if it's exactly the same as one already in the list.
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}