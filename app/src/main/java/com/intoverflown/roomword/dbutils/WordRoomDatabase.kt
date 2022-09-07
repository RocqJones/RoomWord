package com.intoverflown.roomword.dbutils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.intoverflown.roomword.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Step 4. Implement the Room database
 * - Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 * - Room uses the DAO to issue queries to its database.
 * - By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main thread.
 * When Room queries return Flow, the queries are automatically run asynchronously on a background thread.
 */
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao() : WordDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        // To launch a coroutine you need a CoroutineScope
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : WordRoomDatabase {
            // If the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

    /**
     * 10. custom implementation of the RoomDatabase.Callback().
     * - Gets a CoroutineScope as constructor parameter & override the onCreate method to populate the database.
     * - Add the callback to the database build sequence right before calling .build() on the Room.databaseBuilder()
     */
    class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        private suspend fun populateDatabase(wordDao: WordDao) {
            // delete content
            wordDao.deleteAll()

            // Add sample words.
            val w = Word(1, "Jones Mbindyo")
            wordDao.insert(w)
        }
    }
}