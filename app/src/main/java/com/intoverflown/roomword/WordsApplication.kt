package com.intoverflown.roomword

import android.app.Application
import com.intoverflown.roomword.dbutils.WordRoomDatabase
import com.intoverflown.roomword.repository.WordRepository

/**
 * Step 9. Instantiate the repository and the database
 * - repository and the database will be retrieved from the Application whenever they're needed, rather than constructed every time.
 * - Using by lazy so the database and the repository are only created when they're needed rather than when the application starts
 */
class WordsApplication : Application() {
    val database by lazy { WordRoomDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}