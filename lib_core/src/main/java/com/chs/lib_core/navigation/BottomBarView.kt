package com.chs.lib_core.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MenuItem
import androidx.navigation.NavController
import com.blankj.utilcode.util.SizeUtils
import com.chs.lib_core.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

/**
 * author：chs
 * date：2020/3/29
 * des： 底部导航栏
 */
class BottomBarView : BottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener {

    private var navController: NavController? = null

    companion object {
        val sIcons = arrayOf(
            R.drawable.icon_tab_home, R.drawable.icon_tab_apply,
            R.drawable.icon_tab_find, R.drawable.icon_tab_mine
        )
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setOnNavigationItemSelectedListener(this)
        val bottomBar = NavConfig.getBottomBar()
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )
        itemIconTintList = ColorStateList(states, colors)
        itemTextColor = ColorStateList(states, colors)
        //设置文本和字体一直都显示
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val tabs = bottomBar.tabs
        //添加menu
        for (tab in tabs) {
            if (!tab.enable) {
                continue
            }
            val itemId: Int = getItemId(tab.pageUrl)
            if (itemId < 0) {
                continue
            }
            val menu = menu
            val menuItem = menu.add(0, itemId, tab.index, tab.title)
            menuItem.setIcon(sIcons[tab.index])
        }
        //设置menu的大小 添加完所有的itemMenu之后才改变大小，因为每次添加都会先移除所有的item，排序之后再放入到容器中
        var index = 0
        for (tab in tabs) {
            if (!tab.enable) {
                continue
            }
            val itemId: Int = getItemId(tab.pageUrl)
            if (itemId < 0) {
                continue
            }
            val size = SizeUtils.dp2px(tab.size.toFloat())
            val menuView: BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val itemView: BottomNavigationItemView = menuView.getChildAt(index) as BottomNavigationItemView
            itemView.setIconSize(size)
            if (TextUtils.isEmpty(tab.title)) { //title为空的一般是中间的按钮 有那种中间变大的按钮
                val tintColor =
                    if (TextUtils.isEmpty(tab.tintColor)) Color.parseColor("#ff678f") else Color.parseColor(
                        tab.tintColor
                    )
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                //禁止上下浮动的效果
                itemView.setShifting(false)
            }
            index++
        }
        //底部导航栏默认选中项
        if (0 != bottomBar.selectTab) {
            val selectTab = tabs[bottomBar.selectTab]
            if (selectTab.enable) {
                val itemId = getItemId(selectTab.pageUrl)
                //延迟一下在切换，等待NavGraphBuilder解析完成
                post { selectedItemId = itemId }
            }
        }
    }

    private fun getItemId(pageUrl: String): Int {
        val destination = NavConfig.getDestinationMap()[pageUrl]
        return destination?.id ?: -1
    }

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController?.navigate(item.itemId)
        return true;
    }
}