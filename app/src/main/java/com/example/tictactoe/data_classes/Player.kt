package com.example.tictactoe.data_classes

class Player (val name: String){

    private var score: Int = 0

    fun getScore() : Int{
        return score
    }

    fun increaseScore(){
        score++
    }

}