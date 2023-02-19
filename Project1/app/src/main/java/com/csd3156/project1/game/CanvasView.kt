package com.csd3156.project1.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr)
{
    private val snake = Paint()
    private val apple = Paint()
    private val floor = Paint()

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        snake.color = Color.GREEN
        apple.color = Color.RED
        floor.color = Color.GRAY

        canvas?.drawRect(0f,0f,1050f,1050f,floor)

        for (i in Snake.bodyParts)
            canvas?.drawRect(i[0], i[1], i[0]+45, i[1]+45, snake)

        canvas?.drawRect(Apple.posX, Apple.posY, Apple.posX +45, Apple.posY +45,apple)
    }
}
