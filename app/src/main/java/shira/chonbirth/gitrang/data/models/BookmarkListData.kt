package shira.chonbirth.gitrang.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_table")
data class BookmarkListData(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val category: String
)