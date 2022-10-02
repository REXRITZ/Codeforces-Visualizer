package com.ritesh.codeforcesvisualizer.chart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ritesh.codeforcesvisualizer.R
import com.ritesh.codeforcesvisualizer.util.Utils
import com.ritesh.codeforcesvisualizer.util.Utils.colors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PieChart(context: Context, attrs: AttributeSet?): View(context,attrs) {

    private var padding: Float
    private var textSize: Float
    private var textHeight: Int
    private var titleSize: Float
    private var titleHeight: Int
    private var totalLabelHeight: Float = 0.0f
    val oval = RectF()
    private var isReady = false
    private lateinit var pieData: List<PieData>
    private lateinit var title: String
    private var centerX: Float = 0.0f
    private var centerY: Float = 0.0f
    private var radius: Float = 0.0f

    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.GREEN
    }


    val labelPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    val titlePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        typeface = Typeface.DEFAULT_BOLD
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setData(data: List<PieData>) {
        pieData = data
        totalLabelHeight = (textHeight + 5) * data.size.toFloat()
        isReady = true
        invalidate()
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChart,
            0,0).apply {

                try {
                    padding = Utils.convertDpToPixel(16f, resources.displayMetrics.density)
                    labelPaint.color = getColor(R.styleable.PieChart_textcolor,Color.WHITE)
                    titlePaint.color = getColor(R.styleable.PieChart_textcolor,Color.WHITE)
                } finally {
                    recycle()
                }
        }
        textSize = Utils.convertDpToPixel(12f,resources.displayMetrics.density)
        labelPaint.textSize = textSize
        titleSize = Utils.convertDpToPixel(18f,resources.displayMetrics.density)
        textHeight = Utils.calculateTextHeight(labelPaint,"Q")
        titlePaint.textSize = titleSize
        titleHeight = Utils.calculateTextHeight(titlePaint,"Q")

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isReady) {

            canvas.drawText(title,padding,padding*2,titlePaint)
            var textY = padding*2 + titleHeight
            if (totalLabelHeight < height - padding*3 - titleHeight) {
                textY = (height - totalLabelHeight) / 2
            }
            val textX = width/2f
            val radius = textHeight / 2f
            pieData.forEachIndexed { index, data ->
                paint.color = colors[index%colors.size]
                canvas.drawArc(oval,data.startAngle, data.sweepAngle,true,paint)
                canvas.drawCircle(textX + radius , textY + radius, radius, paint)
                canvas.drawText(data.label,textX + radius*4, textY + textHeight , labelPaint)
                textY += textHeight + 5
            }
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val start = padding
        val height = h * 0.45f
        val topPadding = (h - height) / 2

        oval.top = topPadding
        oval.left = start
        oval.right = padding + height
        oval.bottom = (h - topPadding)

        centerX = (oval.left + oval.right) / 2
        centerY = (oval.top + oval.bottom) / 2
        radius = (oval.right - oval.left) / 2

    }

}