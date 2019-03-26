package com.example.lab2.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

enum class Board {
    EMPTY, O, X
}

class MainActivity : AppCompatActivity() {

    private var board: Array<Array<Board>> = arrayOf(
            arrayOf(Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY),
            arrayOf(Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY),
            arrayOf(Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY),
            arrayOf(Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY),
            arrayOf(Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY, Board.EMPTY)
    )

    private var playerType: Board = Board.O
    private var cellUsed: Int = 0
    private var win: Boolean = false
    private var isClickable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerTableCellsListeners()
        reset.setOnClickListener {
            info.text = ""
            resetGame()
        }
        AI.setOnClickListener { handleAI() }
    }

    private fun registerTableCellsListeners() {
        for (i in 0..4) {
            for (j in 0..4) {
                val cell = getImageViewByStringId("cell$i" + "_$j")
                cell.setOnClickListener {
                    if(!win && isClickable) {
                        handleCellClick(i, j, it)
                    }
                }
            }
        }
    }

    private fun getImageViewByStringId(name: String) :ImageView {
        val cellId = resources.getIdentifier(name, "id", packageName)
        return findViewById<ImageView>(cellId)
    }

    private fun handleCellClick(i: Int, j: Int, view: View) {
        info.text = ""

        if(board[i][j] == Board.EMPTY) {
            humanStrategy(i, j)

            if (cellUsed >= 25 && !win) {
                info.text = "Queue.. :("
                changePlayer()
                setImageByBoardType(i, j, playerType)
                win = true
            }

            if (AI.isChecked && !win) {
                AIStrategy()
            }
        }
    }

    private fun checkWin(i: Int, j: Int) {
        if (hasWon(i, j)) {
            if (playerType == Board.O) {
                info.text = "Player 'O' has won!"
            } else {
                info.text = "Player 'X' has won!"
            }
            win = true
        }
    }


    private fun humanStrategy(i: Int, j: Int) {
        setCell(i, j, playerType)
        cellUsed++
        checkWin(i, j)
        changePlayer()
    }

    /**
     * AI is always 'X' !
     */
    private fun AIStrategy() {
        val r = Random()
        var i = r.nextInt(5)
        var j = r.nextInt(5)

        while (board[i][j] != Board.EMPTY) {
            i = r.nextInt(5)
            j = r.nextInt(5)
        }

        info.text = "AI is thinking..."
        board[i][j] = Board.X
        isClickable = false
        cellUsed++

        Handler().postDelayed({
            if(!win) {
                info.text = ""
            }
            setImageByBoardType(i, j, Board.X)
            isClickable = true
        }, 500)

        checkWin(i, j)
        changePlayer()
    }

    private fun hasWon(a: Int, b: Int) : Boolean {
        // diagonals
        if(board[0][0] != Board.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]
                && board[2][2] == board[3][3] && board[3][3] == board[4][4]) {
            return true
        }

        if(board[0][4] != Board.EMPTY && board[0][4] == board[1][3] && board[1][3] == board[2][2]
                && board[2][2] == board[3][1] && board[3][1] == board[4][0]) {
            return true
        }

        val probableType = board[a][b]

        // rows
        var rowsWin = true

        for (i in 0..4) {
            if(board[a][i] != probableType) {
                rowsWin = false
                break
            }
        }

        if(rowsWin) {
            return true
        }

        // columns
        var columnsWin = true

        for (i in 0..4) {
            if(board[i][b] != probableType) {
                columnsWin = false
                break
            }
        }

        if(columnsWin) {
            return true
        }

        return false
    }

    private fun changePlayer() {
        if(playerType == Board.O) {
            playerType = Board.X
        } else {
            playerType = Board.O
        }
    }

    private fun resetGame() {
        for (i in 0..4) {
            for (j in 0..4) {
                setCell(i, j, Board.EMPTY)
            }
        }
        win = false
        cellUsed = 0
        playerType = Board.O
        isClickable = true
    }

    private fun handleAI() {
        resetGame()
    }

    private fun setCell(i: Int, j: Int, type: Board) {
        board[i][j] = type
        setImageByBoardType(i, j, type)
    }

    private fun setImageByBoardType(i: Int, j: Int, type: Board) {
        val cell = getImageViewByStringId("cell$i" + "_$j")

        when (type) {
            Board.EMPTY -> cell.setImageResource(R.drawable.ic_empty_48dp)
            Board.O -> cell.setImageResource(R.drawable.ic_o_48dp)
            Board.X -> cell.setImageResource(R.drawable.ic_x_48dp)
        }
    }
}
