package com.intoverflown.roomword.dbutils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intoverflown.roomword.entity.Word
import kotlinx.coroutines.flow.Flow

/**
 * Step 2. DAO (data access object).
 * - The DAO must be an interface or abstract class.
 * - By default, all queries must be executed on a separate thread.
 * - Room has Kotlin coroutines support. This allows your queries to be annotated with the suspend modifier
 * and then called from a coroutine or from another suspension function.
 * - @Insert: The selected onConflict strategy ignores a new word if it's exactly the same as one already in the list.
 *
 * Step 3. Observing database changes (with kotlinx-coroutines Flow)
 * - When data changes, you usually want to take some action, such as displaying the updated data in the UI.
 * - To observe data changes you will use "Flow" from kotlinx-coroutines. Use a return value of type Flow in your
 * method description, and Room generates all necessary code to update the Flow when the database is updated.
 * - A Flow is an async sequence of values.
 * - Flow produces values one at a time (instead of all at once) that can generate values from async
 * operations like network requests, database calls, or other async code.
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}