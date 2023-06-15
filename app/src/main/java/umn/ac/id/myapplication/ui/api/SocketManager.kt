package umn.ac.id.myapplication.ui.api

import android.content.Context
import io.socket.client.IO
import io.socket.client.Socket

class SocketManager private constructor(private var context: Context) {

    private var socket: Socket? = null
    private val BASE_URL        = "http://192.168.43.63:8080"

    companion object {
        var instance: SocketManager? = null
        fun getInstance(context: Context): SocketManager? {
            if (instance == null) {
                instance = SocketManager(context)
            }
            return instance
        }
    }

    fun getSocket() = socket

    init {
        socket = IO.socket(BASE_URL)
        socket!!.connect()
    }

}
