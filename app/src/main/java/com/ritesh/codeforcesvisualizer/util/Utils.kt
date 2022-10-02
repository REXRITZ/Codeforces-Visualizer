package com.ritesh.codeforcesvisualizer.util

import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertDpToPixel(dp: Float, density: Float): Float {
        return dp * density
    }

    fun calculateTextHeight(paint: Paint, s: String): Int {
        val rect = Rect(0,0,0,0)
        paint.getTextBounds(s,0,s.length,rect)
        return rect.height()
    }

    fun toFormattedTime(time: Long): String {
        val formatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        return formatter.format(time)
    }

    val colors = arrayOf(
        -29539,-3147530,-669952,-2535286,-2164,-5033904,-4225402,-7023404,-12560000,-8999249,-6969988,-4128884,-2508638,-92921,-7818053,-7542017,-13996670,-4119214,-9787514,-39424,-67720,-12148,-5020619,-9791969,-13253935
    )


}