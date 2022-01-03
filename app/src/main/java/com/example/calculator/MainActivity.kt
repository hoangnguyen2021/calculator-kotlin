package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // properties for calculator's main activity
    private var prevOp = "="
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var newValue = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find views
        val digitButtonIds: IntArray =
            intArrayOf(
                R.id.btn_zero,
                R.id.btn_one,
                R.id.btn_two,
                R.id.btn_three,
                R.id.btn_four,
                R.id.btn_five,
                R.id.btn_six,
                R.id.btn_seven,
                R.id.btn_eight,
                R.id.btn_nine,
            )
        val operatorButtonIds: IntArray =
            intArrayOf(
                R.id.btn_plus,
                R.id.btn_minus,
                R.id.btn_multiply,
                R.id.btn_divide,
                R.id.btn_equal,
            )
        val screen: TextView = findViewById(R.id.screen)
        val digitButtons: List<Button> = digitButtonIds.map { id: Int -> findViewById(id) }
        val dotButton: Button = findViewById(R.id.btn_dot)
        val operatorButtons: List<Button> =
            operatorButtonIds.map { id: Int -> findViewById(id) }
        val clearButton: Button = findViewById(R.id.btn_clear)
        val negativeButton: Button = findViewById(R.id.btn_negative)

        /*  digit buttons' handler
            if newValue is true,
                replace the screen text with the button text
            otherwise,
                append the screen text with the button text
                (if the screen text is "-0", remove "0"
                before appending)
         */
        digitButtons.forEach { button: Button ->
            button.setOnClickListener {
                if (newValue) {
                    screen.text = (it as Button).text
                    newValue = false
                } else {
                    if (screen.text.toString() == "0") {
                        screen.text = ""
                    } else if (screen.text.toString() == "-0") {
                        screen.text = "-"
                    }
                    screen.append((it as Button).text)
                }
            }
        }

        /*  dot button's handler
            if screen text ends with '.', do nothing.
            otherwise:
                if newValue is true, set screen text to "0."
                if newValue is false, append "." to screen text
        */
        dotButton.setOnClickListener {
            if (!screen.text.contains('.')) {
                if (newValue) {
                    screen.text = "0."
                    newValue = false
                } else {
                    screen.append(".")
                }
            }
        }

        /*  operator button's handler
            if newValue is false,
                set newValue to true
                if prevOp is "=", save screen text to operand1
                if prevOp is "+", "-", "*", or "/", save screen text to
                    operand2, perform calculation, show result on screen,
                    save result to operand1, and set operand2 to 0
                update prevOp
            otherwise, only update prevOp
         */
        operatorButtons.forEach { button: Button ->
            button.setOnClickListener {
                if (!newValue) {
                    newValue = true
                    when (prevOp) {
                        "=" -> {
                            operand1 = screen.text.toString().toDouble()
                        }
                        "+" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 + operand2
                            screen.text = removeDotZero(result.toString())
                            operand1 = result
                            operand2 = 0.0

                        }
                        "-" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 - operand2
                            screen.text = removeDotZero(result.toString())
                            operand1 = result
                            operand2 = 0.0
                        }
                        "*" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 * operand2
                            println(result)
                            screen.text = removeDotZero(result.toString())
                            operand1 = result
                            operand2 = 0.0
                        }
                        "/" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 / operand2
                            println(result)
                            screen.text = removeDotZero(result.toString())
                            operand1 = result
                            operand2 = 0.0
                        }
                    }
                }
                prevOp = (it as Button).text.toString()
            }
        }

        // clear button's handler
        clearButton.setOnClickListener {
            screen.text = "0"
            newValue = true
            operand1 = 0.0
            operand2 = 0.0
            prevOp = "="
        }

        // negative button's handler
        negativeButton.setOnClickListener {
            if (newValue) {
                screen.text = "-0"
                newValue = false
            } else {
                if (!screen.text.startsWith('-')) {
                    // prepend "-"
                    "-${screen.text}".also { screen.text = it }
                } else {
                    // remove "-"
                    screen.text = screen.text.toString().substring(1)
                }
            }
        }
    }

    private fun removeDotZero(result: String): String {
        if (result == "-0.0") {
            return "0"
        }
        if (result.endsWith(".0")) {
            return result.substring(0, result.length - 2)
        }
        return result
    }

}