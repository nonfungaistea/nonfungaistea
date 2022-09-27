package com.example.myapplication

enum class BoardSize(val numCards: Int){
    //Dynamically
    EASY(8),
    MEDIUM(18),
    HARD(24);

    fun getWidth(): Int {
        return when (this){
            EASY -> 2
            MEDIUM -> 2
            HARD -> 2
        }
    }

    fun getHeight(): Int {
        return numCards / getWidth()
    }

    fun getNumPairs(): Int {
        return numCards / 2
    }
}