package shira.chonbirth.gitrang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.APData
import shira.chonbirth.gitrang.data.models.APListData
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.data.models.MainContentData
import shira.chonbirth.gitrang.data.models.MainListData
import shira.chonbirth.gitrang.data.repositories.SharedRepository
import shira.chonbirth.gitrang.util.RequestState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: SharedRepository
    ): ViewModel() {

    // READ AP LIST
//    private val _allAPList = MutableStateFlow<List<APListData>>(emptyList())
//    val allAPList: StateFlow<List<APListData>> = _allAPList
//    fun getAPList(){
//        viewModelScope.launch {
//            repository.getAPList.collect {
//                _allAPList.value = it
//            }
//        }
//    }

    /////////////////
    ///  OTHERS  ///
    ////////////////
    val searchAppBarState: MutableState<Boolean> = mutableStateOf(false)
    val searchTextState: MutableState<String> = mutableStateOf("")
    var openDeleteDialog: MutableState<Boolean> = mutableStateOf(false)
    var openItemDeleteDialog: MutableState<Boolean> = mutableStateOf(false)
    val bookmark: MutableState<BookmarkListData> = mutableStateOf(value = BookmarkListData(0,"",""))
    var groupPopUp: MutableState<Boolean> = mutableStateOf(false)
    var sharePopUp: MutableState<Boolean> = mutableStateOf(false)
    var actionDialog: MutableState<Boolean> = mutableStateOf(false)
    var title: MutableState<String> = mutableStateOf("")
    var id: MutableState<String> = mutableStateOf("")
    var bookmark1: MutableState<Boolean> = mutableStateOf(false)
    var category: MutableState<String> = mutableStateOf("")

    /////////////////
    ///   LIST   ///
    ////////////////

    // ADD TO LIST
    fun addToList(data: MainListData) {
        viewModelScope.launch {
            repository.addToList(data = data)
        }
    }

    // READ LIST
    private val _allList = MutableStateFlow<List<MainListData>>(emptyList())
    val allList: StateFlow<List<MainListData>> = _allList
    fun getList(){
        viewModelScope.launch {
            repository.getList.collect {
                _allList.value = it
            }
        }
    }

    // LIST COUNT
    val getListCount: Flow<Int> = repository.getListCount

    // ADD/REMOVE BOOKMARK
    fun addRemoveBookmark(gitId: Int, bookmark: Boolean){
        viewModelScope.launch {
            repository.updateBookmark(gitId = gitId, bookmark = bookmark)
        }
    }

    // FILTER LIST
    private val _searchTasks = MutableStateFlow<List<MainListData>>(emptyList())
    val searchTasks: StateFlow<List<MainListData>> = _searchTasks

    fun getSearchTask(searchQuery: String){
        viewModelScope.launch {
            repository.searchDatabase(searchQuery = "%$searchQuery%").collect {
                _searchTasks.value = it
            }
        }
    }

    /////////////////
    ///   MAIN   ///
    ////////////////

    // ADD TO CONTENT
    fun addToContent(data: MainContentData) {
        viewModelScope.launch {
            repository.addToContent(data = data)
        }
    }

    // READ CONTENT
    private val _selectedTask = MutableStateFlow<RequestState<MainContentData>>(RequestState.Idle)
    val selectedTask: StateFlow<RequestState<MainContentData>> = _selectedTask

    fun getSelectedItem(id: Int){
        _selectedTask.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getSelectedItem(gitId = id).collect { item ->
                    _selectedTask.value = RequestState.Success(item)
                }
            }
        }catch (e: Exception){
            _selectedTask.value = RequestState.Error(e)
        }
    }


    // GROUP LIST
    private val _groupList = MutableStateFlow<List<String>>(emptyList())
    val groupList: StateFlow<List<String>> = _groupList
    fun getGroupList(){
        viewModelScope.launch {
            repository.groupList.collect{
                _groupList.value = it
            }
        }
    }

    /////////////////
    /// BOOKMARK ///
    ////////////////

    // ADD TO BOOKMARK
    fun addBookmark(data: BookmarkListData) {
        viewModelScope.launch {
            repository.addBookmark(data = data)
        }
    }

    // GET COUNT
    val getCount: Flow<Int> = repository.getCount

    // LIST BOOKMARK
    private val _allBookmark = MutableStateFlow<List<BookmarkListData>>(emptyList())
    val allBookmark: StateFlow<List<BookmarkListData>> = _allBookmark
    fun getAllBookmark(){
        viewModelScope.launch {
            repository.getAllBookmark.collect{
                _allBookmark.value = it
            }
        }
    }

    // ALL BOOKMARK IDs
    private val _bookmarkIds = MutableStateFlow<List<Int>>(emptyList())
    val bookmarkIds: StateFlow<List<Int>> = _bookmarkIds
    fun getBookmarkIds(){
        viewModelScope.launch {
            repository.bookmarkIds.collect{
                _bookmarkIds.value = it
            }
        }
    }

    // DELETE BOOKMARK
    fun deleteBookmark(swipedItem: BookmarkListData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBookmark(bookmark = swipedItem)
        }
    }

    // DELETE ALL BOOKMARK
    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAllTask()
        }
    }

    // LIST AP
    private val _allAp = MutableStateFlow<List<APListData>>(emptyList())
    val allAp: StateFlow<List<APListData>> = _allAp
    fun getAllAp(){
        viewModelScope.launch {
            repository.getAllAp.collect{
                _allAp.value = it
            }
        }
    }

    // READ CONTENT AP
    private val _selectedAP = MutableStateFlow<RequestState<APData>>(RequestState.Idle)
    val selectedAP: StateFlow<RequestState<APData>> = _selectedAP

    fun getSelectedAP(id: Int){
        _selectedAP.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getSelectedAP(apId = id).collect { item ->
                    _selectedAP.value = RequestState.Success(item)
                }
            }
        }catch (e: Exception){
            _selectedAP.value = RequestState.Error(e)
        }
    }
}
