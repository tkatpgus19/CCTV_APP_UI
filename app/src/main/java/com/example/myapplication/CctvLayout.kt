package com.example.myapplication

import android.app.Activity
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.view.updatePadding

class CctvLayout(activity: Activity) : FrameLayout(activity) {
    var isSetup = false
    val activity = activity
    private val labelView: TextView
    private val statusView: TextView
    var scf = 0
    var startX = 0
    var startY = 0
    var touchCnt = 0

    init {
        labelView = TextView(activity)
        labelView.setTextColor(0xffffffff.toInt())
        labelView.updatePadding(
            left=TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                5.0f,
                resources.displayMetrics).toInt())
        labelView.text = ""

        statusView = TextView(activity)
        statusView.setBackgroundColor(0xff000000.toInt())
        statusView.setTextColor(0xffffffff.toInt())
        statusView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
        statusView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        statusView.gravity = Gravity.CENTER
        statusView.text = "Idle"


        setPadding(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2.0f,
                resources.displayMetrics).toInt())

        background = ContextCompat.getDrawable(activity, R.drawable.layout_border_normal)

        z = 5.0f


        addView(statusView, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT))
        addView(labelView, LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT))
    }

    fun setLabel(label: String) {
        val text = SpannableString("$label ")
        text.setSpan(ForegroundColorSpan(0xffffffff.toInt()), 0, text.length, 0)
        //text.setSpan(BackgroundColorSpan(0x66000000.toInt()), 0, text.length, 0)
        labelView.setText(text, TextView.BufferType.SPANNABLE)
        labelView.setShadowLayer(2.0f, 1.4f, 1.3f, 0xff000000.toInt())
    }

    fun setup(port: Int) {
        val status = SpannableString("Standby")
        status.setSpan(ForegroundColorSpan(0xccaaffaa.toInt()), 0, status.length, 0)
        status.setSpan(BackgroundColorSpan(0x66000000.toInt()), 0, status.length, 0)
        statusView.setText(status, TextView.BufferType.SPANNABLE)
        statusView.visibility = View.VISIBLE
        isSetup = true
    }
    fun warnning(flag: Int){
        if(flag == 1){
            background = ContextCompat.getDrawable(activity, R.drawable.layout_border_warning)
        }
        else{
            background = ContextCompat.getDrawable(activity, R.drawable.layout_border_normal)
        }
    }
    fun setGroup(scf: Int, startX: Int, startY: Int){
        this.scf = scf
        this.startX = startX
        this.startY = startY
    }

}