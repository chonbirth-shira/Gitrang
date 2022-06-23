package shira.chonbirth.gitrang.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_table")
data class MainListData(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val category: String,
    val title: String,
    val english: String?,
    val english1: String?,
    val bookmark: Boolean
)
