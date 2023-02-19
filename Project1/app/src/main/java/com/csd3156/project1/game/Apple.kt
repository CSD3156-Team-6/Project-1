package com.csd3156.project1.game

class Apple
{
    companion object
    {
        var posX = 500f
        var posY = 500f

        fun generateAppleRandom()
        {
            posX = (1..20).random().toFloat() * 50
            posY = (1..20).random().toFloat() * 50
        }
    }
}