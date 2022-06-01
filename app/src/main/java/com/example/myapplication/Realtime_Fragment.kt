package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.FragmentRealtimeBinding

class Realtime_Fragment : Fragment() {
    private lateinit var drawLayout: DrawLayout
    private lateinit var binding: FragmentRealtimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_realtime_, container, false)

        val view = binding.root
        var flag = arguments?.getInt("flag")

        Log.d("CIVAL", "flag == ${flag}")
        drawLayout = DrawLayout(binding, requireActivity(), requireContext())


        // 처음 앱 실행시
        if(flag == null) {
            if (isTabletDevice(requireContext()))
                drawLayout.drawTablet()
            else
                drawLayout.drawPhone()
        }
        // 아닐 때
        else if(flag == 0)
            drawLayout.drawPhone()
        else
            drawLayout.drawTablet()

        return view
    }


    // 태블릿 확인 함수
    private fun isTabletDevice(context: Context): Boolean{
        val xlarge = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
        val large = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge or large
    }

}