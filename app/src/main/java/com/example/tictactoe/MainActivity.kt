package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BOOLEAN = "com.example.tictactoe.BOOLEAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startPvPGame(view: View) {
        val intent = Intent(this, ChooseNameActivity::class.java)
        intent.putExtra(Companion.EXTRA_BOOLEAN, true)
        startActivity(intent)
    }
    fun startPvEGame(view: View) {
        val intent = Intent(this, ChooseNameActivity::class.java)
        intent.putExtra(Companion.EXTRA_BOOLEAN, false)
        startActivity(intent)
    }
}