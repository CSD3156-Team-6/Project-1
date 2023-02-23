package com.csd3156.project1.game

class Snake
{
    companion object
    {
        var headX       = 0f
        var headY       = 0f
        var bodyParts   = mutableListOf(arrayOf(0f, 0f))
        var direction   = "right";
        var alive       = false;

        fun IsMovePossible(): Boolean
        {
            if (headX < 0f || headX > 1000f || headY < 0f || headY > 1000)
                return false
            return true
        }

        fun snakeInitalLength(length: Int) {
            repeat(length) {
                // Convert the snake head to a body
                Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))
            }
        }

        fun resetSnake()
        {
            headX = 0f;
            headY = 0f;
            bodyParts = mutableListOf(arrayOf(0f, 0f))
            direction = "right";
            snakeInitalLength(2)
        }
    }
}