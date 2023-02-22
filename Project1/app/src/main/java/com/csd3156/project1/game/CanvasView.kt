package com.csd3156.project1.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.csd3156.project1.R

class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr)
{
    private val floor = Paint()
    private val snakeBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.snake_body)
    private val appleBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.apple)

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        floor.color = Color.DKGRAY

        canvas?.drawRect(0f,0f,1050f,1050f,floor)

        for (i in Snake.bodyParts)
        {
            val x = i[0]
            val y = i[1]

            // Create a Rect object to represent the bounds of the body part
            val bodyPartRect = RectF(x, y, x + 45, y + 45)

            // Draw the body part image onto the canvas
            canvas?.drawBitmap(snakeBitmap, null, bodyPartRect, null)
        }

        val appleRect = RectF(Apple.posX, Apple.posY, Apple.posX +45, Apple.posY +45)
        canvas?.drawBitmap(appleBitmap, null, appleRect, null)
    }
}
