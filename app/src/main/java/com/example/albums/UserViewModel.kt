package com.example.albums
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class UserViewModel : ViewModel() {
    //prepare to get the data
    private val jsonPlaceholderApi = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JsonPlaceholderApi::class.java)


    //has user information
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    //has albums information
    private val _albums = mutableStateOf<List<Album>>(emptyList())
    val albums: State<List<Album>> get() = _albums

    //has photo information
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> get() = _photos

    //current userid
    private val _userId = mutableStateOf<Int>((0..9).random())
    val userId: State<Int> get() = _userId

    private val _albumId = mutableStateOf<Int>((0..9).random())


    init {
        getUsers()
        getPhotos(_albumId.value)

    }

    private fun getUsers() {
        viewModelScope.launch {
            _users.value = jsonPlaceholderApi.getUsers()
        }
    }

    fun getAlbums(userId: Int) {
        viewModelScope.launch {
            _albums.value = jsonPlaceholderApi.getAlbums(userId)
        }
    }

    fun getPhotos(albumId: Int) {
        //
        viewModelScope.launch {
            _photos.value = jsonPlaceholderApi.getPhotos(albumId)
        }
    }
    fun changeUser(newId: Int){
        _userId.value = newId
    }


}