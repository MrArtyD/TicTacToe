package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ChooseNameActivity : AppCompatActivity() {

    private var isPvP : Boolean = true

    companion object {
        const val EXTRA_NAME_ONE= "com.example.tictactoe.NAME_ONE"
        const val EXTRA_NAME_TWO= "com.example.tictactoe.NAME_TWO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_name)

        checkForPvP()
    }

    private fun checkForPvP() {
        isPvP = intent.getBooleanExtra(MainActivity.EXTRA_BOOLEAN, true)
        if (!isPvP){
            changeLayoutForPvE()
        }
    }

    private fun changeLayoutForPvE() {
        val playerTwoText : TextView = findViewById(R.id.tv_player2)
        val playerTwoEditText : EditText = findViewById(R.id.et_player2)

        playerTwoText.visibility = View.INVISIBLE
        playerTwoEditText.visibility = View.INVISIBLE

        val activityLayout : ConstraintLayout = findViewById(R.id.parentLayout)
        val set = ConstraintSet()

        set.clone(activityLayout)
        set.connect(R.id.btn_start_game, ConstraintSet.TOP,
                    R.id.tv_player1, ConstraintSet.BOTTOM)
        set.applyTo(activityLayout)
    }

    fun startGame(view: View) {
        val playerOneEditText : EditText = findViewById(R.id.et_player1)
        val playerTwoEditText : EditText = findViewById(R.id.et_player2)

        val playerOneName = playerOneEditText.text.toString()
        val playerTwoName = playerTwoEditText.text.toString()

        if (playerOneName == "" || playerTwoName == ""){
            Toast.makeText(this, "Please, select a name for player.", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_BOOLEAN, isPvP)
            intent.putExtra(EXTRA_NAME_ONE, playerOneName)
            intent.putExtra(EXTRA_NAME_TWO, playerTwoName)
            startActivity(intent)
        }
    }
}