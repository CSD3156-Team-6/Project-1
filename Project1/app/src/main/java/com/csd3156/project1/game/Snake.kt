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

        fun resetSnake()
        {
            headX = 0f;
            headY = 0f;
            bodyParts = mutableListOf(arrayOf(0f, 0f))
            direction = "right";
        }
    }
}