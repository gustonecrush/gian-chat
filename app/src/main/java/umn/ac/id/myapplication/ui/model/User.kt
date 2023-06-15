package umn.ac.id.myapplication.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var user: String = "",
    var image: String = "",
    var token: String = "",
    var isOnline: Boolean = false
) : Parcelable {

    companion object {
        const val USERNAME = "username"
        const val IMAGE = "image"
        const val TOKEN = "token"
        const val IS_ONLINE = "isOnline"
    }

}