package shira.chonbirth.gitrang.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import shira.chonbirth.gitrang.data.models.*

@Dao
interface MainDatabaseDao {

    /////////////////
    ///   LIST   ///
    ////////////////

    // ADD TO LIST
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MainListData::class)
    suspend fun addToList(data: MainListData)

    // READ LIST
    @Query("SELECT * FROM list_table ORDER BY id ASC")
    fun getMainList(): Flow<List<MainListData>>

    // LIST COUNT
    @Query("SELECT COUNT(id) FROM list_table")
    fun getListCount(): Flow<Int>

    // ADD/REMOVE BOOKMARK
    @Query("UPDATE list_table SET bookmark = :bookmark WHERE id = :gitId")
    suspend fun updateBookmark(gitId: Int, bookmark: Boolean)

    // FILTER LIST
    @Query("SELECT * FROM list_table WHERE id LIKE :searchQuery OR title LIKE :searchQuery OR english LIKE :searchQuery OR english1 LIKE :searchQuery OR category LIKE :searchQuery")
    fun filterList(searchQuery: String): Flow<List<MainListData>>

    /////////////////
    ///   MAIN   ///
    ////////////////

    // ADD TO CONTENT
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MainContentData::class)
    suspend fun addToContent(data: MainContentData)

    // READ CONTENT
    @Query("SELECT * FROM main_table WHERE id=:gitId")
    fun getSelectedGit(gitId: Int): Flow<MainContentData>


    // GROUP CONTENT
    @Query("SELECT DISTINCT category FROM list_table")
    fun groupList(): Flow<List<String>>

    /////////////////
    /// BOOKMARK ///
    ////////////////

    // ADD TO BOOKMARK
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = BookmarkListData::class)
    suspend fun addBookmark(bookmark: BookmarkListData)

    // GET COUNT
    @Query("SELECT COUNT(id) FROM bookmark_table")
    fun getBookmarkCount(): Flow<Int>

    // LIST BOOKMARK
    @Query("SELECT * FROM bookmark_table")
    fun getBookmarkList(): Flow<List<BookmarkListData>>

    // ALL BOOKMARK IDs
    @Query("SELECT id FROM bookmark_table")
    fun bookmarkIds(): Flow<List<Int>>

    // DELETE BOOKMARK
    @Delete(entity = BookmarkListData::class)
    suspend fun deleteBookmark(bookmark: BookmarkListData)

    // DELETE ALL BOOKMARK
    @Query("DELETE FROM bookmark_table")
    suspend fun deleteAllBookmark()


    ///////////////////////////////
    /// Aganchakgrike Poraiani ///
    /////////////////////////////

    // LIST AP
    @Query("SELECT * FROM aplist_table")
    fun getAPList(): Flow<List<APListData>>

    // READ CONTENT
    @Query("SELECT * FROM ap_table WHERE id=:apId")
    fun getSelectedAP(apId: Int): Flow<APData>
}