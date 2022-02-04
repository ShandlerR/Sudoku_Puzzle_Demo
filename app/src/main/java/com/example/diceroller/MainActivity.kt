package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private fun startGame(boxArray: Array<Array<TextView>>, range: Int, givenNumbers: Int): MutableList<MutableList<Int>> {
        println("Game Started")

        for(element in boxArray) {
            for(box in element) {
                box.isFocusable = true
            }
        }

        var solutionArray: MutableList<MutableList<Int>> = createSolution(boxArray, range)

        println(solutionArray)

        var playerArray = changeRandomToZero(solutionArray)

        println(playerArray)

        updateTable(boxArray, playerArray)

        return solutionArray

    }

    private fun checkSolution(solutionArray: MutableList<MutableList<Int>>, boxArray: Array<Array<TextView>>): Boolean {
        //checks to see if what the player entered in matches the solution code
        var gameFinish = true
        for ((column, element) in boxArray.withIndex()) {
            for ((row, box) in element.withIndex()) {
                if(box.text.toString() != solutionArray[column][row].toString()) {
                    gameFinish = false
                }
            }
        }
        return gameFinish
    }

    private fun createSolution(boxArray: Array<Array<TextView>>, range: Int): MutableList<MutableList<Int>> {
        //returns the 2-d solution array filled in from 1-9 with random numbers (never repeating)

        var solutionList: MutableList<MutableList<Int>> = ArrayList()
        var numberList: MutableList<Int> = ArrayList()

        for(number in (1..range)) {
            numberList.add(number)
        }

        var listOne: MutableList<Int> = ArrayList()
        for(i in (1..3)) {
            listOne.add(0)
        }
        var listTwo: MutableList<Int> = ArrayList()
        for(i in (1..3)) {
            listTwo.add(0)
        }
        var listThree: MutableList<Int> = ArrayList()
        for(i in (1..3)) {
            listThree.add(0)
        }

        var randomColumn = (0..2).random()
        var randomRow = (0..2).random()

        for(element in numberList) {

            var elementChange = false
            while(!elementChange) {

                randomColumn = (0..2).random()
                randomRow = (0..2).random()

                when(randomColumn) {
                    0 -> {
                        if(listOne[randomRow] == 0) {
                            listOne[randomRow] = element
                            elementChange = true
                        }
                    }
                    1 -> {
                        if(listTwo[randomRow] == 0) {
                            listTwo[randomRow] = element
                            elementChange = true
                        }
                    }
                    2 -> {
                    if(listThree[randomRow] == 0) {
                        listThree[randomRow] = element
                        elementChange = true
                        }
                    }
                }
            }

        }

        solutionList.add(listOne)
        solutionList.add(listTwo)
        solutionList.add(listThree)





        return solutionList
    }

    private fun changeRandomToZero(solutionArray: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        //returns the solution Array with one element set to 0 (for the player to guess)
        var playerArray: MutableList<MutableList<Int>> = ArrayList()
        var listOne: MutableList<Int> = solutionArray[0].toMutableList()
        var listTwo: MutableList<Int> = solutionArray[1].toMutableList()
        var listThree: MutableList<Int> = solutionArray[2].toMutableList()

        var randomColumn = (0..2).random()
        var randomRow = (0..2).random()

        when(randomColumn) {
            0 -> {
                listOne.set(randomRow, 0)
            }
            1 -> {
                listTwo.set(randomRow, 0)
            }
            2 -> {
                listThree.set(randomRow, 0)
            }
        }

        playerArray.add(listOne)
        playerArray.add(listTwo)
        playerArray.add(listThree)


        //val playerArray = solutionArray.toMutableList()

        return playerArray
    }

    private fun updateTable(boxArray: Array<Array<TextView>>, playerArray: MutableList<MutableList<Int>>) {
        //updates the Ui that the user sees with the new table
        for((column, element) in boxArray.withIndex()) {
            for((row, box) in element.withIndex()) {
                if(playerArray[column][row] > 0) {
                    box.text = playerArray[column][row].toString()
                    box.isFocusable = false
                } else {
                    box.text = ""
                    box.isFocusable = true
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val restartButton: Button = findViewById(R.id.button)
        val boxOne: TextView = findViewById(R.id.textBox1)
        val boxTwo: TextView = findViewById(R.id.textBox2)
        val boxThree: TextView = findViewById(R.id.textBox3)
        val boxFour: TextView = findViewById(R.id.textBox4)
        val boxFive: TextView = findViewById(R.id.textBox5)
        val boxSix: TextView = findViewById(R.id.textBox6)
        val boxSeven: TextView = findViewById(R.id.textBox7)
        val boxEight: TextView = findViewById(R.id.textBox8)
        val boxNine: TextView = findViewById(R.id.textBox9)
        var range = 9 //the end of the range which each box can max at
        var givenNumbers = 8 //The amount of numbers given to the user to help them solve the puzzle



        var boxArray : Array<Array<TextView>> = arrayOf(arrayOf(boxOne, boxTwo, boxThree), arrayOf(boxFour, boxFive, boxSix), arrayOf(boxSeven, boxEight, boxNine))



        var solutionArray: MutableList<MutableList<Int>> = startGame(boxArray, range, givenNumbers)

        var gameFinish: Boolean

        val resetButton: Button = findViewById(R.id.button)
        resetButton.setOnClickListener {
            solutionArray = startGame(boxArray, range, givenNumbers)
        }

        val submitButton: Button = findViewById(R.id.button11)
        submitButton.setOnClickListener {
        gameFinish = checkSolution(solutionArray, boxArray)
            println(solutionArray)
            print("[")
            for(element in boxArray) {
                for(box in element) {
                    print("${box.text}, ")
                }
            }
            println("]")
        if(gameFinish) {
            val toast = Toast.makeText(applicationContext, "Congratulations!", Toast.LENGTH_LONG)
            toast.show()
        } else {
            val toast = Toast.makeText(applicationContext, "Try Again...", Toast.LENGTH_SHORT)
            toast.show()
        }
        }



/*val rollButton: Button = findViewById(R.id.button)
rollButton.setOnClickListener {
   rollDice()
}*/
}

/*private fun rollDice() {
val dice = Dice(6)
val diceRoll = dice.roll()
val resultTextView: TextView = findViewById(R.id.textView)
resultTextView.text = diceRoll.toString()
} */
}

/*class Dice(val numSides: Int) {

fun roll(): Int {
return (1..numSides).random()
}
} */
