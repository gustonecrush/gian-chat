//package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.Message
//import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.User
//
//class ChatViewModel(application: Application) : AndroidViewModel(application) {
//
//    val dataUserLiveData = MutableLiveData<ArrayList<User>>()
//
//    fun getUsers(arrayList: ArrayList<User>) {
//        GlobalScope.launch {
//            dataUserLiveData.postValue(arrayList)
//        }
//    }
//
//}