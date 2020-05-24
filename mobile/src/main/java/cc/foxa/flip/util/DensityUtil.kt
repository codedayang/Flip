package cc.foxa.flip.util

import android.content.Context

fun dip2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density;
    return dpValue * scale
}
