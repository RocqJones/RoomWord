package com.intoverflown.roomword.dbutils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.intoverflown.roomword.entity.Word

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

        fun getDatabase(context: Context) : WordRoomDatabase {
            // If the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}