//package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.datastore.preferences.preferencesDataStore
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import com.nurbk.ps.demochat.adapter.UserAdapter
//import com.nurbk.ps.demochat.other.*
//import io.socket.client.Socket
//import io.socket.emitter.Emitter
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import umn.ac.id.myapplication.R
//import umn.ac.id.myapplication.databinding.FragmentChatCompanyBinding
//import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.User
//import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.network.SocketManager
//import umn.ac.id.myapplication.ui.data.UserPreferences
//import umn.ac.id.myapplication.ui.viewmodelfactory.ChatCompanyViewModelFactory
//import java.lang.reflect.Type
//import java.util.*
//
//class ChatCompanyFragment : Fragment() {
//
//    private lateinit var mBinding: FragmentChatCompanyBinding
//    private lateinit var user: User
//
//    private val Context.dataStore by preferencesDataStore(name = "user")
//    private var token = ""
//
//    private val userAdapter = UserAdapter()
//    private val viewMode by lazy { ViewModelProvider(requireActivity())[UsersViewModel::class.java] }
//
//    private var mSocket: Socket? = null
//    private var userString: String = ""
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
//        val chatViewModel =
//            ViewModelProvider(this, ChatCompanyViewModelFactory(userPreferences)).get(ChatCompanyViewModel::class.java)
//
//        mBinding = FragmentChatCompanyBinding.inflate(layoutInflater, container, false)
//
//        chatViewModel.getSession().observe(viewLifecycleOwner){
//            if(it.isLogin){
//                token = it.token
//                Log.d("TAG", "onCreate: $token")
//            }
//        }
//
//        mSocket!!.on(GET_ALL_USER, onUserList)
//        return mBinding.root
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mSocket = SocketManager.getInstance(requireContext())!!.getSocket()
//
////        userString = ConfigUser.getInstance(requireContext())!!.getPreferences()!!
////            .getString(DATA_USER_NAME, "")!!
////        user = Gson().fromJson(userString, User::class.java)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        mBinding.rvChatList.apply {
//            adapter = userAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//
//        userAdapter.setItemClickListener { user, _ ->
//            findNavController()
//                .navigate(
//                    R.id.btnSelectUser,
//                    Bundle().apply {
//                        this.putString(USER_ID, (user as User).id)
//                        this.putString(TYPE_CHAT, USER_FRAGMENT)
//                        this.putString(USER_NAME, user.name)
//                    }
//                )
//        }
//    }
//
//    private var onUserList = Emitter.Listener { args ->
//        GlobalScope.launch(Dispatchers.Main) {
//            val length = args.size
//            if (length == 0) {
//                return@launch
//            }
//            val userListToken: Type = object : TypeToken<ArrayList<User>>() {}.type
//            val userList =
//                Gson().fromJson<ArrayList<User>>(
//                    args[0].toString(),
//                    userListToken
//                )
//            user.isOnline = true
//            userList.remove(user)
//            viewMode.addListUsers(userList)
//        }
//    }
//
//}