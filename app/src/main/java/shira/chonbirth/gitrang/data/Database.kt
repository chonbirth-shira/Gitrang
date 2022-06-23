package shira.chonbirth.gitrang.data

import androidx.room.Database
import androidx.room.RoomDatabase
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.data.models.MainContentData
import shira.chonbirth.gitrang.data.models.MainListData

@Database(entities = [MainListData::class, MainContentData::class, BookmarkListData::class], version = 1, exportSchema = true)
abstract class Database: RoomDatabase() {
    abstract fun databaseDao(): MainDatabaseDao
}