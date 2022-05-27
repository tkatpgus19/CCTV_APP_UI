package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setExpandableList()


        /*
        var sec : Int = 0
        timer(period = 1000, initialDelay = 100){
            sec++
            Log.d("CIVAL",  "${sec}")
            if(sec >= 2 && sec < 8) {
                frameList[0].warnning(1)
                val builder = createNotificationChannel("id", "name")
                    .setTicker("Ticker")
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setNumber(1)
                    .setAutoCancel(true)
                    .setContentTitle("Weapon Detected!")
                    .setContentText("앱에 와서 확인하세요")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(baseContext)){
                    notify(0, builder.build())
                }
            }
            else if(sec >= 8){
                frameList[0].warnning(0)
                cancel()
            }
        }
        */

    }

    private fun setExpandableList(){
        val parentList = mutableListOf("실시간 CCTV", "저장된 CCTV 영상", "BLANK")
        val childList = mutableListOf(
            mutableListOf("2x2", "4x4"),
            mutableListOf(),
            mutableListOf()
        )

        val expandableAdapter = ExpandableListAdapter(this, parentList, childList)
        binding.elMenu.setAdapter(expandableAdapter)

        binding.elMenu.setOnGroupClickListener{ parent, v, groupPosition, id ->
            when(id){
                1L->{
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                    val fragment = Archive_Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.nav_fragment, fragment)
                        .commit()
                }
                2L->binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            false
        }
        binding.elMenu.setOnChildClickListener{ parent, v, groupPosition, childPosition, id ->
            var flag = 0
            when(id){
                0L -> flag = 0
                1L -> flag = 1
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            val bundle = Bundle()
            bundle.putInt("flag", flag)

            val fragment = Realtime_Fragment()
            fragment.arguments = bundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.nav_fragment, fragment)
                .commit()
            false
        }
    }
    // 취소 버튼으로 메뉴 닫기
    override fun onBackPressed(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    // 알림생성 함수
    private fun createNotificationChannel(id :String, name :String) : NotificationCompat.Builder{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, id)
        } else {
            NotificationCompat.Builder(this)
        }
    }


}