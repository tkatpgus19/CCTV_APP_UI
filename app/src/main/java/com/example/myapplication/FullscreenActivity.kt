package com.example.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout

class FullscreenActivity : AppCompatActivity() {
    private var frameList: List<CctvLayout> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)


        val t = findViewById<LinearLayout>(R.id.testt)


        frameList = (0..15).map { i ->
            val frame = CctvLayout(this)
            frame.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            frame
        }


        val cnt = intent.getIntExtra("cnt", 5)
        val scf = intent.getIntExtra("scf",0)

        if(scf == 1) {
            frameList[cnt].setup(1)
            frameList[cnt].setLabel("${cnt}")
        }
        else{
            frameList[cnt].delete()
        }

        t.addView(frameList[cnt])


        frameList[cnt].setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}