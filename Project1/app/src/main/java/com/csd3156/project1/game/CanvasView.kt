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
    private val snakeHeadBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.snake_head)
    private val bg : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.game_background)

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        floor.color = Color.DKGRAY

        //canvas?.drawRect(0f,0f,1050f,1050f,floor)
        canvas?.drawBitmap(bg,0f, 0f, floor)

        var count = 1
        for (i in Snake.bodyParts)
        {
            val x = i[0]
            val y = i[1]

            // Create a Rect object to represent the bounds of the body part
            val bodyPartRect = RectF(x, y, x + 45, y + 45)

            if(count == Snake.bodyParts.size) {
                val rotator = Matrix()

                when (Snake.direction) {
                    "right" -> rotator.postRotate(90F)
                    "down" -> rotator.postRotate(180F)
                    "left" -> rotator.postRotate(270F)
                }

                val rotatedHead = Bitmap.createBitmap(snakeHeadBitmap, 0, 0, snakeHeadBitmap.width, snakeHeadBitmap.height, rotator, true);
                canvas?.drawBitmap(rotatedHead, null, bodyPartRect, null)
            }

            else
                // Draw the body part image onto the canvas
                canvas?.drawBitmap(snakeBitmap, null, bodyPartRect, null)

            count++
        }

        val appleRect = RectF(Apple.posX, Apple.posY, Apple.posX +45, Apple.posY +45)
        canvas?.drawBitmap(appleBitmap, null, appleRect, null)
    }
}
