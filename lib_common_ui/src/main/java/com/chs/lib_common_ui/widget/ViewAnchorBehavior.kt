package com.chs.lib_common_ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.blankj.utilcode.util.SizeUtils
import com.chs.lib_common_ui.R

/**
 * author：chs
 * date：2020/6/30
 * des： 详情页面 CoordinatorLayout的行为控制器 泛型View是需要基于哪个View来摆放自己的位置
 */
class ViewAnchorBehavior:CoordinatorLayout.Behavior<View> {

    private var anchorId = 0
    private var extraUsed = 0
    constructor() : super()
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        val arr = context.obtainStyledAttributes(attributeSet, R.styleable.ViewAnchorBehavior,0,0)
        anchorId = arr.getResourceId(R.styleable.ViewAnchorBehavior_anchorId, 0)
        arr.recycle()
        extraUsed = SizeUtils.dp2px(48f)
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return anchorId == dependency.id
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int  //竖直方向上已经被占用的空间
    ): Boolean {
        //child所依赖的View
        val anchorView = parent.findViewById<View>(anchorId) ?: return false

        //重新计算子视图竖直方向上所占用的空间
        val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        val topMargin = layoutParams.topMargin
        val bottom =  anchorView.bottom

        val heightRealUsed = topMargin + bottom + extraUsed
        parent.onMeasureChild(child,parentWidthMeasureSpec,0,parentHeightMeasureSpec,heightRealUsed)
        return true
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {

        //child所依赖的View
        val anchorView = parent.findViewById<View>(anchorId) ?: return false

        //重新计算子视图竖直方向上所占用的空间
        val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        val topMargin = layoutParams.topMargin
        val bottom =  anchorView.bottom

        parent.onLayoutChild(child,layoutDirection)
        //设置view的偏移量
        child.offsetTopAndBottom(topMargin+bottom)
        return true
    }

}