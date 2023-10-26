package shira.chonbirth.gitrang.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ap_table")
data class APData(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val d1: String?,
    val j1: String?,
    val d2: String?,
    val j2: String?,
    val d3: String?,
    val j3: String?,
    val d4: String?,
    val j4: String?,
    val d5: String?,
    val j5: String?,
    val d6: String?,
    val j6: String?,
    val d7: String?,
    val j7: String?,
    val d8: String?,
    val j8: String?,
    val d9: String?,
    val j9: String?,
    val d10: String?,
    val j10: String?,
    val d11: String?,
    val j11: String?,
    val d12: String?,
    val j12: String?,
    val d13: String?,
    val j13: String?,
    val d14: String?,
    val j14: String?,
    val verse: String
)
