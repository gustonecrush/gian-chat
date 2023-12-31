package umn.ac.id.myapplication.ui.companypage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.databinding.ActivityChatBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.viewmodel.MessageViewModel
import umn.ac.id.myapplication.ui.data.GetChatResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.model.Message
import umn.ac.id.myapplication.ui.model.MessageData
import umn.ac.id.myapplication.ui.viewmodelfactory.MessageViewModelFactory
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileViewModelFactory
import java.lang.reflect.Type

class MessageCompanyActivity : AppCompatActivity() {

    private var mSocket: Socket? = null
    private lateinit var binding: ActivityChatBinding
    private val messageAdapter   = MessageAdapter(arrayListOf())

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSocket = SocketManager.getInstance(this)!!.getSocket()

        binding.usernameChat.text = intent.getStringExtra("username").toString()

        binding.rcDataMes.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        binding.btnSend.setOnClickListener {
            sendMessage(intent.getStringExtra("token").toString(), LoginCompanyActivity.company.token)
        }

        mSocket!!.on("message") { arg ->
            runOnUiThread {
                val messageTypeToken: Type = object : TypeToken<Message>() {}.type
                val message = Gson().fromJson<Message>(arg[0].toString(), messageTypeToken)
                messageAdapter.apply {
                    dataMessages.add(message)
                }
                messageAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun sendMessage(tokenApplicant: String, tokenCompany: String) {

        val message = JSONObject()
        message.put("user", LoginCompanyActivity.company.user)
        message.put("token", LoginCompanyActivity.company.token)
        message.put("message", binding.inputMessage.text.toString())

        val messageData = MessageData(
            tokenA = tokenApplicant,
            tokenC = tokenCompany,
            Message = message
        )

        val client = ApiClient.apiInstance.sendMessageFromCompany(LoginCompanyActivity.company.token, messageData)
        client.enqueue(object : Callback<GetChatResponse> {
            override fun onResponse(call: Call<GetChatResponse>, response: Response<GetChatResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val message = apiResponse?.message ?: "No message provided"
                    Log.d("", "onResponse: $message")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        response.errorBody()?.toString()
                    } catch (e: JSONException) {
                        "Error retrieving error message"
                    }
                    Log.e("", "onResponse: $errorMessage")
                }
            }

            override fun onFailure(call: Call<GetChatResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message}")
            }
        })

        mSocket!!.emit("message", message)
    }

}