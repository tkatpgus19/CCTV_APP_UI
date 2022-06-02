package com.example.myapplication

import android.animation.LayoutTransition
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.databinding.FragmentRealtimeBinding

class DrawLayout(binding: FragmentRealtimeBinding, activity: FragmentActivity, context: Context) {
    private var frameList: List<CctvLayout> = arrayListOf()
    var list = arrayListOf<Int>()
    private var layoutParamsList: MutableList<GridLayout.LayoutParams?> = MutableList(16) { null }
    private val con = context
    private val binding = binding
    private val act = activity
    private val layoutTransition = LayoutTransition()
    private val groupList = listOf(
        listOf(0,1,4,5),
        listOf(2,3,6,7),
        listOf(8,9,12,13),
        listOf(10,11,14,15))

    init {
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

        for(cnt in 0..15) {
            frameList[cnt].setOnLongClickListener {

                val popupMenu = PopupMenu(con, it)
                act.menuInflater.inflate(R.menu.camera_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.addCam -> {
                            if(frameList[cnt].isSetup){
                                Toast.makeText(con, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                frameList[cnt].setLabel("${cnt}")
                                frameList[cnt].setup(50501)
                            }
                            true
                        }
                        else -> {
                            if(frameList[cnt].isSetup) {
                                frameList[cnt].delete()
                                frameList[cnt].isSetup = false
                            }
                            else
                                Toast.makeText(con, "이미 제거했습니다.", Toast.LENGTH_SHORT).show()
                            true
                        }
                    }
                }

                true
            }
        }

        for(cnt in 0..3){
            for(i in groupList[cnt]) {

                // 그리드 한칸당 설정
                frameList[i].setOnClickListener {
                    val scf = frameList[i].scf

                    if(frameList[i].z != 0.0f) {
                        act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

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

                                for (a in (0..15).filter { it != i }) {
                                    frameList[a].z = 5.0f
                                }
                            }
                            // 터치가 첫번째(1차확대 상태), 전체화면으로 확대
                            else {
                                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

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

                            for (a in (0..15).filter { it != i }) {
                                frameList[a].z = 0.0f
                            }
                        }
                    }
                }
            }
        }
    }
    fun drawPhone(){

        setLayoutParams(1)

        for (cnt in 0..15) {
            if(frameList[cnt].scf == 1){
                frameList[cnt].setLabel("${cnt}")
                frameList[cnt].setup(50501)
            }
            frameList[cnt].setOnLongClickListener {

                val popupMenu = PopupMenu(con, it)
                act.menuInflater.inflate(R.menu.camera_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.addCam -> {
                            if(frameList[cnt].isSetup){
                                Toast.makeText(con, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                frameList[cnt].setLabel("${cnt}")
                                frameList[cnt].setup(50501)
                                frameList[cnt].scf = 1
                                list[cnt] = 1
                            }
                            true
                        }
                        else -> {
                            if(frameList[cnt].isSetup) {
                                frameList[cnt].delete()
                                frameList[cnt].isSetup = false
                            }
                            else
                                Toast.makeText(con, "이미 제거했습니다.", Toast.LENGTH_SHORT).show()

                            true
                        }
                    }
                }

                true
            }
            frameList[cnt].setOnClickListener {
                // 터치이벤트 등록

                // 터치된 뷰만 할당
                if (frameList[cnt].z != 0.0f) {

                    // 전체화면에서 터치했을시 원래대로 복귀
                    if (layoutParamsList[cnt] != null && frameList[cnt].touchCnt == 2) {
                        //

                        frameList[cnt].layoutParams = layoutParamsList[cnt]
                        layoutParamsList[cnt] = null

                        for (a in (0..15).filter {it != cnt}) {
                            frameList[a].z = 5.0f
                            frameList[a].layoutParams = layoutParamsList[a]
                            layoutParamsList[a] = null
                        }
                        frameList[cnt].touchCnt = 0
                        Log.d("CIVAL", "${cnt},${frameList[cnt].touchCnt}")
                    }
                    else if(frameList[cnt].touchCnt == 0){
                        layoutParamsList[cnt] =
                            frameList[cnt].layoutParams as GridLayout.LayoutParams

                        var layoutParams = GridLayout.LayoutParams()

                        layoutParams = GridLayout.LayoutParams(
                            GridLayout.spec(cnt / 2 * 2, 4, 2.0f),
                            GridLayout.spec(0, 4, 2.0f)
                        )
                        layoutParams.width = 0
                        layoutParams.height = 0

                        frameList[cnt].z = 10.0f
                        frameList[cnt].layoutParams = layoutParams

                        // 짝수
                        if(cnt % 2 == 0) {
                            for(a in (0..15).filter { it != cnt }){
                                if(a < cnt) {
                                    layoutParamsList[a] =
                                        frameList[a].layoutParams as GridLayout.LayoutParams
                                    continue
                                }
                                layoutParamsList[a] =
                                    frameList[a].layoutParams as GridLayout.LayoutParams

                                    layoutParams = GridLayout.LayoutParams(
                                        GridLayout.spec(frameList[a].startY + 2 * (a % 2 + 1), 2, 1.0f),
                                        GridLayout.spec(frameList[a].startX + 2 - (a % 2 * 4), 2, 1.0f)
                                    )
                                layoutParams.width = 0
                                layoutParams.height = 0
                                frameList[a].layoutParams = layoutParams
                            }
                        }
                        // 홀수
                        else{
                            for(a in (0..15).filter { it != cnt }){
                                if(a < cnt-1) {
                                    layoutParamsList[a] =
                                        frameList[a].layoutParams as GridLayout.LayoutParams
                                    continue
                                }
                                layoutParamsList[a] =
                                    frameList[a].layoutParams as GridLayout.LayoutParams

                                if(a == cnt-1){
                                    layoutParams = GridLayout.LayoutParams(
                                        GridLayout.spec(frameList[a].startY + 4, 2, 1.0f),
                                        GridLayout.spec(frameList[a].startX, 2, 1.0f)
                                    )
                                }
                                else{
                                    layoutParams = GridLayout.LayoutParams(
                                        GridLayout.spec(frameList[a].startY + 2 * (a % 2 + 1), 2, 1.0f),
                                        GridLayout.spec(frameList[a].startX + 2 - (a % 2 * 4), 2, 1.0f)
                                    )
                                }
                                layoutParams.width = 0
                                layoutParams.height = 0
                                frameList[a].layoutParams = layoutParams
                            }
                        }

                        for (a in (0..15).filter { it != cnt }) {
                            frameList[a].z = 0.0f
                        }
                        frameList[cnt].touchCnt++
                        Log.d("CIVAL", "${cnt},${frameList[cnt].touchCnt}")
                    }
                    else{
                        val intent = Intent(con, FullscreenActivity::class.java)
                        intent.putExtra("cnt", cnt)
                        intent.putExtra("scf", frameList[cnt].scf)
                        startActivity(con, intent, null)
                    }
                }
            }
        }
    }

    private fun setLayoutParams(flag: Int){
        var num = 4
        var rowCnt = 8
        var columnCnt = 8
        var sizeY = getSize().y
        var sizeX = getSize().x

        // 모바일 일때
        if(flag == 1){
            num /= 2
            rowCnt *= 4
            columnCnt /= 2

            if(sizeY < getSize().x)
                sizeY = sizeX

            frameList = (0..15).map { i ->
                val frame = CctvLayout(act)
                val layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(i / num * 2, 2, 1.0f),
                    GridLayout.spec(i % num * 2, 2, 1.0f)
                )
                layoutParams.width = 0
                layoutParams.height = 0
                frame.layoutParams = layoutParams

                frame.setGroup(0,i % num * 2, i / num * 2)
                list.add(frame.scf)
                frame
            }
        }
        else{
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

        if(flag == 1){
            linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, sizeY *2)
            linearLayout.addView(gridLayout)

            val subLayout = LinearLayout(con)
            subLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            subLayout.addView(linearLayout)

            val scrollView = ScrollView(con)
            scrollView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            scrollView.addView(subLayout)
            binding.frameLayout.addView(scrollView)
        }
        else
        {
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            linearLayout.addView(gridLayout)
            binding.frameLayout.addView(linearLayout)
        }

    }
    private fun getSize(): Point {
        val display = act.windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        return size
    }

}