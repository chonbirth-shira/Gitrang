package shira.chonbirth.gitrang.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_table")
data class MainContentData (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val category: String,
    val title: String,
    val english: String?,
    val english1: String?,
    val verse: String,
    val git_key: String?,
    val para1: String?,
    val chorus: String?,
    val para2: String?,
    val para3: String?,
    val para4: String?,
    val para5: String?,
    val para6: String?,
    val para7: String?,
    val para8: String?
)