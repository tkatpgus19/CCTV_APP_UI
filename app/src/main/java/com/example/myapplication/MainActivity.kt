package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    private lateinit var drawLayout: DrawLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawLayout = DrawLayout(this, baseContext, 13, getSize())

        //태블릿인가?
        if(isTabletDevice(baseContext))
            drawLayout.drawTablet()
        else
            drawLayout.drawPhone()

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

    // 알림생성 함수
    private fun createNotificationChannel(id :String, name :String) : NotificationCompat.Builder{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, id)
        } else {
            NotificationCompat.Builder(this)
        }
    }

    // 태블릿 확인함수
    private fun isTabletDevice(context: Context): Boolean{
        val xlarge = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
        val large = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge or large
    }

    // 디바이스 크기 계산
    private fun getSize(): Point{
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        return size
    }


}