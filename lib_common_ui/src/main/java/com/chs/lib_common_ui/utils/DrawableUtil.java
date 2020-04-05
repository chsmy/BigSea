package com.chs.lib_common_ui.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;

/**
 * @author chs
 * date：2019-10-24 15:00
 * des：获取圆角drawable
 */
public class DrawableUtil {

    /**
     * 圆角背景
     * @param colorId  背景颜色
     * @param radiusSize 圆角大小
     * @return
     */
    public static GradientDrawable getRoundDrawable(Context context, int colorId, int radiusSize ) {
        GradientDrawable drawable = new GradientDrawable();
        float radius = SizeUtils.dp2px( radiusSize);
        drawable.setCornerRadius(radius);
        drawable.setColor(ContextCompat.getColor(context, colorId));
        return drawable;
    }

    /**
     * 有边的背景
     * @param bgColorId 背景颜色
     * @param strokeColor 描边颜色
     * @param radiusSize  圆角大小
     * @param strokeWidth  描边的宽度
     * @return
     */
    public static GradientDrawable getRoundStrokeDrawable(Context context,
                                                          int bgColorId, int strokeColor, int radiusSize, float strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        float radius = SizeUtils.dp2px( radiusSize);
        drawable.setCornerRadius(radius);
        drawable.setColor(ContextCompat.getColor(context, bgColorId));
        drawable.setStroke(SizeUtils.dp2px(strokeWidth), ContextCompat.getColor(context,strokeColor));
        return drawable;
    }


    /**
     * 能源图表 图例
     * @return
     */
    public static GradientDrawable getEnergyDrawable(Context context, int colorId) {
        GradientDrawable drawable = new GradientDrawable();
        float radius = SizeUtils.dp2px( 1);
        drawable.setCornerRadius(radius);
        drawable.setColor(ContextCompat.getColor(context, colorId));
        drawable.setSize(SizeUtils.dp2px(12), SizeUtils.dp2px(12));
        return drawable;
    }

    /**
     *  左边有圆角
     * @radius 单位dp
     * @return
     */
    public static GradientDrawable getDrawableLeftRadius(Context context, int colorId, float radiusDp){
        GradientDrawable drawable = new GradientDrawable();
        float radiusPix = SizeUtils.dp2px(radiusDp);
        drawable.setCornerRadii(new float[]{radiusPix,radiusPix,0,0,0,0,radiusPix,radiusPix});
        drawable.setColor(ContextCompat.getColor(context,colorId));
        return drawable;
    }
    /**
     *  左边有圆角 从左到右渐变
     * @radius 单位dp
     * @return
     */
    public static GradientDrawable getGradientDrawableLeftRadius(Context context,
                                                                 int startColorId, int endColorId, float radiusDp){
        int[] colors = {ContextCompat.getColor(context,startColorId), ContextCompat.getColor(context,endColorId)};
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors);
        float radiusPix = SizeUtils.dp2px(radiusDp);
        drawable.setCornerRadii(new float[]{radiusPix,radiusPix,0,0,0,0,radiusPix,radiusPix});
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }
    /**
     *  下边有圆角
     * @radius 单位dp
     * @return
     */
    public static GradientDrawable getDrawableBottom(Context context, int colorId, float radiusDp){
        GradientDrawable drawable = new GradientDrawable();
        float radiusPix = SizeUtils.dp2px(radiusDp);
        drawable.setCornerRadii(new float[]{0,0,0,0,radiusPix,radiusPix,radiusPix,radiusPix});
        drawable.setColor(ContextCompat.getColor(context,colorId));
        return drawable;
    }

}
