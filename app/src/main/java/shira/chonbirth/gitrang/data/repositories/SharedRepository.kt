package shira.chonbirth.gitrang.data.repositories

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import shira.chonbirth.gitrang.data.MainDatabaseDao
import shira.chonbirth.gitrang.data.models.*
import javax.inject.Inject

@ViewModelScoped
class SharedRepository @Inject constructor(private val databaseDao: MainDatabaseDao) {


    // READ AP LIST
//    val getAPList: Flow<List<APListData>> = databaseDao.getAPList()

    /////////////////
    ///   LIST   ///
    ////////////////

    // ADD TO LIST
    suspend fun addToList(data: MainListData){
        databaseDao.addToList(data)
    }

    // READ LIST
    val getList: Flow<List<MainListData>> = databaseDao.getMainList()

    // LIST COUNT
    val getListCount: Flow<Int> = databaseDao.getListCount()

    // ADD/REMOVE BOOKMARK
    suspend fun updateBookmark(gitId: Int, bookmark: Boolean){
        return databaseDao.updateBookmark(gitId = gitId, bookmark = bookmark)
    }

    // FILTER LIST
    fun searchDatabase(searchQuery: String): Flow<List<MainListData>> {
        return databaseDao.filterList(searchQuery = searchQuery)
    }

    /////////////////
    ///   MAIN   ///
    ////////////////

    // ADD TO CONTENT
    suspend fun addToContent(data: MainContentData){
        databaseDao.addToContent(data)
    }

    // READ CONTENT
    fun getSelectedItem(gitId: Int): Flow<MainContentData>{
        return databaseDao.getSelectedGit(gitId = gitId)
    }


    // GROUP LIST
    val groupList: Flow<List<String>> = databaseDao.groupList()

    /////////////////
    /// BOOKMARK ///
    ////////////////

    // ADD TO BOOKMARK
    suspend fun addBookmark(data: BookmarkListData){
        databaseDao.addBookmark(data)
    }

    // GET COUNT
    val getCount: Flow<Int> = databaseDao.getBookmarkCount()

    // LIST BOOKMARK
    val getAllBookmark: Flow<List<BookmarkListData>> = databaseDao.getBookmarkList()

    // ALL BOOKMARK IDs
    val bookmarkIds: Flow<List<Int>> = databaseDao.bookmarkIds()

    // DELETE BOOKMARK
    suspend fun deleteBookmark(bookmark: BookmarkListData){
        databaseDao.deleteBookmark(bookmark = bookmark)
    }

    // DELETE ALL BOOKMARK
    suspend fun deleteAllTask(){
        databaseDao.deleteAllBookmark()
    }

    // LIST AP
    val getAllAp: Flow<List<APListData>> = databaseDao.getAPList()

    // READ CONTENT AP
    fun getSelectedAP(apId: Int): Flow<APData>{
        return databaseDao.getSelectedAP(apId = apId)
    }

//
////    val getAllGit: Flow<List<DataModel>> = mainDao.getAllGit()
//    val getAllGit: Flow<List<ListDataModel>> = mainDao.getAllGit()
//    val getAllBookmark: Flow<List<BookmarkDataModel>> = mainDao.getAllBookmarkGit()
////    val getAllBookmarkGit: Flow<List<DataModel>> = mainDao.getAllBookmarkGit()
////    val databaseCheck: Int = mainDao.databaseCheck()
//    val getCount: Flow<Int> = mainDao.getCount()
//    val bookmarkIds: Flow<List<Int>> = mainDao.bookmarkIds()
//
////    suspend fun itemCount(gitId: Int): Flow<Int> {
////        return mainDao.itemCount(gitId = gitId)
////    }
//
////    fun itemCount(gitId: Int): Flow<Int> {
////        return mainDao.itemCount(gitId = gitId)
////    }
//
//    suspend fun updateBookmark(gitId: Int, bookmark: Boolean){
//        return mainDao.updateBookmark(gitId = gitId, bookmark = bookmark)
//    }
//
////    suspend fun add(id: Int, category: String, title: String, english: String, english1: String, verse: String){
////        mainDao.add(id, category, title, english, english1, verse)
////    }
//
//    fun getSelectedItem(gitId: Int): Flow<DataModel>{
//        return mainDao.getSelectedGit(gitId = gitId)
//    }
//
//    suspend fun addData(data: DataModel){
//        mainDao.addData(data = data)
//    }
//
//    fun searchDatabase(searchQuery: String): Flow<List<ListDataModel>> {
//        return mainDao.searchDatabase(searchQuery = searchQuery)
//    }
//
//    suspend fun addBookmark(bookmark: BookmarkDataModel){
//        mainDao.addBookmark(bookmark = bookmark)
//    }
//
//    suspend fun deleteBookmark(bookmark: BookmarkDataModel){
//        mainDao.deleteBookmark(bookmark = bookmark)
//    }
//
//    suspend fun deleteAllTask(){
//        mainDao.deleteAllBookmark()
//    }
//
//    fun common(gitId: Int): Int {
//        return mainDao.common(id = gitId)
//    }
}