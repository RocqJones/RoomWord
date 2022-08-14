package com.intoverflown.roomword.repository

import androidx.annotation.WorkerThread
import com.intoverflown.roomword.dbutils.WordDao
import com.intoverflown.roomword.entity.Word
import kotlinx.coroutines.flow.Flow

/**
 * Step 5. Create a repository
 * - A repository class abstracts access to multiple data sources(example: network, Dao).
 * - Why use a Repository? A Repository manages queries and allows you to use multiple backends.
 * - Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
 * - The "suspend" modifier tells the compiler that this needs to be called from a coroutine or another suspending function.
 */
class WordRepository(private val wordDao: WordDao) {
    /**
     * Room executes all queries on a separate thread.
     * Observed Flow will notify the observer when the data has changed.
     */

    val allWords : Flow<List<Word>> = wordDao.getAlphabetizedWords()

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to implement
     * anything else to ensure we're not doing long running database work off the main thread.
     */
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}