package umn.ac.id.myapplication.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.*
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity
import umn.ac.id.myapplication.ui.chat.model.Message
import umn.ac.id.myapplication.ui.chat.other.ID
import umn.ac.id.myapplication.ui.chat.other.decodeImage
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class MessageAdapter(val dataMessages: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var receiveMessageBinding: ItemReceiveMessageBinding
    private lateinit var senderMessageBinding: ItemSendMessageBinding

    inner class SenderViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView) {
        fun bind(message: Message) {
            senderMessageBinding.textMessage.text = message.message
        }
    }

    inner class ReceverViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView) {
        fun bind(message: Message) {
            receiveMessageBinding.textMessage.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {
                SenderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_sender_message, parent, false)
                )
            }
            else -> {
                ReceverViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_receive_message, parent, false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is SenderViewHolder) {
            holder.bind(dataMessages[position])
        } else if (holder is ReceverViewHolder) {
            holder.bind(dataMessages[position])
        }

    }

    override fun getItemCount(): Int = dataMessages.size


    override fun getItemViewType(position: Int): Int {
        val message = dataMessages[position]

        return when (message.id) {
            LoginActivity.users.id -> {
                0
            }
            else -> {
                1
            }
        }
    }

}