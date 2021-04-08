package com.example.tictactoe.data_classes

class Player (val name: String){

    var score: Int = 0

    fun increaseScore(){
        score++
    }

}