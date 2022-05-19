package com.example.myapplication

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class DrawLayout(activity: Activity, context: Context, camera: Int, displaySize: Point) {
    private var frameList: List<CctvLayout> = arrayListOf()
    private var layoutParamsList: MutableList<GridLayout.LayoutParams?> = MutableList(16) { null }
    private val binding: ActivityMainBinding
    private val act = activity
    private val con = context
    private val cam = camera
    private val layoutTransition = LayoutTransition()
    private val size = displaySize
    private val groupList = listOf(
        listOf(0,1,4,5),
        listOf(2,3,6,7),
        listOf(8,9,12,13),
        listOf(10,11,14,15),
        listOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15))

    init {
        binding = DataBindingUtil.setContentView(act, R.layout.activity_main)
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        layoutTransition.setDuration(200)

    }

    fun drawTablet(){

        val testList = listOf(
            listOf(2,4, 2,5, 3,4),
            listOf(2,3, 3,2, 3,3),
            listOf(4,4, 5,4, 5,5),
            listOf(4,2, 4,3, 5,2))

        val testList2 = listOf(
            3,5, 2,2, 4,5, 5,3
        )

        var t = 0

        setLayoutParams(0)

        for(cnt in 0..3){
            for(i in groupList[cnt]) {

                // 그리드 한칸당 설정
                frameList[i].setOnClickListener {
                    val scf = frameList[i].scf

                    if(frameList[i].z != 0.0f) {
                        for (n in groupList[cnt].filter { it != i }) {
                            if (layoutParamsList[n] != null) {
                                if (frameList[i].touchCnt == 2) {
                                    frameList[n].layoutParams = layoutParamsList[n]
                                    layoutParamsList[n] = null
                                    frameList[n].z = 5.0f
                                    t = 0
                                }
                            } else {
                                layoutParamsList[n] =
                                    frameList[n].layoutParams as GridLayout.LayoutParams
                                val layoutParams = GridLayout.LayoutParams(
                                    GridLayout.spec(testList[cnt][t * 2], 1, 0.5f),
                                    GridLayout.spec(testList[cnt][t * 2 + 1], 1, 0.5f)
                                )
                                layoutParams.width = 0
                                layoutParams.height = 0
                                frameList[n].z = 5.0f
                                frameList[n].layoutParams = layoutParams
                                t++
                            }
                        }

                        if (layoutParamsList[i] != null) {

                            // 터치가 두번째(전체화면), 다시 원래 4x4로 복귀
                            if (frameList[i].touchCnt == 2) {
                                frameList[i].layoutParams = layoutParamsList[i]
                                layoutParamsList[i] = null
                                frameList[i].z = 5.0f

                                frameList[scf].layoutParams = layoutParamsList[scf]
                                layoutParamsList[scf] = null
                                frameList[scf].z = 5.0f

                                frameList[i].touchCnt = 0

                                for (a in groupList[4].filter { it != i }) {
                                    frameList[a].z = 5.0f
                                }
                            }
                            // 터치가 첫번째(1차확대 상태), 전체화면으로 확대
                            else {
                                val layoutParams = GridLayout.LayoutParams(
                                    GridLayout.spec(0, 8, 1.0f),
                                    GridLayout.spec(0, 8, 1.0f)
                                )
                                frameList[i].z = 10.0f
                                frameList[i].layoutParams = layoutParams

                                frameList[i].touchCnt++
                            }
                        } else {
                            layoutParamsList[i] =
                                frameList[i].layoutParams as GridLayout.LayoutParams
                            layoutParamsList[scf] =
                                frameList[scf].layoutParams as GridLayout.LayoutParams

                            val layoutParams = GridLayout.LayoutParams(
                                GridLayout.spec(frameList[i].startY, 4, 1.0f),
                                GridLayout.spec(frameList[i].startX, 4, 1.0f)
                            )
                            val divLayoutParams = GridLayout.LayoutParams(
                                GridLayout.spec(testList2[cnt * 2], 1, 0.5f),
                                GridLayout.spec(testList2[cnt * 2 + 1], 1, 0.5f)
                            )
                            frameList[i].z = 5.0f
                            frameList[i].layoutParams = layoutParams

                            divLayoutParams.width = 0
                            divLayoutParams.height = 0

                            frameList[scf].z = 5.0f
                            frameList[scf].layoutParams = divLayoutParams

                            frameList[i].touchCnt++

                            for (a in groupList[4].filter { it != i }) {
                                frameList[a].z = 0.0f
                            }
                        }
                    }
                }
            }
        }
    }
    fun drawPhone(){
        val testList = listOf(0,4,8,12)
        if(size.x < size.y) {
            setLayoutParams(1)

            for (cnt in 0..15) {
                frameList[cnt].setOnClickListener {
                    if (frameList[cnt].z != 0.0f) {
                        if (layoutParamsList[cnt] != null) {
                            frameList[cnt].layoutParams = layoutParamsList[cnt]
                            layoutParamsList[cnt] = null

                            for (a in groupList[4]) {
                                frameList[a].z = 5.0f
                            }
                        } else {
                            layoutParamsList[cnt] =
                                frameList[cnt].layoutParams as GridLayout.LayoutParams
                            val layoutParams = GridLayout.LayoutParams(
                                GridLayout.spec(testList[cnt / 4], 4, 0.5f),
                                GridLayout.spec(0, 4, 0.5f)
                            )
                            frameList[cnt].z = 10.0f
                            frameList[cnt].layoutParams = layoutParams

                            for (a in groupList[4].filter { it != cnt }) {
                                frameList[a].z = 0.0f
                            }
                        }
                    }
                }
            }
        }
        else{
            setLayoutParams(2)
            for(cnt in 0..15){
                frameList[cnt].setOnClickListener {
                    if(layoutParamsList[cnt] != null){
                        frameList[cnt].layoutParams = layoutParamsList[cnt]
                        layoutParamsList[cnt] = null
                        frameList[cnt].z = 5.0f
                    }
                    else{
                        layoutParamsList[cnt] =
                            frameList[cnt].layoutParams as GridLayout.LayoutParams
                        val layoutParams = GridLayout.LayoutParams(
                            GridLayout.spec(0,8,1.0f),
                            GridLayout.spec(0,8,1.0f)
                        )
                        frameList[cnt].z = 10.0f
                        frameList[cnt].layoutParams = layoutParams
                    }
                }
            }

        }

    }

    private fun setLayoutParams(flag: Int){
        var num = 4
        var rowCnt = 8
        var columnCnt = 8
        var sizeY = size.y
        var sizeX = size.x

        // 모바일 일때
        if(flag == 1){
            num /= 2
            rowCnt *= 2
            columnCnt /= 2
        }
        else if(flag == 2){
            sizeY -= 75
        }
        else
            sizeY -= 75

        frameList = (0..15).map { i ->
            val frame = CctvLayout(act)
            val layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(i / num * 2, 2, 1.0f),
                GridLayout.spec(i % num * 2, 2, 1.0f)
            )
            layoutParams.width = 0
            layoutParams.height = 0
            frame.layoutParams = layoutParams

            when(i){
                0,1,4,5 -> frame.setGroup(6,0,0)
                2,3,6,7 -> frame.setGroup(5,4,0)
                8,9,12,13 -> frame.setGroup(10,0,4)
                else -> frame.setGroup(9,4,4)
            }
            frame
        }

        val gridLayout = GridLayout(con)
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
        gridLayout.layoutTransition = layoutTransition

        val linearLayout = LinearLayout(con)

        val lp = LinearLayout.LayoutParams(sizeX,sizeY)
        linearLayout.layoutParams = lp
        linearLayout.addView(gridLayout)

        binding.linearLayout.addView(linearLayout)

        for(j in 0..cam){
            frameList[j].setLabel("${j}")
            frameList[j].setup(50501)
        }
    }
}