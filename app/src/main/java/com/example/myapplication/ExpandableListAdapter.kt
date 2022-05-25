package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil

class ExpandableListAdapter(
    private val context: Context,
    private val parents: MutableList<String>,
    private val childList: MutableList<MutableList<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount() = parents.size

    override fun getChildrenCount(parent: Int) = childList[parent].size

    override fun getGroup(parent: Int) = parents[parent]

    override fun getChild(parent: Int, child: Int): String = childList[parent][child]

    override fun getGroupId(parent: Int) = parent.toLong()

    override fun getChildId(parent: Int, child: Int) = child.toLong()

    override fun hasStableIds() = false

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    /* 부모 계층 레이아웃 설정 */
    override fun getGroupView(
        parent: Int,
        isExpanded: Boolean,
        convertView: View?,
        parentview: ViewGroup
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val parentView = inflater.inflate(R.layout.menu_parent, parentview, false)

        val tvParent = parentView.findViewById<TextView>(R.id.tv_list_title)
        tvParent.text = parents[parent]

        setIcon(parent, parentView)
        setArrow(parent, parentView, isExpanded)

        return parentView
    }

    /* 자식 계층 레이아웃 설정 */
    override fun getChildView(
        parent: Int,
        child: Int,
        isLastChild: Boolean,
        convertView: View?,
        parentview: ViewGroup
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childView = inflater.inflate(R.layout.menu_child_realtime, parentview, false)

        val tvChild = childView.findViewById<TextView>(R.id.tv_child_title)
        tvChild.text = getChild(parent, child)

        return childView
    }

    /* drawer 아이콘 설정 */
    private fun setIcon(parentPosition: Int, parentView: View) {
        val iv = parentView.findViewById<ImageView>(R.id.iv_img)
        when (parentPosition) {
            0 -> iv.setImageResource(R.drawable.ic_baseline_videocam_24)
            1 -> iv.setImageResource(R.drawable.ic_baseline_video_library_24)
            2 -> iv.setImageResource(R.drawable.ic_baseline_waves_24)
        }
    }

    /* 닫힘, 열림 표시해주는 화살표 설정 */
    private fun setArrow(parentPosition: Int, parentView: View, isExpanded: Boolean) {
        val arrow = parentView.findViewById<ImageView>(R.id.iv_arrow_drop)
        /* 0번째 부모는 자식이 없으므로 화살표 설정해주지 않음 */
        if (parentPosition == 0) {
            if (isExpanded) arrow.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
            else arrow.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }
    }
}