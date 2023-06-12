package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nurbk.ps.demochat.ui.fragment.MainFragment
import io.socket.client.Socket
import org.json.JSONObject
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.home.HomeFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileFragment
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.other.*
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationview: BottomNavigationView;
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var user: User
    private var mSocket: Socket? = null
    private var isDataShow = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = UserPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

        mSocket = SocketManager.getInstance(this)!!.getSocket()

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {}
        mSocket!!.on(Socket.EVENT_CONNECT) {}
        mSocket!!.on(Socket.EVENT_DISCONNECT) {}

        mainViewModel.getSession().observe(this){
            if (it.isLogin){
                isDataShow = savedInstanceState?.getBoolean(IS_CONNECTING) ?: true
                if (isDataShow) {
                    mSocket!!.emit(GET_ALL_USER, true)
                    mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
                        put(User.USERNAME, it.username)
                        put(User.IS_ONLINE, true)
                    })
                }
            }
        }

        bottomNavigationview = findViewById(R.id.bottom_nav)

        bottomNavigationview.setOnNavigationItemSelectedListener{
            item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_home -> selectedFragment = HomeFragment()
                R.id.navigation_chat -> selectedFragment = MainFragment()
                R.id.navigation_history -> selectedFragment = HistoryFragment()
                R.id.navigation_profile -> selectedFragment = ProfileFragment()
            }

            if(selectedFragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit()
            }

            true

        }

        bottomNavigationview.selectedItemId = R.id.navigation_home

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_CONNECTING, false)
    }

//    override fun onDestroy() {
//        if (mSocket != null)
//            mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
//                put(User.ID, user.id)
//                put(User.IS_ONLINE, false)
//            })
//        super.onDestroy()
//    }
}