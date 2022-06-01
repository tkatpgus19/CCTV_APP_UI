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

        var num = 4
        var rowCnt = 8
        var columnCnt = 8

        frameList = (0..15).map { i ->
            val frame = CctvLayout(this)
            val layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(i / num * 2, 2, 1.0f),
                GridLayout.spec(i % num * 2, 2, 1.0f)
            )
            layoutParams.width = 0
            layoutParams.height = 0
            frame.layoutParams = layoutParams

            frame
        }

        val gridLayout = GridLayout(baseContext)
        gridLayout.rowCount = rowCnt
        gridLayout.columnCount = columnCnt
        gridLayout.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        (0..15).map { i ->
            gridLayout.addView(frameList[i])
        }

        val cnt = intent.getIntExtra("cnt", 5)
        val list = intent.getIntArrayExtra("list")

        for(a in list!!){
            if(a == cnt){
                frameList[cnt].setup(1)
                frameList[cnt].setLabel("${cnt}")
            }
        }
        frameList[cnt].layoutParams = GridLayout.LayoutParams(
            GridLayout.spec(0, 8, 1.0f),
            GridLayout.spec(0, 8, 1.0f)
        )
        frameList[cnt].z = 10.0f
        t.addView(gridLayout)


        frameList[cnt].setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }


}