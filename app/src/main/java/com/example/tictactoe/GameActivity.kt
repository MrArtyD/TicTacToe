package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.data_classes.Player
import kotlin.random.Random
import kotlin.random.nextInt


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var isPvP : Boolean = true
    private var gameEnded : Boolean = false
    private var turnOfPlayerOne = true
    private var roundCount : Int = 0

    private var playerOne: Player?  = null
    private var playerTwo: Player?  = null

    private var tvPlayerOneScore: TextView? = null
    private var tvPlayerTwoScore: TextView? = null
    private var tvPlayersTurn: TextView? = null

    private var buttonsArray = arrayOf<Array<Button>>()
    private var unclickedButtons: ArrayList<Button>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeGame()
    }

    private fun initializeGame() {
        val nameOne : String? = intent.getStringExtra(ChooseNameActivity.EXTRA_NAME_ONE)
        val nameTwo : String? = intent.getStringExtra(ChooseNameActivity.EXTRA_NAME_TWO)

        playerOne = Player(nameOne!!)
        playerTwo = Player(nameTwo!!)

        tvPlayerOneScore = findViewById(R.id.tv_player1_score)
        tvPlayerTwoScore = findViewById(R.id.tv_player2_score)
        tvPlayersTurn = findViewById(R.id.tv_turn)

        (findViewById<TextView>(R.id.tv_player1_name)).text = "$nameOne:"
        (findViewById<TextView>(R.id.tv_player2_name)).text = "$nameTwo:"
        tvPlayersTurn?.text = getString(R.string.turn_of) + " $nameOne"

        initializeButtons()

        isPvP = intent.getBooleanExtra(MainActivity.EXTRA_BOOLEAN, true)
        if (!isPvP){
            initializeUnclickedButtons()
        }
    }

    private fun initializeButtons() {
        for (indexOne in 0..2){
            var array = arrayOf<Button>()
            for (indexTwo in 0..2){
                val buttonID = "button_$indexOne$indexTwo"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                array += findViewById<Button>(resID)
                array[indexTwo].setOnClickListener(this)
            }
            buttonsArray += array
        }
    }

    override fun onClick(view: View?) {
        if ((view as Button).text.toString() != "" || gameEnded){
            return
        }

        if (turnOfPlayerOne) {
            view.text = "X"
        } else {
            view.text = "O"
        }

        roundCount++

        if (!isPvP){
            setAsClicked(view)
        }

        if (checkForWin()) {
            if (turnOfPlayerOne) {
                playerWins(playerOne)
                tvPlayerOneScore?.text = playerOne?.score.toString()
            } else {
                playerWins(playerTwo)
                tvPlayerTwoScore?.text = playerTwo?.score.toString()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            changeTurn()

            if (!isPvP && !turnOfPlayerOne){
                val handler = Handler()
                handler.postDelayed({
                    moveOfEnvironment()
                }, 800)
            }
        }
    }

    private fun changeTurn() {
        turnOfPlayerOne = !turnOfPlayerOne
        if (turnOfPlayerOne){
            tvPlayersTurn?.text = getString(R.string.turn_of) + " " + playerOne?.name
        }else{
            tvPlayersTurn?.text = getString(R.string.turn_of) + " " + playerTwo?.name
        }
    }

    //Method stolen from https://codinginflow.com/tutorials/android/tic-tac-toe/part-3-finish-game
    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttonsArray[i][j].text.toString()
            }
        }

        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] != "") {
                return true
            }
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] != "") {
                return true
            }
        }

        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != "") {
            return true
        }

        if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != "") {
            return true
        }

        return false
    }

    private fun playerWins(player: Player?) {
        player?.increaseScore()
        Toast.makeText(this, player?.name + " wins!", Toast.LENGTH_SHORT).show()
        gameEnded = true
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
    }


    private fun resetField() {
        buttonsArray.forEach {
            it.forEach { button ->
                button.text = ""
            }
        }
        roundCount = 0
        gameEnded = false

        if (!isPvP){
            turnOfPlayerOne = false
            changeTurn()
            initializeUnclickedButtons()
        }
    }

    fun endGame(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun resetGame(view: View) {
        resetField()
    }

    private fun initializeUnclickedButtons() {
        unclickedButtons = arrayListOf()
        buttonsArray.forEach {
            it.forEach {button ->
                unclickedButtons!!.add(button)
            }
        }
    }

    private fun setAsClicked(button: Button) {
        unclickedButtons!!.remove(button)
    }

    private fun moveOfEnvironment() {
        unclickedButtons!!.random().performClick()
    }

}